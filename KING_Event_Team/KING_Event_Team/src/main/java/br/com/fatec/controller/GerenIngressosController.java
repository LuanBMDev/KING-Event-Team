/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.IngressoDAO;
import br.com.fatec.model.Evento;
import br.com.fatec.model.Ingresso;
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
public class GerenIngressosController implements Initializable
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
    private TableView<Ingresso> tbvIngressos;
    @FXML
    private TableColumn<Ingresso, String> colNome;
    @FXML
    private TableColumn<Ingresso, Double> colTotal;
    @FXML
    private TableColumn<Ingresso, Integer> colMeiaEntrada;
    @FXML
    private Label txtNomeEvento;
    @FXML
    private Pane panBusca;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnNovoIngresso;
    
    private IngressoDAO dao = new IngressoDAO();
    public static Evento evento;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        txtNomeEvento.setText(evento.getNomeEvento());
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
        App.carregarCena("GerenEventos");
    }

    @FXML
    private void btnEditar_Click(ActionEvent event) 
    {
        Ingresso ingresso = tbvIngressos.getSelectionModel().selectedItemProperty().get();
        if(ingresso == null)
        {
            App.mensagem("AVISO", "Selecione um Ingresso!", Alert.AlertType.WARNING);
            return;
        }
        
        NovoIngressoController.isModoEdicao = true;
        NovoIngressoController.ingressoAEditar = ingresso;
        App.carregarCena("NovoIngresso");
    }

    @FXML
    private void btnDeletar_Click(ActionEvent event) 
    {
        Ingresso ingresso = tbvIngressos.getSelectionModel().selectedItemProperty().get();
        if(ingresso == null)
        {
            App.mensagem("AVISO", "Selecione um Ingresso!", Alert.AlertType.WARNING);
            return;
        }
        
        try
        {
            dao.remover(ingresso);
            App.mensagem("SUCESSO", "Ingresso removido com sucesso!");
        }
        catch(SQLException ex)
        {
            App.mensagem("ERRO", "Erro ao remover ingresso: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnNovoIngresso_Click(ActionEvent event) 
    {
        App.carregarCena("NovoIngresso");
    }
    
    private void preencherTabela()
    {
        tbvIngressos.getItems().clear();
        
        try
        {
            IngressoDAO dao = new IngressoDAO();
            ObservableList<Ingresso> lista = FXCollections.observableArrayList(
                    dao.listar("codEvento = " + evento.getCodEvento()));
            //lista.setAll(dao.listar(""));
            tbvIngressos.setItems(lista);
            
            colNome.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                    cellData.getValue().getPessoa().getNome()));
            colTotal.setCellValueFactory(new PropertyValueFactory("totalPago"));
            colMeiaEntrada.setCellValueFactory(new PropertyValueFactory("meiaEntrada"));
        }
        catch(SQLException ex)
        {
            App.mensagem("ERRO", "Erro ao preencher tabela: " + ex.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
