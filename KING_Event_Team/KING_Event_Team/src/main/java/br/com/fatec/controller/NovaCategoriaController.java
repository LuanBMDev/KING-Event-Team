/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.CategoriaDAO;
import br.com.fatec.model.Categoria;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author luann
 */
public class NovaCategoriaController implements Initializable 
{

    // Propriedades do FXML
    @FXML
    private Button btnFechar;
    @FXML
    private Button btnMin;
    @FXML
    private Button btnVoltar;
    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtNomeCat;
    @FXML
    private Button btnCadCat;
    @FXML
    private Button btnLimpar;
    

    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    
    private Categoria categoria;
    
    public static boolean isModoEdicao = false;
    public static Categoria catAEditar;

    
    // Métodos de Controller
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        carregarModoGravacao();
    }    

    @FXML
    private void btnCadCat_Click(ActionEvent event) throws SQLException 
    {

       
       if(txtNomeCat.getText().isBlank())
        {
            App.mensagem("AVISO!", "PREENCHA O CAMPO OBRIGATÓRIO!", Alert.AlertType.WARNING);

            return;
        }

        try
        {
            Categoria c = new Categoria();
            c = carregarModel();

            CategoriaDAO dao = new CategoriaDAO();
            
            if (isModoEdicao)
            {
                // script do modo edição
                dao.alterar(c);
                App.mensagem("SUCESSO", c.getNomeCat() + " Alterado com sucesso!");
            }
            else
            {
                // script do modo cadastro
                dao.inserir(c);
                App.mensagem("SUCESSO", c.getNomeCat()+ " Cadastrado com sucesso!");
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

    @FXML
    private void btnLimpar_Click(ActionEvent event) 
    {
        limpar();
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
        desativarEdicao();
        App.voltarHierarquia("MenuPrincipal", "GerenCategorias", "NovoEvento");
    }

    private Categoria carregarModel(){
            Categoria model = new Categoria();
            if(isModoEdicao) model.setCodCat(catAEditar.getCodCat());
            model.setNomeCat(txtNomeCat.getText().trim());
            return model;
    }
    private void desativarEdicao()
    {
        isModoEdicao = false;
        catAEditar = null;
        carregarModoGravacao();
    }
    private void carregarModoGravacao()
    {
        if(isModoEdicao)
        {
            // Muda a aparência para se encaixar no modo de Edição 
            lblTitulo.setText("EDITAR " + catAEditar.getNomeCat().toUpperCase());
            btnCadCat.setText("GRAVAR ALTERAÇÕES");
            btnLimpar.setText("SAIR DA EDIÇÃO");
            txtNomeCat.setText(catAEditar.getNomeCat());
           
        }
        else
        {
            // Muda a aparência para se encaixar no modo de Cadastro
            lblTitulo.setText("NOVA CATEGORIA");
            btnCadCat.setText("NOVA CATEGORIA");
            btnLimpar.setText("LIMPAR");
        }
    }
    
    private void limpar()
    {
        txtNomeCat.clear();
        desativarEdicao();
    }
}
