/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.LocalizacaoDAO;
import br.com.fatec.model.Localizacao;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
public class GerenLocaisController implements Initializable{

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
    private TableColumn<Localizacao, String> colNomeLocal;
    @FXML
    private TableColumn<Localizacao, String> colEndereco;
    @FXML
    private TableColumn<Localizacao, Integer> colNumero;
    @FXML
    private TableColumn<Localizacao, String> colCidade;
    @FXML
    private TableColumn<Localizacao, String> colCEP;
    @FXML
    private TableColumn<Localizacao, String> colTipo;
    @FXML
    private Pane panBusca;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnNovoLocal;
    @FXML
    private Button btnAtualizar;

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
        // Por padrão ele vai pro Menu principal, mas altere dependendo da situação
        App.changeScene(App.getScene()); 
    }

    @FXML
    private void btnEditar_Click(ActionEvent event) {
        Localizacao localizacao = tbvLocais.getSelectionModel().selectedItemProperty().get();
        if(localizacao == null){
            App.mensagem("AVISO", "Selecione uma Localizacao!", Alert.AlertType.WARNING);
            return;
        }
        
    }

    @FXML
    private void btnDeletar_Click(ActionEvent event) {
        Localizacao localizacao = tbvLocais.getSelectionModel().selectedItemProperty().get();
        if(localizacao == null){
            App.mensagem("AVISO", "Selecione uma Localizacao!", Alert.AlertType.WARNING);
            return;
        }
        
        try{
            LocalizacaoDAO dao = new LocalizacaoDAO();
            dao.remover(localizacao);
            App.mensagem("Sucesso", localizacao.getNomeLocal()+"foi removido com sucesso!");
            preencherTabela();
        }
        catch(SQLException ex){
             App.mensagem("ERRO", "Erro ao Deletar", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnNovoLocal_Click(ActionEvent event) 
    {
        App.changeScene(App.getNovoLocal());
    }
    
    private void preencherTabela(){
        tbvLocais.getItems().clear();
        
        try{
            LocalizacaoDAO dao = new LocalizacaoDAO();
            ObservableList<Localizacao> lista = FXCollections.observableArrayList(dao.listar(""));
            tbvLocais.setItems(lista);
            
            colNomeLocal.setCellValueFactory(new PropertyValueFactory("nomeLocal"));
            colEndereco.setCellValueFactory(new PropertyValueFactory("enderecoLocal"));
            colNumero.setCellValueFactory(new PropertyValueFactory("numeroLocal"));
            colCidade.setCellValueFactory(new PropertyValueFactory("cidade"));
            colCEP.setCellValueFactory(new PropertyValueFactory("CEP"));
            colTipo.setCellValueFactory(new PropertyValueFactory("tipoLocal"));
            
        }
        catch(SQLException ex){
            App.mensagem("ERRO", "Erro ao preencher tabela.", Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void btnAtualizar_Click(ActionEvent event){
        preencherTabela();
    }
}
