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
        carregarModoGravacao();
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
        desativarEdicao();
        App.voltarHierarquia("MenuPrincipal", "GerenPessoas");
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
            
            if (isModoEdicao)
            {
                // script do modo edição
                dao.alterar(p);
                App.mensagem("SUCESSO", p.getNome() + " Alterado com sucesso!");
            }
            else
            {
                // script do modo cadastro
                dao.inserir(p);
                App.mensagem("SUCESSO", p.getNome() + " Cadastrado com sucesso!");
            }
            
            limpar();
        }
        catch(SQLException ex)
        {
            if(isModoEdicao)
            {
                App.mensagem("ERRO", "Erro ao editar", Alert.AlertType.ERROR);
            }
            else
            {
                App.mensagem("ERRO", "Erro ao cadastrar", Alert.AlertType.ERROR);
            }
        }
        
    }

    @FXML
    private void btnLimpar_Click(ActionEvent event) 
    {
        limpar();
    }
    
    private void carregarModoGravacao()
    {
        if(isModoEdicao)
        {
            // Muda a aparência para se encaixar no modo de Edição 
            String nomePessoa[] = pesAEditar.getNome().split(" ");
            lblTitulo.setText("EDITAR " + nomePessoa[0].toUpperCase());
            btnCadastrar.setText("GRAVAR ALTERAÇÕES");
            btnLimpar.setText("SAIR DA EDIÇÃO");
            txtNome.setText(pesAEditar.getNome());
            txtEmail.setText(pesAEditar.getEmail());
            txtCPF.setText(pesAEditar.getCPF());
            txtCPF.setDisable(true);
            txtTelefone.setText(pesAEditar.getTelefone());
        }
        else
        {
            // Muda a aparência para se encaixar no modo de Cadastro
            lblTitulo.setText("NOVA PESSOA");
            txtCPF.setDisable(false);
            btnCadastrar.setText("CADASTRAR PESSOA");
            btnLimpar.setText("LIMPAR");
        }
    }
    
    private void desativarEdicao()
    {
        isModoEdicao = false;
        pesAEditar = null;
        carregarModoGravacao();
    }
    
    private void limpar()
    {
        txtCPF.setText("");
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        desativarEdicao();
    }
}
