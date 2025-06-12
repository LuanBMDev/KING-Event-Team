/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.ExpositorDAO;
import br.com.fatec.DAO.PessoaDAO;
import br.com.fatec.model.Expositor;
import br.com.fatec.model.Pessoa;
import br.com.fatec.persistencia.Banco;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class GerenExpositoresController implements Initializable {

    @FXML
    private Button btnFechar;
    @FXML
    private Button btnMin;
    @FXML
    private Button btnVoltar;
    @FXML
    private Label lblTitulo;
    @FXML
    private TableView<Expositor> tbvExpositores;
    @FXML
    private TableColumn<Expositor, Integer> colCodigo;
    @FXML
    private TableColumn<Expositor, String> colLogo;
    @FXML
    private TableColumn<Expositor, String> colNome;
    @FXML
    private TableColumn<Expositor, String> colCPFCNPJ;
    @FXML
    private TableColumn<Expositor, String> colTelefone;
    @FXML
    private TableColumn<Expositor, String> colEmail;
    @FXML
    private Button btnNovoExpositor;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDeletar;
    @FXML
    private Button btnVerLogo;
    @FXML
    private ComboBox<String> combuscartipo;
    @FXML
    private TextField txtbuscar;
    @FXML
    private Button btnPesquisar;
    
    private String tipoBusca;
    public String buscar;
    private PreparedStatement pst;
    private ResultSet rs;
    private Expositor expositor;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        combuscartipo.getItems().addAll("codExpo","logoExpo","nomeFant","CPFCNPJ","telefoneExpo","emailExpo");
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
    private void btnNovoExpositor_Click(ActionEvent event) 
    {
        App.carregarCena("NovoExpositor");
    }

    @FXML
    private void btnEditar_Click(ActionEvent event) 
    {
        Expositor expositor = tbvExpositores.getSelectionModel().selectedItemProperty().get();
        if(expositor == null)
        {
            App.mensagem("AVISO", "Selecione um Expositor!", Alert.AlertType.WARNING);
            return;
        }
        
        NovoExpositorController.isModoEdicao = true;
        NovoExpositorController.expoAEditar = expositor;
        App.carregarCena("NovoExpositor");
    }

    @FXML
    private void btnDeletar_Click(ActionEvent event) 
    {
        Expositor expositor = tbvExpositores.getSelectionModel().selectedItemProperty().get();
        if(expositor == null)
        {
            App.mensagem("AVISO", "Selecione um Expositor!", Alert.AlertType.WARNING);
            return;
        }
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("CONFIRMAÇÃO");
        alerta.setHeaderText("Deseja excluir este item?");
        Optional<ButtonType> resultado = alerta.showAndWait();
        if(resultado.isPresent() && resultado.get() == ButtonType.OK){
            
        }
        try
        {
            ExpositorDAO dao = new ExpositorDAO();
            dao.remover(expositor);
            App.mensagem("SUCESSO", "Expositor removido com sucesso!");
        }
        catch(SQLException ex)
        {
            App.mensagem("ERRO", "Erro ao remover expositor.", Alert.AlertType.ERROR);
        }
        
        preencherTabela();
    }

    private void preencherTabela()
    {
        tbvExpositores.getItems().clear();
        
        try
        {
            ExpositorDAO dao = new ExpositorDAO();
            ObservableList<Expositor> lista = FXCollections.observableArrayList(dao.listar(""));
            //lista.setAll(dao.listar(""));
            tbvExpositores.setItems(lista);
            
            colCodigo.setCellValueFactory(new PropertyValueFactory("codExpo"));
            colLogo.setCellValueFactory(new PropertyValueFactory("logoExpo"));
            colNome.setCellValueFactory(new PropertyValueFactory("nomeFant"));
            colCPFCNPJ.setCellValueFactory(new PropertyValueFactory("CPFCNPJ"));
            colEmail.setCellValueFactory(new PropertyValueFactory("emailExpo"));
            colTelefone.setCellValueFactory(new PropertyValueFactory("telefoneExpo"));
        }
        catch(SQLException ex)
        {
            App.mensagem("ERRO", "Erro ao preencher tabela.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnVerLogo_Click(ActionEvent event) 
    {
        Expositor expositor = tbvExpositores.getSelectionModel().selectedItemProperty().get();
        if(expositor == null)
        {
            App.mensagem("AVISO", "Selecione um Expositor!", Alert.AlertType.WARNING);
            return;
        }
        
        try 
        {       
            Desktop.getDesktop().open(new File("//" + expositor.getLogoExpo()));
        }
        catch (IOException ex) 
        {
            Logger.getLogger(GerenExpositoresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void bntPesquisar_click(ActionEvent event) {
        buscar = txtbuscar.getText();
        tipoBusca = combuscartipo.getValue();
        try{
        String sql = "SELECT * FROM Expositor ";
            
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
            
        while(rs.next())
        {
            expositor = new Expositor();
            
            expositor.setCodExpo(rs.getInt("codExpo"));
            expositor.setNomeFant(rs.getString("nomeFant"));
            expositor.setCPFCNPJ(rs.getString("CPFCNPJ"));
            expositor.setLogoExpo(rs.getString("logoExpo"));
            expositor.setEmailExpo(rs.getString("emailExpo"));
            expositor.setTelefoneExpo(rs.getString("telefoneExpo"));
        }
        Banco.desconectar();
        rs.close();
        
        }
        catch(SQLException ex){
            
        }

        tbvExpositores.getItems().clear();
        
        ObservableList<Expositor> listar = FXCollections.observableArrayList(expositor);
        tbvExpositores.setItems(listar);
        colCodigo.setCellValueFactory(new PropertyValueFactory("codExpo"));
        colLogo.setCellValueFactory(new PropertyValueFactory("logoExpo"));
        colNome.setCellValueFactory(new PropertyValueFactory("nomeFant"));
        colCPFCNPJ.setCellValueFactory(new PropertyValueFactory("CPFCNPJ"));
        colEmail.setCellValueFactory(new PropertyValueFactory("emailExpo"));
        colTelefone.setCellValueFactory(new PropertyValueFactory("telefoneExpo"));
        
    }
}
