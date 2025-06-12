/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.PessoaDAO;
import br.com.fatec.model.Pessoa;
import br.com.fatec.persistencia.Banco;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author luann
 */
public class GerenPessoasController implements Initializable
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
    private TableView<Pessoa> tbvPessoas;
    @FXML
    private TableColumn<Pessoa, String> colCPF;
    @FXML
    private TableColumn<Pessoa, String> colNome;
    @FXML
    private TableColumn<Pessoa, String> colEmail;
    @FXML
    private TableColumn<Pessoa, String> colTelefone;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnNovaPessoa;
    @FXML
    private Button btnPesquisar;
    @FXML
    private TextField txtBuscar;
    
    private String buscar;
    @FXML
    private ComboBox<String> cmbTipo;
    
    private String tipoBusca;
    
    private PreparedStatement pst;
    
    private ResultSet rs;
    
    private Pessoa pessoa;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        preencherTabela();
        cmbTipo.getItems().addAll("CPF", "Nome", "Email", "Telefone");
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
        App.carregarCena("MenuPrincipal");
    }

    @FXML
    private void btnEditar_Click(ActionEvent event)
    {
        Pessoa pessoa = tbvPessoas.getSelectionModel().selectedItemProperty().get();
        if(pessoa == null)
        {
            App.mensagem("AVISO", "Selecione uma Pessoa!", Alert.AlertType.WARNING);
            return;
        }
        
        NovaPessoaController.pesAEditar = pessoa;
        NovaPessoaController.isModoEdicao = true;
        App.carregarCena("NovaPessoa");
    }

    @FXML
    private void btnDeletar_Click(ActionEvent event) 
    {
        Pessoa pessoa = tbvPessoas.getSelectionModel().selectedItemProperty().get();
        if(pessoa == null)
        {
            App.mensagem("AVISO", "Selecione uma Pessoa!", Alert.AlertType.WARNING);
            return;
        }
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("CONFIRMAÇÃO");
        alerta.setHeaderText("Deseja excluir este item?");
        Optional<ButtonType> resultado = alerta.showAndWait();
        if(resultado.isPresent() && resultado.get() == ButtonType.OK){
          
            try
            {
                PessoaDAO dao = new PessoaDAO();
                dao.remover(pessoa);
                App.mensagem("SUCESSO", pessoa.getNome() + " foi removido(a) com sucesso!");   
            }
            catch(SQLException ex)
            {
                App.mensagem("ERRO", "Erro ao Deletar", Alert.AlertType.ERROR);
            }

            preencherTabela();
        }
        else{
            App.mensagem("VOLTAR", pessoa.getNome() + " não foi removido(a)!"); 
            preencherTabela();
        }
    }

    @FXML
    private void btnNovaPessoa_Click(ActionEvent event) 
    {
        App.carregarCena("NovaPessoa");
    }
    
    private void preencherTabela()
    {
        tbvPessoas.getItems().clear();
        
        try
        {
            PessoaDAO dao = new PessoaDAO();
            ObservableList<Pessoa> lista = FXCollections.observableArrayList(dao.listar(""));
            //lista.setAll(dao.listar(""));
            tbvPessoas.setItems(lista);
            
            colNome.setCellValueFactory(new PropertyValueFactory("nome"));
            colCPF.setCellValueFactory(new PropertyValueFactory("CPF"));
            colEmail.setCellValueFactory(new PropertyValueFactory("email"));
            colTelefone.setCellValueFactory(new PropertyValueFactory("telefone"));
        }
        catch(SQLException ex)
        {
            App.mensagem("ERRO", "Erro ao preencher tabela.", Alert.AlertType.ERROR);
        }
    }

    private void btnAtualizar_Click(ActionEvent event) 
    {
        preencherTabela();
    }

    @FXML
    private void btnPesquisar_click(ActionEvent event) {
        buscar = txtBuscar.getText();
        tipoBusca = cmbTipo.getValue();
        
        try{
        String sql = "SELECT * FROM Pessoa ";
            
        if(tipoBusca != null){
            sql += " WHERE " + tipoBusca;
            if(buscar.matches("\\d+")){
                sql += " = " + buscar + ";";
            }
            else{
                sql += " like '%" + buscar + "%';";
            }
        }
        else{
            App.mensagem("AVISO, Selecione um tipo de pesquisa!", Alert.AlertType.INFORMATION);
        }
            
        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);
        rs = pst.executeQuery();
            
        if(rs.next())
        {
        pessoa = new Pessoa();
            
        pessoa.setCPF(rs.getString("CPF"));
        pessoa.setNome(rs.getString("nome"));
        pessoa.setEmail(rs.getString("email"));
        pessoa.setTelefone(rs.getString("telefone"));
              
        }
        Banco.desconectar();
        rs.close();
        }
        catch(SQLException ex){
            
        }

        tbvPessoas.getItems().clear();
        
        ObservableList<Pessoa> listar = FXCollections.observableArrayList(pessoa);
        tbvPessoas.setItems(listar);
        colNome.setCellValueFactory(new PropertyValueFactory("nome"));
        colCPF.setCellValueFactory(new PropertyValueFactory("CPF"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colTelefone.setCellValueFactory(new PropertyValueFactory("telefone"));
        
    }
}
