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
    private TextField txtEstado;
    @FXML
    private ComboBox<String> cmbTipo;
    @FXML
    private Button btnCadLocal;
    @FXML
    private Button btnLimpar;
    
    private LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();

    private Localizacao localizacao;
    
    public static boolean isModoEdicao = false;
    public static Localizacao localAEditar;
    
    //public static int codLocal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        cmbTipo.getItems().addAll("Presencial", "Online");
        carregarModoGravacao();
    }
      
    @FXML
    private void btnCadLocal_Click(ActionEvent event)
    {
        localizacao = new Localizacao();
        if(validarDados())
        {
            localizacao = carregarModel();
            try{
                if(isModoEdicao){
                    localizacaoDAO.alterar(localizacao);
                    App.mensagem("SUCESSO!", "Local alterado com sucesso!");
                }
                else{
                    localizacaoDAO.inserir(localizacao);
                    App.mensagem("SUCESSO!", "Local registrado com sucesso!");
                }
                limparDados();
            }
            catch(SQLException ex){
                if (isModoEdicao)
                {
                    App.mensagem("ERRO", "Erro ao alterar: " + ex.getMessage(), Alert.AlertType.ERROR);
                }
                else
                {
                    App.mensagem("ERRO", "Erro ao cadastrar: " + ex.getMessage(), Alert.AlertType.ERROR);
                }
            }
        }
    }

    @FXML
    private void btnLimpar_Click(ActionEvent event) 
    {
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
        if(txtNomeLocal.getText().isBlank()||
           txtEndereco.getText().isBlank() ||
           cmbTipo.getValue() == null)
        {
           App.mensagem("AVISO", "Por favor preencha todos os campos obrigatórios (*)!", Alert.AlertType.WARNING);System.out.println("dados invalidos");
           return false;
        }
        else
        {
            System.out.println("dados validados com sucesso");
            if (txtCidade.getText().isBlank()) txtCidade.clear();
            if (txtCEP.getText().isBlank()) txtCEP.clear();
            if (txtEstado.getText().isBlank()) txtEstado.clear();
            if (txtNumero.getText().isBlank()) txtNumero.clear();
            return true;
        }
    }
    
    private Localizacao carregarModel()
    {
        Localizacao model = new Localizacao();
        model.setNomeLocal(txtNomeLocal.getText().trim());
        model.setCEP(txtCEP.getText().trim());
        model.setEnderecoLocal(txtEndereco.getText().trim());
        model.setNumeroLocal(txtNumero.getText().trim());
        model.setCidade(txtCidade.getText().trim());
        model.setEstado(txtEstado.getText().trim());
        model.setTipoLocal(cmbTipo.getValue());
        
        if(isModoEdicao) model.setCodLocal(localAEditar.getCodLocal());
        
        return model;
    }
        
    private void limparDados()
    {
        txtNomeLocal.clear();
        txtCEP.clear();
        txtEndereco.clear();
        txtNumero.clear();
        txtCidade.clear();
        txtEndereco.clear();
        txtEstado.clear();
        cmbTipo.setValue("");
        desativarEdicao();
    }
    
    private void carregarModoGravacao(){
        if(isModoEdicao){
            lblTitulo.setText("EDITAR LOCAL");
            btnCadLocal.setText("GRAVAR ALTERAÇÕES");
            btnLimpar.setText("SAIR DA EDIÇÃO");
            txtNomeLocal.setText(localAEditar.getNomeLocal());
            txtCEP.setText(localAEditar.getCEP());
            txtEndereco.setText(localAEditar.getEnderecoLocal());
            txtNumero.setText(localAEditar.getNumeroLocal());
            txtCidade.setText(localAEditar.getCidade());
            txtEstado.setText(localAEditar.getEstado());
            cmbTipo.setValue(localAEditar.getTipoLocal());
            
        }
        else{
            lblTitulo.setText("NOVA LOCALIZACAO");
            btnCadLocal.setText("CADASTRAR LOCAL");
            btnLimpar.setText("LIMPAR");
            
        }
    }
    
    private void desativarEdicao()
    {
        isModoEdicao = false;
        localAEditar = null;
        carregarModoGravacao();
    }
    
    
}
