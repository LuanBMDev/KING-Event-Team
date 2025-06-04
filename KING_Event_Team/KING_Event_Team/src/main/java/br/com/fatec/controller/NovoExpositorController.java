/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author luann
 */
public class NovoExpositorController {

    @FXML
    private Button btnFechar;
    @FXML
    private Button btnMin;
    @FXML
    private Button btnVoltar;
    @FXML
    private Label lblTitulo;
    @FXML
    private ImageView imgLogo;
    @FXML
    private Button btnLogo;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCPFCNPJ;
    @FXML
    private TextField txtTelefone;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private Button btnCadExpo;
    @FXML
    private Button btnLimpar;

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
        App.changeScene(App.getGerenEvento()); 
    }

    @FXML
    private void btnLogo_Click(ActionEvent event) {
    }

    @FXML
    private void btnCadExpo_Click(ActionEvent event) {
    }

    @FXML
    private void btnLimpar_Click(ActionEvent event) {
    }
}
