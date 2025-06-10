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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author luann
 */
public class NovaLocalizacaoController implements Initializable
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
    private TextField txtNomeLocal;
    @FXML
    private TextField txtCEP;
    @FXML
    private TextField txtEndereco;
    @FXML
    private TextField txtNumero;
    @FXML
    private TextField txtCidade;
    @FXML
    private ComboBox<String> cmbTipo;
    @FXML
    private Button btnCadLocal;
    @FXML
    private Button btnLimpar;
    
    private LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();

    private Localizacao localizacao;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbTipo.getItems().addAll("Presencial", "Online");
    }
      
    @FXML
    private void btnCadLocal_Click(ActionEvent event) throws SQLException {
        localizacao = new Localizacao();
        if(validarDados()){
            localizacao = carregarModel();
            localizacaoDAO.inserir(localizacao);
            limparDados();
            mensagem("local registrado com sucesso!");
        }
    }

    @FXML
    private void btnLimpar_Click(ActionEvent event) {
        limparDados();
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
        App.voltarHierarquia("MenuPrincipal", "GerenLocais", "NovoEvento");
    }
    
    private boolean validarDados(){
        if(txtNomeLocal.getText().isEmpty() ||
           txtCEP.getText().isEmpty() ||
           txtEndereco.getText().isEmpty() ||
           txtNumero.getText().isEmpty() ||
           txtCidade.getText().isEmpty() ||
           cmbTipo.getValue() == null){
            mensagem("Por favor preencha todos os campos!");
            System.out.println("dados invalidos");
            return false;
        }
        else{
            System.out.println("dados validados com sucesso");
            return true;
        }
    }
        private void mensagem(String msg) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensagem");
        alerta.setHeaderText(msg);
        alerta.setContentText("");

        alerta.showAndWait();
    }
        private Localizacao carregarModel(){
            Localizacao model = new Localizacao();
            model.setNomeLocal(txtNomeLocal.getText().trim());
            model.setCEP(txtCEP.getText().trim());
            model.setEnderecoLocal(txtEndereco.getText().trim());
            model.setNumeroLocal((int) Long.parseLong(txtNumero.getText().trim()));
            model.setCidade(txtCidade.getText().trim());
            model.setTipoLocal(cmbTipo.getValue());
            
            return model;
        }
        
        private void limparDados(){
            txtNomeLocal.clear();
            txtCEP.clear();
            txtEndereco.clear();
            txtNumero.clear();
            txtCidade.clear();
            cmbTipo.setValue("");
        }
}
