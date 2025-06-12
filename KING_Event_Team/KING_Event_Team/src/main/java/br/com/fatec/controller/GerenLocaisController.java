/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.LocalizacaoDAO;
import br.com.fatec.model.Localizacao;
import br.com.fatec.persistencia.Banco;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
public class GerenLocaisController implements Initializable
{
    @FXML
    private Button btnFechar;
    @FXML
    private Button btnMin;
    @FXML
    private Button btnVoltar;
    @FXML
    private Label lblTitulo;
    @FXML
    private TableView<Localizacao> tbvLocais;
    @FXML
    private TableColumn<Localizacao, Integer> colCodigo;
    @FXML
    private TableColumn<Localizacao, String> colNomeLocal;
    @FXML
    private TableColumn<Localizacao, String> colEndereco;
    @FXML
    private TableColumn<Localizacao, Integer> colNumero;
    @FXML
    private TableColumn<Localizacao, String> colCidade;
    @FXML
    private TableColumn<Localizacao, String> colEstado;
    @FXML
    private TableColumn<Localizacao, String> colCEP;
    @FXML
    private TableColumn<Localizacao, String> colTipo;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnNovoLocal;
    @FXML
    private ComboBox<String> cmbTipo;
    @FXML
    private TextField txtBuscar;
    @FXML
    private Button btnPesquisar;
    
    private String buscar;
    
    private String tipoBusca;
    
    private PreparedStatement pst;
    
    private ResultSet rs;
    
    private Localizacao localizacao;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        preencherTabela();
        cmbTipo.getItems().addAll("CodLocal", "NomeLocal", "CEP", "Endereco", "NumeroLocal", "Cidade", "Estado", "TipoLocal");
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
    private void btnEditar_Click(ActionEvent event) 
    {
        Localizacao localizacao = tbvLocais.getSelectionModel().selectedItemProperty().get();
        if(localizacao == null)
        {
            App.mensagem("AVISO", "Selecione uma Localização!", Alert.AlertType.WARNING);
            return;
        }
        
        NovaLocalizacaoController.localAEditar = localizacao;
        NovaLocalizacaoController.isModoEdicao = true;
        App.carregarCena("NovaLocalizacao");
    }

    @FXML
    private void btnDeletar_Click(ActionEvent event) 
    {
        Localizacao localizacao = tbvLocais.getSelectionModel().selectedItemProperty().get();
        if(localizacao == null)
        {
            App.mensagem("AVISO", "Selecione uma Localização!", Alert.AlertType.WARNING);
            return;
        }
        
        try
        {
            LocalizacaoDAO dao = new LocalizacaoDAO();
            dao.remover(localizacao);
            App.mensagem("SUCESSO", localizacao.getNomeLocal() + " foi removido com sucesso!");
            preencherTabela();
        }
        catch(SQLException ex)
        {
             App.mensagem("ERRO", "Erro ao Deletar: É possível que essa localização esteja sendo usada em um evento.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnNovoLocal_Click(ActionEvent event) 
    {
        App.carregarCena("NovaLocalizacao");
    }
    
    private void preencherTabela()
    {
        tbvLocais.getItems().clear();
        
        try
        {
            LocalizacaoDAO dao = new LocalizacaoDAO();
            ObservableList<Localizacao> lista = FXCollections.observableArrayList(dao.listar(""));
            tbvLocais.setItems(lista);
            
            colCodigo.setCellValueFactory(new PropertyValueFactory("codLocal"));
            colNomeLocal.setCellValueFactory(new PropertyValueFactory("nomeLocal"));
            colEndereco.setCellValueFactory(new PropertyValueFactory("enderecoLocal"));
            colNumero.setCellValueFactory(new PropertyValueFactory("numeroLocal"));
            colCidade.setCellValueFactory(new PropertyValueFactory("cidade"));
            colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
            colCEP.setCellValueFactory(new PropertyValueFactory("CEP"));
            colTipo.setCellValueFactory(new PropertyValueFactory("tipoLocal"));
            
        }
        catch(SQLException ex)
        {
            App.mensagem("ERRO", "Erro ao preencher tabela.", Alert.AlertType.ERROR);
        }
    }
    
    private void btnAtualizar_Click(ActionEvent event)
    {
        preencherTabela();
    }

    @FXML
    private void btnPesquisar_Click(ActionEvent event) {
        buscar = txtBuscar.getText();
        tipoBusca = cmbTipo.getValue();
        
        try{
        String sql = "SELECT * FROM localizacao ";
            
        if(tipoBusca != null){
            sql += " WHERE " + tipoBusca;
            if(buscar.matches("\\d+")){
                sql += " = " + buscar + ";";
            }
            else{
                sql += " = '" + buscar + "';";
            }
        }
        else{
            App.mensagem("AVISO, Selecione um tipo de pesquisa!", Alert.AlertType.INFORMATION);
        }
            
        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);
        rs = pst.executeQuery();
        
        while(rs.next()){
        localizacao = new Localizacao();
        
        localizacao.setCodLocal(rs.getInt("codLocal"));
        localizacao.setNomeLocal(rs.getString("nomeLocal"));
        localizacao.setCEP(rs.getString("CEP"));
        localizacao.setEnderecoLocal(rs.getString("endereco"));
        localizacao.setNumeroLocal(rs.getString("numeroLocal"));
        localizacao.setCidade(rs.getString("cidade"));
        localizacao.setEstado(rs.getString("estado"));
        localizacao.setTipoLocal(rs.getString("tipoLocal"));
        
        }
        Banco.desconectar();
        rs.close();
        }
        catch(SQLException ex){
            
        }
        
        tbvLocais.getItems().clear();
        
        ObservableList<Localizacao> listar = FXCollections.observableArrayList(localizacao);
        tbvLocais.setItems(listar);
        colCodigo.setCellValueFactory(new PropertyValueFactory("codLocal"));
        colNomeLocal.setCellValueFactory(new PropertyValueFactory("nomeLocal"));
        colEndereco.setCellValueFactory(new PropertyValueFactory("enderecoLocal"));
        colNumero.setCellValueFactory(new PropertyValueFactory("numeroLocal"));
        colCidade.setCellValueFactory(new PropertyValueFactory("cidade"));
        colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
        colCEP.setCellValueFactory(new PropertyValueFactory("CEP"));
        colTipo.setCellValueFactory(new PropertyValueFactory("tipoLocal"));
    }
}
