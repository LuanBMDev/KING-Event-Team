/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.ExposicaoDAO;
import br.com.fatec.DAO.ExpositorDAO;
import br.com.fatec.model.Evento;
import br.com.fatec.model.Exposicao;
import br.com.fatec.model.Expositor;
import br.com.fatec.persistencia.Banco;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *
 * @author luann
 */
public class NovaExposicaoController implements Initializable
{

    @FXML
    private Button btnFechar;
    @FXML
    private Button btnMin;

    @FXML
    private Label lblTitulo;
    @FXML
    private ComboBox<Expositor> cmbExpositor;
    @FXML
    private TextArea txtDescricao;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnVoltar;
    
    private Exposicao exposicao;
    public static boolean isModoEdicao;
    private ExposicaoDAO dao = new ExposicaoDAO();;
    public static Exposicao expoAEditar;
    public static Evento evento;
    private PreparedStatement pst;
    private ResultSet rs;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        if (isModoEdicao) evento = expoAEditar.getEvento();
        carregarExpositor();
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
        voltar();
    }
    
    private void voltar()
    {
        desativarEdicao();
        App.voltarHierarquia("GerenEventos", "GerenExposicoes");
    }
    
    private Exposicao carregarModel(){
            Exposicao model = new Exposicao(null, null);
            
            model.setExpositor(cmbExpositor.getValue());
            model.setEvento(evento);
            
            model.setDescricao(txtDescricao.getText());
            
            debugModel(model);
            return model;
    }
    
    private void debugModel(Exposicao model)
    {
        App.mensagem(model.getExpositor().getCodExpo()+ "\n"
                + model.getExpositor().getNomeFant()+ "\n"
                + model.getExpositor().getEmailExpo()+ "\n"
                + model.getExpositor().getCPFCNPJ()+ "\n"
                + model.getExpositor().getTelefoneExpo() + "\n"
                + model.getExpositor().getLogoExpo(), Alert.AlertType.INFORMATION);
    }
    
    @FXML
    private void btnSalvar_Click(ActionEvent event) {
        exposicao = new Exposicao(null, null);
        if(txtDescricao.getText().isBlank()){
            App.mensagem("Erro","Por favor preencha todos os campos!");
        }
        else{
            try{
            Exposicao e = new Exposicao(null, null);
            e = carregarModel();
            
            if (isModoEdicao)
            {
                // script do modo edição
                debugModel(e);
                dao.alterar(e);
                App.mensagem("SUCESSO", "Alterado com sucesso!");
                voltar();
            }
            else
            {
                // script do modo cadastro
                dao.inserir(e);
                App.mensagem("SUCESSO", " Cadastrado com sucesso!");
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

    }
    
    private void limpar()
    {
        txtDescricao.clear();
        cmbExpositor.setValue(null);
        desativarEdicao();
    }
    
    private void desativarEdicao()
    {
        isModoEdicao = false;
        expoAEditar = null;
        carregarModoGravacao();
    }
    
    private void carregarModoGravacao()
    {
        if(isModoEdicao)
        {
            lblTitulo.setText("EDITAR EXPOSIÇÃO");
            lblTitulo.setFont(Font.font("System", FontWeight.BOLD, 32));
            
            txtDescricao.setText(expoAEditar.getDescricao());
            cmbExpositor.setValue(expoAEditar.getExpositor());
            cmbExpositor.setDisable(true);
        }
        else
        {
            lblTitulo.setText("NOVA EXPOSIÇÃO");
            lblTitulo.setFont(Font.font("System", FontWeight.BOLD, 36));
            cmbExpositor.setDisable(false);
        }
    }
    
    private void carregarExpositor()
    {
       ExpositorDAO expositorDAO = new ExpositorDAO();
       
       try
       {
           ObservableList<Expositor> listData = FXCollections.observableArrayList(
                   expositorDAO.listar(""));
           cmbExpositor.setItems(listData);
       }
       catch(SQLException ex)
       {
           App.mensagem("ERRO", "Erro ao popular Expositores: " + ex.getMessage(), Alert.AlertType.ERROR);
       }
    }

}
