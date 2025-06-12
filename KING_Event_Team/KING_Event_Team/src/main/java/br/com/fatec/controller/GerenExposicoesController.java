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
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author luann
 */
public class GerenExposicoesController implements Initializable
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
    private TableView<Exposicao> tbvExposicoes;
    @FXML
    private TableColumn<Exposicao, String> colNomeExpo;
    @FXML
    private TableColumn<Exposicao, String> colDescExpo;
    @FXML
    private Button btnNovaExpo;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDeletar;
   
    public static Evento evento;
    private ExposicaoDAO dao = new ExposicaoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preencherTabela();
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
        App.carregarCena("GerenEventos");
    }

    @FXML
    private void btnNovaExpo_Click(ActionEvent event) 
    {
        App.carregarCena("NovaExposicao");
    }

    @FXML
    private void btnEditar_Click(ActionEvent event) 
    {
        Exposicao exposicao = tbvExposicoes.getSelectionModel().selectedItemProperty().get();
        if(exposicao == null)
        {
            App.mensagem("AVISO", "Selecione uma Exposição!", Alert.AlertType.WARNING);
            return;
        }

        //ExpositorDAO expoDAO = new ExpositorDAO();

        exposicao.setEvento(evento);
        NovaExposicaoController.expoAEditar = exposicao;
        NovaExposicaoController.isModoEdicao = true;
        App.carregarCena("NovaExposicao");
    }

    @FXML
    private void btnDeletar_Click(ActionEvent event)
    {
        Exposicao exposicao = tbvExposicoes.getSelectionModel().selectedItemProperty().get();
        if(exposicao == null)
        {
            App.mensagem("AVISO", "Selecione uma Exposição!", Alert.AlertType.WARNING);
            return;
        }
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("CONFIRMAÇÃO");
        alerta.setHeaderText("Deseja excluir este item?");
        Optional<ButtonType> resultado = alerta.showAndWait();
        if(resultado.isPresent() && resultado.get() == ButtonType.OK){
        
        exposicao.setEvento(evento);
        try
        {
            dao.remover(exposicao);
            App.mensagem("SUCESSO", "Deletado com sucesso!");
        }
        catch(SQLException ex)
        {
            App.mensagem("ERRO", "Falha ao deletar: " + ex.getMessage());
        }
        
        preencherTabela();
        }
    }
    
    private void preencherTabela(){
        tbvExposicoes.getItems().clear();
        
        try
        {
            ExposicaoDAO dao = new ExposicaoDAO();
            ObservableList<Exposicao> lista = FXCollections.observableArrayList(
                    dao.listar("codEvento = " + evento.getCodEvento()));
            tbvExposicoes.setItems(lista);
            
            colNomeExpo.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(
                   cellData.getValue().getExpositor().getNomeFant()));
            colDescExpo.setCellValueFactory(new PropertyValueFactory("descricao"));
            
        }
        catch(SQLException ex)
        {
            App.mensagem("ERRO", "Erro ao preencher tabela."+ ex.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
