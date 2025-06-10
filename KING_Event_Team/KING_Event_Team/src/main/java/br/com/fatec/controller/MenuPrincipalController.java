/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author luann
 */
public class MenuPrincipalController implements Initializable 
{

    // Propriedades do FXML
    @FXML
    private VBox vbxCadastrar;
    @FXML
    private Button btnNovoEvento;
    @FXML
    private Button btnNovaCat;
    @FXML
    private Button btnNovoLocal;
    @FXML
    private VBox vbxGerenciar;
    @FXML
    private Button btnGerenEventos;
    @FXML
    private Button btnGerenCat;
    @FXML
    private Button btnGerenLocal;
    @FXML
    private Button btnFechar;
    @FXML
    private Button btnMin;
    @FXML
    private Button btnNovaPessoa;
    @FXML
    private Button btnGerenPessoas;
    @FXML
    private Button btnNovoExpo;
    @FXML
    private Button btnGerenExpo;

    
    // Métodos de Controller
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
    }

    @FXML
    private void btnNovoEvento_Click(ActionEvent event) 
    {
        App.carregarCena("NovoEvento");
    }

    @FXML
    private void btnNovaCat_Click(ActionEvent event) 
    {
        App.carregarCena("NovaCategoria");
    }

    @FXML
    private void btnNovoLocal_Click(ActionEvent event) 
    {
        App.carregarCena("NovaLocalizacao");
    }

    @FXML
    private void btnGerenEventos_Click(ActionEvent event) 
    {
        App.carregarCena("GerenEventos");
    }

    @FXML
    private void btnGerenCat_Click(ActionEvent event) 
    {
        App.carregarCena("GerenCategorias");
    }

    @FXML
    private void btnGerenLocal_Click(ActionEvent event) 
    {
        App.carregarCena("GerenLocais");
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
    private void btnNovaPessoa_Click(ActionEvent event) 
    {
        App.carregarCena("NovaPessoa");
    }

    @FXML
    private void btnGerenPessoas_Click(ActionEvent event) 
    {
        App.carregarCena("GerenPessoas");
    }

    @FXML
    private void btnNovoExpo_Click(ActionEvent event) 
    {
        App.carregarCena("NovoExpositor");
    }

    @FXML
    private void btnGerenExpo_Click(ActionEvent event) 
    {
        App.carregarCena("GerenExpositores");
    }
}
