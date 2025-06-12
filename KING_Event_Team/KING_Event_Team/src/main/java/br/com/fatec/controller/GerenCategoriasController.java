/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.CategoriaDAO;
import br.com.fatec.model.Categoria;
import br.com.fatec.persistencia.Banco;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
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
public class GerenCategoriasController implements Initializable{

    @FXML
    private Button btnFechar;
    @FXML
    private Button btnMin;
    @FXML
    private Button btnVoltar;
    @FXML
    private Label lblTitulo;
    @FXML
    private TableView<Categoria> tbvCategorias;
    @FXML
    private TableColumn<Categoria, String> colNomeCat;
    @FXML
    private TableColumn<Categoria, Integer> colCodCat;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnNovaCat;
    @FXML
    private ComboBox<String> cmbBuscar;
    @FXML
    private TextField txtBuscarTipo;
    @FXML
    private Button btnPesquisar;
    
    private String tipoBusca;
    public String buscar;
    private PreparedStatement pst;
    private ResultSet rs;
    private Categoria categoria;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbBuscar.getItems().addAll("codCat","nomeCat");
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
        App.carregarCena("MenuPrincipal");
    }

    @FXML
    private void btnEditar_Click(ActionEvent event) {
        Categoria categoria = tbvCategorias.getSelectionModel().selectedItemProperty().get();
        if(categoria == null)
        {
            App.mensagem("AVISO", "Selecione uma Pessoa!", Alert.AlertType.WARNING);
            return;
        }
        NovaCategoriaController.catAEditar = categoria;
        NovaCategoriaController.isModoEdicao = true;
        App.carregarCena("NovaCategoria");
    }

    @FXML
    private void btnDeletar_Click(ActionEvent event) 
    {
        Categoria categoria = tbvCategorias.getSelectionModel().selectedItemProperty().get();
        if(categoria == null)
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
                CategoriaDAO dao = new CategoriaDAO();
                dao.remover(categoria);
                App.mensagem("SUCESSO", categoria.getNomeCat() + " foi removido(a) com sucesso!");   
            }
            catch(SQLException ex)
            {
                App.mensagem("ERRO", "Erro ao Deletar", Alert.AlertType.ERROR);
            }

            preencherTabela();
        }
    }

    @FXML
    private void btnNovaCat_Click(ActionEvent event) 
    {
        App.carregarCena("NovaCategoria");
    }
    
    private void preencherTabela()
    {
        tbvCategorias.getItems().clear();
        
        try
        {
           CategoriaDAO dao = new CategoriaDAO();
           ObservableList<Categoria> lista = FXCollections.observableArrayList(dao.listar(""));
           tbvCategorias.setItems(lista);
           
           colNomeCat.setCellValueFactory(new PropertyValueFactory("nomeCat"));
           colCodCat.setCellValueFactory(new PropertyValueFactory("codCat"));
        }
        catch(SQLException ex)
        {
            App.mensagem("ERRO", "Erro ao preencher tabela.", Alert.AlertType.ERROR);      
        }
    }

    @FXML
    private void btnPesquisar_click(ActionEvent event) {
        buscar = txtBuscarTipo.getText();
        tipoBusca = cmbBuscar.getValue();
        
        try{
        String sql = "SELECT * FROM Categoria ";
            
        if(tipoBusca != null){
            sql += " WHERE " + tipoBusca;
            if(buscar.matches("\\d+")){
                sql += " like %" + buscar + "%;";
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
        categoria = new Categoria();
            
        categoria.setCodCat(rs.getInt("codCat"));
        categoria.setNomeCat(rs.getString("nomeCat"));
        
              
        }
        Banco.desconectar();
        rs.close();
        }
        catch(SQLException ex){
            
        }

        tbvCategorias.getItems().clear();
        
        ObservableList<Categoria> listar = FXCollections.observableArrayList(categoria);
        tbvCategorias.setItems(listar);
        colCodCat.setCellValueFactory(new PropertyValueFactory("codCat"));
        colNomeCat.setCellValueFactory(new PropertyValueFactory("nomeCat"));
                
    }
    
}
