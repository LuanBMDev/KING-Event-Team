/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.CategoriaDAO;
import br.com.fatec.model.Categoria;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author luann
 */
public class NovaCategoriaController implements Initializable 
{

    // Propriedades do FXML
    @FXML
    private Button btnFechar;
    @FXML
    private Button btnMin;
    @FXML
    private Button btnVoltar;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtNomeCat;
    @FXML
    private Button btnCadCat;
    @FXML
    private Button btnLimpar;
    

    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    
    private Categoria categoria;

    
    // Métodos de Controller
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    

    @FXML
    private void btnCadCat_Click(ActionEvent event) throws SQLException 
    {

       categoria = new Categoria();
       if(txtNomeCat.getText().isEmpty()){
            mensagem("Por favor preencha todo o campo!");
            System.out.println("dado invalido");
       }    
       else{
           categoria = carregarModel();
           categoriaDAO.inserir(categoria);
           mensagem("categoria criada com sucesso");
           System.out.println("dado valido");
           txtNomeCat.clear();
       }
       
       

    }

    @FXML
    private void btnLimpar_Click(ActionEvent event) 
    {
        txtNomeCat.setText("");
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
        App.voltarHierarquia("MenuPrincipal", "GerenCategorias", "NovoEvento");
    }

    private void mensagem(String msg) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensagem");
        alerta.setHeaderText(msg);
        alerta.setContentText("");

        alerta.showAndWait();
    }

    private Categoria carregarModel(){
            Categoria model = new Categoria();
            model.setNomeCat(txtNomeCat.getText().trim());
            return model;
    }
    

}
