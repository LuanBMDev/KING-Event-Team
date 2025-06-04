/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author luann
 */
public class NovoEventoController {

    @FXML
    private Button btnFechar;
    @FXML
    private Button btnMin;
    @FXML
    private Button btnVoltar;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtNomeEvento;
    @FXML
    private ComboBox<?> cmbLocal;
    @FXML
    private Button btnAddLocal;
    @FXML
    private ComboBox<?> cmbCategoria;
    @FXML
    private Button btnAddCat;
    @FXML
    private TextField txtDataInicio;
    @FXML
    private TextField txtDataFinal;
    @FXML
    private TextField txtPrecoPadrao;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private Button btnCriarEvento;
    @FXML
    private Button btnLimpar;
    

    @FXML
    private void btnAddLocal_Click(ActionEvent event) {
    }

    @FXML
    private void btnAddCat_Click(ActionEvent event) {
    }

    @FXML
    private void btnCriarEvento_Click(ActionEvent event) {
    }

    @FXML
    private void btnLimpar_Click(ActionEvent event) {
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
}
