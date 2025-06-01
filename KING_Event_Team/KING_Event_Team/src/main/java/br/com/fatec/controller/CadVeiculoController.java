/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.DAO.ProprietarioDAO;
import br.com.fatec.DAO.VeiculoDAO;
import br.com.fatec.model.Proprietario;
import br.com.fatec.model.Veiculo;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Aluno
 */
public class CadVeiculoController implements Initializable {

    @FXML
    private TextField txtPlaca;
    @FXML
    private TextField txtModelo;
    @FXML
    private TextField txtPreco;
    @FXML
    private TextField txtCodProprietario;
    @FXML
    private ComboBox<Proprietario> cbProprietario;
    @FXML
    private Button btnPesquisar;
    @FXML
    private Button btnGravar;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnFechar;

    //variaveis auxiliares
    private Proprietario proprietario;
    private Veiculo veiculo;
    private VeiculoDAO veiculoDAO = new VeiculoDAO();
    private ProprietarioDAO proprietarioDAO = new ProprietarioDAO();
    private boolean incluindo = true;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnPesquisar_Click(ActionEvent event) {
        if(txtPlaca.getText().length() > 0)
        {
            veiculo = new Veiculo(null);
            veiculo.setPlaca(txtPlaca.getText());
            
            try
            {
                veiculo = veiculoDAO.buscarID(veiculo);
            }
            catch(SQLException ex)
            {
                mensagem("Erro de busca no Veículo: " + ex.getMessage());
            }
            
            if(veiculo == null)
            {
                mensagem("Veículo não encontrado!!");
                return;
            }
            else
            {
                moveModelToTela(veiculo);
                incluindo = false;
            }
        }
    }

    @FXML
    private void btnGravar_Click(ActionEvent event) {
        
        //tem que validar os campos antes de gravar
        veiculo = moveTelaToModel();
        
        //vamos gravar a inclusão
        if(incluindo) {
            try {
                if(veiculoDAO.inserir(veiculo)) {
                    mensagem("Gravação efetuada com Sucesso!!!");
                    //precisa limpar os campos !!!!
                }
                else {
                    mensagem("Ocorreu erro na Gravação");
                }
            } catch (SQLException ex) {
                mensagem("Erro na Gravação do Veículo: " + 
                        ex.getMessage());
            }
        }
        else
        {
            try {
                if(veiculoDAO.alterar(veiculo)) {
                    mensagem("Alteração efetuada com Sucesso!!!");
                    //precisa limpar os campos !!!!
                }
                else {
                    mensagem("Ocorreu erro na Alteração");
                }
            } catch (SQLException ex) {
                mensagem("Erro na Alteração do Veículo: " + 
                        ex.getMessage());
            }
        }
    }

    /**
     * Este método transfere todos os dados da tela para um model
     * @return 
     */
    private Veiculo moveTelaToModel() {
        //cria o objeto
        proprietario = new Proprietario();
        proprietario.setCodProprietario(Integer.parseInt(
                txtCodProprietario.getText()));
        try {
            //gera o objeto de proprietario
            proprietario = proprietarioDAO.buscarID(proprietario);
        } catch (SQLException ex) {
            mensagem("Erro de Busca Proprietario " + 
                    ex.getMessage());
        }
        
        //cria o veiculo já com o proprietario
        veiculo = new Veiculo(proprietario);
        veiculo.setModelo(txtModelo.getText());
        veiculo.setValor(Double.parseDouble(txtPreco.getText()));
        veiculo.setPlaca(txtPlaca.getText());
        
        //devolve o veiculo
        return veiculo;
    }
    
    private void moveModelToTela(Veiculo v)
    {
        txtPlaca.setText(v.getPlaca());
        txtModelo.setText(v.getModelo());
        txtPreco.setText(String.valueOf(v.getValor()));
        txtCodProprietario.setText(String.valueOf(v.getProprietario().getCodProprietario()));
    }
    
    @FXML
    private void btnExcluir_Click(ActionEvent event) 
    {
        veiculo = new Veiculo(null);
        veiculo.setPlaca(txtPlaca.getText());
        
        // Tem que solicitar se o usuário confirma a exclusão
        try
        {
            if(veiculoDAO.remover(veiculo))
            {
                mensagem("Exclusão efetuada com sucesso!!");
                // Tem que limpar os campos
            }
            else
            {
                mensagem("Ocorreu erro na Exclusão!");
            }
        }
        catch(SQLException ex)
        {
            mensagem("Erro na Exclusão: " + ex.getMessage());
        }
    }

    @FXML
    private void btnFechar_Click(ActionEvent event) {
    }
    
    private void mensagem(String msg) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensagem");
        alerta.setHeaderText(msg);
        alerta.setContentText("");

        alerta.showAndWait(); //exibe a mensage
    }
}
