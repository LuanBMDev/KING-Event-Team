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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author luann
 */
public class GerenEventosController {

    @FXML
    private Button btnFechar;
    @FXML
    private Button btnMin;
    @FXML
    private Button btnVoltar;
    @FXML
    private Label lblTitulo;
    @FXML
    private TableView<?> tbvEventos;
    @FXML
    private TableColumn<?, ?> colCodEvento;
    @FXML
    private TableColumn<?, ?> colNomeEvento;
    @FXML
    private TableColumn<?, ?> colDescEvento;
    @FXML
    private TableColumn<?, ?> colDataInicio;
    @FXML
    private TableColumn<?, ?> colDataFim;
    @FXML
    private TableColumn<?, ?> colStatus;
    @FXML
    private TableColumn<?, ?> colLocal;
    @FXML
    private TableColumn<?, ?> colCategoria;
    @FXML
    private TableColumn<?, ?> colIngressoPadrao;
    @FXML
    private TableColumn<?, ?> colTotalVendido;
    @FXML
    private Pane panBusca;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnNovoEvento;
    @FXML
    private Button btnCadVisitante;
    @FXML
    private Button btnGerenVisitantes;
    @FXML
    private Button btnCadExpositor;
    @FXML
    private Button btnGerenExpositores;

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
    private void btnDeletar_Click(ActionEvent event) {
    }

    @FXML
    private void btnEditar_Click(ActionEvent event) {
    }

    @FXML
    private void btnNovoEvento_Click(ActionEvent event) 
    {
        App.changeScene(App.getNovoEvento());
    }

    @FXML
    private void btnCadVisitante_Click(ActionEvent event) {
    }

    @FXML
    private void btnGerenVisitantes_Click(ActionEvent event) {
    }

    @FXML
    private void btnCadExpositor_Click(ActionEvent event) {
    }

    @FXML
    private void btnGerenExpositores_Click(ActionEvent event) {
    }
}
