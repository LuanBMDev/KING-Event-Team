/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.PessoaDAO;
import br.com.fatec.model.Pessoa;
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
public class GerenPessoasController implements Initializable
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
    private TableView<Pessoa> tbvPessoas;
    @FXML
    private TableColumn<Pessoa, String> colCPF;
    @FXML
    private TableColumn<Pessoa, String> colNome;
    @FXML
    private TableColumn<Pessoa, String> colEmail;
    @FXML
    private TableColumn<Pessoa, String> colTelefone;
    @FXML
    private Pane panBusca;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnNovaPessoa;
    @FXML
    private Button btnAtualizar;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
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
    }

    @FXML
    private void btnDeletar_Click(ActionEvent event) 
    {
        
    }

    @FXML
    private void btnNovaPessoa_Click(ActionEvent event) 
    {
        App.changeScene(App.getNovaPessoa());
    }
    
    private void preencherTabela()
    {
        tbvPessoas.getItems().clear();
        
        try
        {
            PessoaDAO dao = new PessoaDAO();
            ObservableList<Pessoa> lista = FXCollections.observableArrayList(dao.listar(""));
            //lista.setAll(dao.listar(""));
            tbvPessoas.setItems(lista);
            
            colNome.setCellValueFactory(new PropertyValueFactory("nome"));
            colCPF.setCellValueFactory(new PropertyValueFactory("CPF"));
            colEmail.setCellValueFactory(new PropertyValueFactory("email"));
            colTelefone.setCellValueFactory(new PropertyValueFactory("telefone"));
        }
        catch(SQLException ex)
        {
            App.mensagem("ERRO", "Erro ao preencher tabela.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnAtualizar_Click(ActionEvent event) 
    {
        preencherTabela();
    }
}
