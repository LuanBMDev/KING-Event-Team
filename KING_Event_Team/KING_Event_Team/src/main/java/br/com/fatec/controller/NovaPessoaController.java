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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author luann
 */
public class NovaPessoaController implements Initializable
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
    private TextField txtNome;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtCPF;
    @FXML
    private TextField txtTelefone;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnLimpar;
    
    public static boolean isModoEdicao = false;
    public static Pessoa pesAEditar;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        
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
        App.voltarHierarquia(App.getGerenPessoas()); 
    }

    @FXML
    private void btnCadastrar_Click(ActionEvent event) 
    {
        if(txtCPF.getText().isBlank() 
                || txtNome.getText().isBlank() 
                || txtEmail.getText().isBlank())
        {
            App.mensagem("AVISO!", "PREENCHA TODOS OS CAMPOS OBRIGATÓRIOS!", Alert.AlertType.WARNING);
            return;
        }
        
        if(txtTelefone.getText().isBlank()) txtTelefone.setText("");
        
        try
        {
            Pessoa p = new Pessoa();
            p.setCPF(txtCPF.getText());
            p.setNome(txtNome.getText());
            p.setEmail(txtEmail.getText());
            p.setTelefone(txtTelefone.getText());

            PessoaDAO dao = new PessoaDAO();
            dao.inserir(p);
            
            App.mensagem("SUCESSO", p.getNome() + " Cadastrado com sucesso!");
            limpar();
        }
        catch(SQLException ex)
        {
            App.mensagem("ERRO", "ERRO DE BANCO DE DADOS: " + ex.getMessage(),
                    Alert.AlertType.ERROR);
        }
        
    }

    @FXML
    private void btnLimpar_Click(ActionEvent event) 
    {
        limpar();
    }
    
    private void limpar()
    {
        txtCPF.setText("");
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
    }
}
