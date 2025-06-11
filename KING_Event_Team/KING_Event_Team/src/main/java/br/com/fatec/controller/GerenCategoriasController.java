/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.CategoriaDAO;
import br.com.fatec.model.Categoria;
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
public class GerenCategoriasController implements Initializable{

    @FXML
    private Button btnFechar;
    @FXML
    private Button btnMin;
    @FXML
    private Button btnVoltar;
    @FXML
    private Label lblTitulo;
    @FXML
    private TableView<Categoria> tbvCategorias;
    @FXML
    private TableColumn<Categoria, String> colNomeCat;
    @FXML
    private TableColumn<Categoria, Integer> colCodCat;
    @FXML
    private Pane panBusca;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnNovaCat;
    

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
    private void btnEditar_Click(ActionEvent event) {
    
    }

    @FXML
    private void btnDeletar_Click(ActionEvent event) {
   
    }

    @FXML
    private void btnNovaCat_Click(ActionEvent event) 
    {
        App.carregarCena("NovaCategoria");
    }
    
    private void preencherTabela()
    {
        tbvCategorias.getItems().clear();
        
        try
        {
           CategoriaDAO dao = new CategoriaDAO();
           ObservableList<Categoria> lista = FXCollections.observableArrayList(dao.listar(""));
           tbvCategorias.setItems(lista);
           
           colNomeCat.setCellValueFactory(new PropertyValueFactory("nomeCat"));
           colCodCat.setCellValueFactory(new PropertyValueFactory("codCat"));
        }
        catch(SQLException ex)
        {
            App.mensagem("ERRO", "Erro ao preencher tabela.", Alert.AlertType.ERROR);      
        }
    }
}
