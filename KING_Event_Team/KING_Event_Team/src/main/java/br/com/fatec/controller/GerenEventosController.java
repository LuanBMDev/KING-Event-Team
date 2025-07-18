/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.CategoriaDAO;
import br.com.fatec.DAO.EventoDAO;
import br.com.fatec.DAO.LocalizacaoDAO;
import br.com.fatec.model.Categoria;
import br.com.fatec.model.Evento;
import br.com.fatec.model.Localizacao;
import br.com.fatec.persistencia.Banco;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author luann
 */
public class GerenEventosController implements Initializable{

    @FXML
    private Button btnFechar;
    @FXML
    private Button btnMin;
    @FXML
    private Button btnVoltar;
    @FXML
    private Label lblTitulo;
    @FXML
    private TableView<Evento> tbvEventos;
    @FXML
    private TableColumn<Evento, Integer> colCodEvento;
    @FXML
    private TableColumn<Evento, String> colNomeEvento;
    @FXML
    private TableColumn<Evento, String> colDataInicio;
    @FXML
    private TableColumn<Evento, String> colDataFim;
    @FXML
    private TableColumn<Evento, String> colStatus;
    @FXML
    private TableColumn<Evento, String> colLocal;
    @FXML
    private TableColumn<Evento, String> colCategoria;
    @FXML
    private TableColumn<Evento, Double> colIngressoPadrao;
    @FXML
    private TableColumn<Evento, String> colTotalVendido;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnNovoEvento;
    @FXML
    private Button btnCadIngresso;
    @FXML
    private Button btnGerenIngressos;
    @FXML
    private Button btnCadExposicao;
    @FXML
    private Button btnGerenExposicoes;
    @FXML
    private ComboBox<String> cmbTipo;
    @FXML
    private TextField txtPesquisar;
    @FXML
    private Button btnPesquisar;
    
    private String buscar;
    
    private String tipoBusca;
    
    private PreparedStatement pst;
    
    private ResultSet rs;
    
