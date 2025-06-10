/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.EventoDAO;
import br.com.fatec.model.Categoria;
import br.com.fatec.model.Localizacao;
import br.com.fatec.model.Evento;
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
public class NovoEventoController implements Initializable{

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
    private ComboBox<Localizacao> cmbLocal;
    @FXML
    private Button btnAddLocal;
    @FXML
    private ComboBox<Categoria> cmbCategoria;
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
    
    private EventoDAO eventoDAO = new EventoDAO();
    
    private Evento evento;
    
    private PreparedStatement pst;
    
    private ResultSet rs;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            carregarLocal();
            carregarCategoria();
    }
    
    @FXML
    private void btnAddLocal_Click(ActionEvent event) {
        App.changeScene(App.getNovoLocal());
    }

    @FXML
    private void btnAddCat_Click(ActionEvent event) {
        App.changeScene(App.getNovaCategoria());
    }

    @FXML
    private void btnCriarEvento_Click(ActionEvent event) {
        evento = new Evento(null, null);
        if(validarDados()){
            try {
                evento = carregarModel();
                eventoDAO.inserir(evento);
                limparDados();
            } catch (SQLException ex) {
                Logger.getLogger(NovoEventoController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
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
        App.voltarHierarquia(App.getGerenEventos());
    }
    
    private void limparDados(){
        txtNomeEvento.clear();
        cmbLocal.setValue(null);
        cmbCategoria.setValue(null);
        txtDataInicio.clear();
        txtDataFinal.clear();
        txtPrecoPadrao.clear();
        txtDescricao.clear();
        
    }
    
    private void carregarLocal(){
        try {
            Banco.conectar();
            String sql = "SELECT codLocal, nomeLocal FROM localizacao";
            pst = Banco.obterConexao().prepareStatement(sql);
            rs = pst.executeQuery();
            
            ObservableList<Localizacao> listData = FXCollections.observableArrayList();
            
            while (rs.next()){
                var local = new Localizacao();
                local.setNomeLocal(rs.getString("nomeLocal"));
                local.setCodLocal(rs.getInt("codLocal"));
                listData.add(local);
            }
            cmbLocal.setItems(listData);
        } catch (SQLException ex) {
            Logger.getLogger(NovoEventoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void carregarCategoria(){
        try {
            Banco.conectar();
            String sql = "SELECT codCat, nomeCat FROM categoria";
            pst = Banco.obterConexao().prepareStatement(sql);
            rs = pst.executeQuery();
            
            ObservableList<Categoria> listData = FXCollections.observableArrayList();
            
            while (rs.next()){
                var cat = new Categoria();
                cat.setCodCat(rs.getInt("codCat"));
                cat.setNomeCat(rs.getString("nomeCat"));
                listData.add(cat);
            }
            cmbCategoria.setItems(listData);
        } catch (SQLException ex) {
            Logger.getLogger(NovoEventoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Evento carregarModel(){
        Evento model = new Evento(null,null);
        model.setNomeEvento(txtNomeEvento.getText().trim());
        //model.setLocalizacao.getNomeLocal(cmbLocal.getValue());
        
        model.setLocalizacao(cmbLocal.getValue());
        
        
        //model.setCategoria((Categoria) cmbCategoria.getValue());
        
        model.setCategoria(cmbCategoria.getValue());
        
        model.setDataInicio(txtDataInicio.getText());
        model.setDataFim(txtDataFinal.getText());
        model.setPrecoPadrao(Double.parseDouble(txtPrecoPadrao.getText().trim()));
       
        return model;
    }
    
    private boolean validarDados(){
        if(txtNomeEvento.getText().isEmpty() ||
           cmbLocal.getValue() == null ||
           cmbCategoria.getValue() == null ||
           txtDataInicio.getText().isEmpty() ||
           txtDataFinal.getText().isEmpty() ||
           txtPrecoPadrao.getText().isEmpty() ||
           txtDescricao.getText().isEmpty()){
            App.mensagem("Erro","Por favor preencha todos os campos!");
            return false;
        }else{
            System.out.println("dados validados com sucesso!");
            return true;
        }
    }
    
    
}
