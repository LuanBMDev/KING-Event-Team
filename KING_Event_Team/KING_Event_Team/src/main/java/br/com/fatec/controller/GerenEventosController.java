/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.EventoDAO;
import br.com.fatec.model.Categoria;
import br.com.fatec.model.Evento;
import br.com.fatec.model.Localizacao;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private Pane panBusca;
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

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       preencherTabela();
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
        
        NovaExposicaoController.evento = evento;
        App.carregarCena("GerenExposicoes");
    }
    private void preencherTabela()
    {
        tbvEventos.getItems().clear();
        
        try
        {
            EventoDAO dao = new EventoDAO();
            ObservableList<Evento> lista = FXCollections.observableArrayList(dao.listar(""));
            //lista.setAll(dao.listar(""));
            tbvEventos.setItems(lista);
            
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
        catch(SQLException ex)
        {
            App.mensagem("ERRO", "Erro ao preencher tabela.", Alert.AlertType.ERROR);
        }
        
    }
}
