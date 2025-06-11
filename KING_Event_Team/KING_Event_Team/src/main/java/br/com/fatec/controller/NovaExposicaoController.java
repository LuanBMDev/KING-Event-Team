/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.ExposicaoDAO;
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
    public static boolean isModoEdicao = false;
    public static Exposicao expoAEditar;
    public static Evento evento;
    private PreparedStatement pst;
    private ResultSet rs;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        carregarExpositor();
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
        App.voltarHierarquia("GerenEventos", "GerenExposicoes");
    }
    private Exposicao carregarModel(){
            Exposicao model = new Exposicao(null, null);
            model.setDescricao(txtDescricao.getText());
            model.setExpositor(cmbExpositor.getValue());
            model.setEvento(evento);
            return model;
    }
    @FXML
    private void btnSalvar_Click(ActionEvent event) {
        exposicao = new Exposicao(null, null);
        if(txtDescricao.getText().isBlank()){
            App.mensagem("Erro","Por favor preencha todos os campos!");
        }
        else{
            try{
            Exposicao e = new Exposicao(null, null, null);
            e = carregarModel();

            ExposicaoDAO dao = new ExposicaoDAO();
            if (isModoEdicao)
            {
                // script do modo edição
                dao.alterar(e);
                App.mensagem("SUCESSO", "Alterado com sucesso!");
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
        //carregarModoGravacao();
    }
    private void carregarExpositor(){
        try {
            Banco.conectar();
            String sql = "SELECT codExpo, nomeFant FROM Expositor";
            pst = Banco.obterConexao().prepareStatement(sql);
            rs = pst.executeQuery();
            
            ObservableList<Expositor> listData = FXCollections.observableArrayList();
            
            while (rs.next()){
                var exp = new Expositor();
                exp.setCodExpo(rs.getInt("codExpo"));
                exp.setNomeFant(rs.getString("nomeFant"));
                listData.add(exp);
            }
            cmbExpositor.setItems(listData);
        } catch (SQLException ex) {
            Logger.getLogger(NovoEventoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
