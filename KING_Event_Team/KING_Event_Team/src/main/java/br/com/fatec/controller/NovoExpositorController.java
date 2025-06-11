/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.ExpositorDAO;
import br.com.fatec.model.Expositor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author luann
 */
public class NovoExpositorController implements Initializable
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
    private Button btnCadExpo;
    @FXML
    private Button btnLimpar;
    
    private String caminhoLogo;
    public static boolean isModoEdicao;
    public static Expositor expoAEditar;

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
        App.voltarHierarquia("MenuPrincipal", "GerenExpositores");
    }

    @FXML
    private void btnLogo_Click(ActionEvent event) 
    {
        FileChooser explorer = new FileChooser();
        explorer.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Imagens", "*.jpg", "*.png",
                "*.jpeg"));
        File arquivo = explorer.showOpenDialog(new Stage());
        if(arquivo != null)
        {
            imgLogo.setImage(new Image("file:///" + arquivo.getAbsolutePath()));
            caminhoLogo = arquivo.getAbsolutePath();
        }
    }

    @FXML
    private void btnCadExpo_Click(ActionEvent event) 
    {
        if(txtNome.getText().isBlank() 
                || txtCPFCNPJ.getText().isBlank() 
                || txtEmail.getText().isBlank()
                || txtTelefone.getText().isBlank())
        {
            App.mensagem("AVISO!", "PREENCHA TODOS OS CAMPOS!", Alert.AlertType.WARNING);
            return;
        }
        
        try
        {
            Expositor expo = new Expositor();
            expo.setCPFCNPJ(txtCPFCNPJ.getText());
            expo.setNomeFant(txtNome.getText());
            expo.setEmailExpo(txtEmail.getText());
            expo.setTelefoneExpo(txtTelefone.getText());
            expo.setLogoExpo(caminhoLogo);
            
            ExpositorDAO dao = new ExpositorDAO();
            
            if(isModoEdicao)
            {
                expo.setCodExpo(expoAEditar.getCodExpo());
                dao.alterar(expo);
                App.mensagem("SUCESSO", "Expositor " + expo.getNomeFant() + " alterado com sucesso!");
            }
            else
            {
                dao.inserir(expo);
                App.mensagem("SUCESSO", "Expositor " + expo.getNomeFant() + " cadastrado com sucesso!");
            }
            
            limpar();
        }
        catch(SQLException ex)
        {
            if(isModoEdicao)
            {
                App.mensagem("ERRO", "Falha ao Alterar: " + ex.getMessage(), Alert.AlertType.ERROR);
            }
            else
            {
                App.mensagem("ERRO", "Falha ao Cadastrar: " + ex.getMessage(), Alert.AlertType.ERROR);
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
            String nomeExpositor[] = expoAEditar.getNomeFant().split(" ");
            lblTitulo.setText("EDITAR " + nomeExpositor[0].toUpperCase());
            btnCadExpo.setText("GRAVAR ALTERAÇÕES");
            btnLimpar.setText("SAIR DA EDIÇÃO");
            txtNome.setText(expoAEditar.getNomeFant());
            txtEmail.setText(expoAEditar.getEmailExpo());
            txtCPFCNPJ.setText(expoAEditar.getCPFCNPJ());
            txtCPFCNPJ.setDisable(true);
            txtTelefone.setText(expoAEditar.getTelefoneExpo());
            caminhoLogo = expoAEditar.getLogoExpo();
            imgLogo.setImage(new Image("file:///" + caminhoLogo));
        }
        else
        {
            // Muda a aparência para se encaixar no modo de Cadastro
            lblTitulo.setText("NOVO EXPOSITOR");
            txtCPFCNPJ.setDisable(false);
            btnCadExpo.setText("CADASTRAR EXPOSITOR");
            btnLimpar.setText("LIMPAR");
        }
    }
    
    private void desativarEdicao()
    {
        isModoEdicao = false;
        expoAEditar = null;
        carregarModoGravacao();
    }
    
    private void limpar()
    {
        txtNome.setText("");
        txtCPFCNPJ.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        App a = new App();
        imgLogo.setImage(a.definirImagem("TemplateLogo.jpg"));
        desativarEdicao();
    }
}
