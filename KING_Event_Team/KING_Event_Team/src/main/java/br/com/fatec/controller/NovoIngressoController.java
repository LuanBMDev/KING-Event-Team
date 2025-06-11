/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.IngressoDAO;
import br.com.fatec.model.Evento;
import br.com.fatec.model.Pessoa;
import br.com.fatec.persistencia.Banco;
import br.com.fatec.model.Ingresso;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author luann
 */
public class NovoIngressoController implements Initializable
{

    @FXML
    private Button btnFechar;
    @FXML
    private Button btnMin;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label txtNomeEvento;
    @FXML
    private ComboBox<Pessoa> cmbPessoa;
    @FXML
    private CheckBox chkMeiaEntrada;
    @FXML
    private Label txtValorFinal;
    @FXML
    private Button btnCadIngresso;
    @FXML
    private Button btnLimpar;
    @FXML
    private Button btnVoltar;
    
    private Ingresso ingresso;
    
    private PreparedStatement pst;
    
    private ResultSet rs;
    
    public static Evento evento;
    
    private int chk = 0;
    public static boolean isModoEdicao;
    public static Ingresso ingressoAEditar;
    
    private IngressoDAO ingressoDAO = new IngressoDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarPessoa();
        txtNomeEvento.setText(evento.getNomeEvento());
        txtValorFinal.setText(String.format("%.2f", evento.getPrecoPadrao()));
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
        App.voltarHierarquia("GerenEventos", "GerenIngressos");
    }

    @FXML
    private void btnCadIngresso_Click(ActionEvent event) {
        ingresso = new Ingresso(null, null);
        if(cmbPessoa != null){
            ingresso = carregarModel();
            try{
                if(isModoEdicao){
                    ingressoDAO.alterar(ingresso);
                    App.mensagem("SUCESSO!", "Ingresso alterado com sucesso!");
                }
                else{
                    ingressoDAO.inserir(ingresso);
                    App.mensagem("SUCESSO!", "Ingresso registrado com sucesso!");
                }
                limparDados();
            }
            catch(SQLException ex){
                
            }
        }
        else{
            App.mensagem("ERRO", "Selecione uma pessoa!");
        }
    }

    @FXML
    private void btnLimpar_Click(ActionEvent event) 
    {
        limparDados();
    }
    
        private void carregarPessoa(){
        try {
            Banco.conectar();
            String sql = "SELECT CPF, nome FROM pessoa";
            pst = Banco.obterConexao().prepareStatement(sql);
            rs = pst.executeQuery();
            
            ObservableList<Pessoa> listData = FXCollections.observableArrayList();
            
            while (rs.next()){
                var pessoa = new Pessoa();
                pessoa.setCPF(rs.getString("CPF"));
                pessoa.setNome(rs.getString("nome"));
                listData.add(pessoa);
            }
            cmbPessoa.setItems(listData);
        } catch (SQLException ex) {
            Logger.getLogger(NovoEventoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Ingresso carregarModel(){
        Ingresso model = new Ingresso(null, null);
        
        model.setPessoa(cmbPessoa.getValue());
        model.setEvento(evento);
        model.setTotalPago(Double.parseDouble(txtValorFinal.getText().replace(",", ".")));
        model.setMeiaEntrada(chk);
        
        return model;
    }
    
    private void limparDados(){
        cmbPessoa.setValue(null);
        chkMeiaEntrada.setSelected(false);
    }

    @FXML
    private void chkMeiaEntrada_Click(ActionEvent event) {
        if(chkMeiaEntrada.isSelected()){
            txtValorFinal.setText(String.format("%.2f", evento.getPrecoPadrao() / 2));
            chk = 1;
       }
        else{
            txtValorFinal.setText(String.format("%.2f", evento.getPrecoPadrao()));
            chk = 0;
        }
    }
}