    private Evento evento;

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       preencherTabela();
       cmbTipo.getItems().addAll("CodEvento", "NomeEvento", "DataInicio", "DataFim", "StatusEvento", "CodLocal", "CodCat", "PrecoIngresso");
    }
    
    @FXML
    private void btnFechar_Click(ActionEvent event) 
    {
        Stage stage = (Stage) btnFechar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnMin_Click(ActionEvent event) 
    {
        Stage stage = (Stage) btnMin.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    private void btnVoltar_Click(ActionEvent event) 
    {
        App.carregarCena("MenuPrincipal");
    }

    @FXML
    private void btnDeletar_Click(ActionEvent event) 
    {
        Evento evento = tbvEventos.getSelectionModel().selectedItemProperty().get();
        if(evento == null)
        {
            App.mensagem("AVISO", "Selecione um Evento!", Alert.AlertType.WARNING);
            return;
        }
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("CONFIRMAÇÃO");
        alerta.setHeaderText("Deseja excluir este item?");
        Optional<ButtonType> resultado = alerta.showAndWait();
        if(resultado.isPresent() && resultado.get() == ButtonType.OK){
        
            try
            {
                EventoDAO dao = new EventoDAO();
                dao.remover(evento);
                App.mensagem("APAGADO", "Evento apagado com sucesso!");
            }
            catch(SQLException ex)
            {
                App.mensagem("ERRO", "Erro ao Deletar", Alert.AlertType.ERROR);
            }

            preencherTabela();
        }
    }

    @FXML
    private void btnEditar_Click(ActionEvent event) 
    {
        Evento evento = tbvEventos.getSelectionModel().selectedItemProperty().get();
        if(evento == null)
        {
            App.mensagem("AVISO", "Selecione um Evento!", Alert.AlertType.WARNING);
            return;
        }
        
        NovoEventoController.isModoEdicao = true;
        NovoEventoController.eventoAEditar = evento;
        App.carregarCena("NovoEvento");
    }

    @FXML
    private void btnNovoEvento_Click(ActionEvent event) 
    {
        App.carregarCena("NovoEvento");
    }
    
    @FXML
    private void btnCadIngresso_Click(ActionEvent event) 
    {

        Evento evento = tbvEventos.getSelectionModel().selectedItemProperty().get();
        if(evento == null)
        {
            App.mensagem("AVISO", "Selecione um Evento!", Alert.AlertType.WARNING);
            return;
        }
        
        NovoIngressoController.evento = evento;
        App.carregarCena("NovoIngresso");
    }

    @FXML
    private void btnGerenIngressos_Click(ActionEvent event) 
    {
        Evento evento = tbvEventos.getSelectionModel().selectedItemProperty().get();
        if(evento == null)
        {
            App.mensagem("AVISO", "Selecione um Evento!", Alert.AlertType.WARNING);
            return;
        }
        
        GerenIngressosController.evento = evento;
        App.carregarCena("GerenIngressos");
    }

    @FXML
    private void btnCadExposicao_Click(ActionEvent event) 
    {
        Evento evento = tbvEventos.getSelectionModel().selectedItemProperty().get();
        if(evento == null)
        {
            App.mensagem("AVISO", "Selecione um Evento!", Alert.AlertType.WARNING);
            return;
        }
        
        NovaExposicaoController.evento = evento;
        App.carregarCena("NovaExposicao");
    }

    @FXML
    private void btnGerenExposicoes_Click(ActionEvent event) 
    {
        Evento evento = tbvEventos.getSelectionModel().selectedItemProperty().get();
        if(evento == null)
        {
            App.mensagem("AVISO", "Selecione um Evento!", Alert.AlertType.WARNING);
            return;
        }
        
        GerenExposicoesController.evento = evento;
        App.carregarCena("GerenExposicoes");
    }
    private void preencherTabela() {
    tbvEventos.getItems().clear();

    // 1. Mapa para guardar total vendido por evento
    Map<Integer, Double> totalPorEvento = new HashMap<>();

    // 2. Buscar totalPago por evento
    try {
        String sql = "SELECT codEvento, SUM(totalPago) as total FROM Ingresso GROUP BY codEvento";
        Banco.conectar();
        PreparedStatement pst = Banco.obterConexao().prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            int codEvento = rs.getInt("codEvento");
            double total = rs.getDouble("total");
            totalPorEvento.put(codEvento, total);
        }

        rs.close();
        pst.close();
        Banco.desconectar();
    } catch (SQLException ex) {
        App.mensagem("ERRO", "Erro ao buscar totais vendidos: " + ex.getMessage(), Alert.AlertType.ERROR);
    }

    // 3. Preencher tabela
    try {
        EventoDAO dao = new EventoDAO();
        ObservableList<Evento> lista = FXCollections.observableArrayList(dao.listar(""));
        tbvEventos.setItems(lista);

        colCodEvento.setCellValueFactory(new PropertyValueFactory<>("codEvento"));
        colNomeEvento.setCellValueFactory(new PropertyValueFactory<>("nomeEvento"));
        colDataInicio.setCellValueFactory(new PropertyValueFactory<>("dataInicio"));
        colDataFim.setCellValueFactory(new PropertyValueFactory<>("dataFim"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("statusEvento"));
        colLocal.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getLocalizacao().getNomeLocal()));
        colCategoria.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                cellData.getValue().getCategoria().getNomeCat()));
        colIngressoPadrao.setCellValueFactory(new PropertyValueFactory<>("precoPadrao"));

        // 4. Coluna com total vendido para cada evento
        colTotalVendido.setCellValueFactory(cellData -> {
            int codEvento = cellData.getValue().getCodEvento();
            double total = totalPorEvento.getOrDefault(codEvento, 0.0);
            return new ReadOnlyStringWrapper(String.format("%.2f", total));
        });

    } catch (SQLException ex) {
        App.mensagem("ERRO", "Erro ao preencher tabela: " + ex.getMessage(), Alert.AlertType.ERROR);
    }
}

    @FXML
    private void btnPesquisar_Click(ActionEvent event) {
        buscar = txtPesquisar.getText();
        tipoBusca = cmbTipo.getValue();
        
        try{
        String sql = "SELECT * FROM Evento ";
            
        if(tipoBusca != null){
            sql += " WHERE " + tipoBusca;
            if(buscar.matches("\\d+")){
                sql += " = " + buscar + ";";
            }
            else{
                sql += " like '%"+ buscar + "%';";
            }
        }
        else{
            App.mensagem("AVISO, Selecione um tipo de pesquisa!", Alert.AlertType.INFORMATION);
        }
        
        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);
        rs = pst.executeQuery();
        
        while(rs.next()){
            Categoria cat = new Categoria();
            cat.setCodCat(rs.getInt("codCat"));
            CategoriaDAO catDAO = new CategoriaDAO();
            cat = catDAO.buscarID(cat);
            
            Localizacao local = new Localizacao();
            local.setCodLocal(rs.getInt("codLocal"));
            LocalizacaoDAO localDAO = new LocalizacaoDAO();
            local = localDAO.buscarID(local);
            
            evento = new Evento(cat, local);
        
            evento.setCodEvento(rs.getInt("codEvento"));
            evento.setNomeEvento(rs.getString("nomeEvento"));
            evento.setDataInicio(rs.getString("dataInicio"));
            evento.setDataFim(rs.getString("dataFim"));
            evento.setStatusEvento(rs.getString("statusEvento"));
            evento.setPrecoPadrao(rs.getDouble("precoIngresso"));
        
        }
        Banco.desconectar();
        rs.close();
        }
        catch(SQLException ex){
            
        }
        
        tbvEventos.getItems().clear();
        
        ObservableList<Evento> listar = FXCollections.observableArrayList(evento);
        
        tbvEventos.setItems(listar);
            
            colCodEvento.setCellValueFactory(new PropertyValueFactory("codEvento"));
            colNomeEvento.setCellValueFactory(new PropertyValueFactory("nomeEvento"));
            colDataInicio.setCellValueFactory(new PropertyValueFactory("dataInicio"));
            colDataFim.setCellValueFactory(new PropertyValueFactory("dataFim"));
            colStatus.setCellValueFactory(new PropertyValueFactory("statusEvento"));
            colLocal.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                    cellData.getValue().getLocalizacao().getNomeLocal()));
            colCategoria.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                    cellData.getValue().getCategoria().getNomeCat()));
            colIngressoPadrao.setCellValueFactory(new PropertyValueFactory("precoPadrao"));

            colTotalVendido.setCellValueFactory(cellData -> new ReadOnlyStringWrapper("0"));
        
    }
}
