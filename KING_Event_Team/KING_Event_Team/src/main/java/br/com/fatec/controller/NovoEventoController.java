/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.EventoDAO;
import br.com.fatec.exceptions.PeriodoEventoInvalidoException;
import br.com.fatec.model.Categoria;
import br.com.fatec.model.Localizacao;
import br.com.fatec.model.Evento;
import br.com.fatec.persistencia.Banco;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import javafx.scene.control.Alert;
/**
 *
 * @author luann 
 */
public class NovoEventoController implements Initializable
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
    private TextField txtPrecoPadrao;
    @FXML
    private Button btnCriarEvento;
    @FXML
    private Button btnLimpar;
    
    private EventoDAO eventoDAO = new EventoDAO();
    
    private Evento evento;
    
    private PreparedStatement pst;
    
    private ResultSet rs;
    @FXML
    private DatePicker dateInicio;
    @FXML
    private DatePicker dateFim;
    
    private String dataFimL;
    private String dataInicioL;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    // MORTE IMINENTE
    private String dataAtual;
    private String status;
    
    public static boolean isModoEdicao;
    public static Evento eventoAEditar;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        carregarLocal();
        carregarCategoria();
        carregarModoGravacao();
    }
    
   public void selecionaDataInicio(ActionEvent event) 
   {
        LocalDate dataInicio = dateInicio.getValue();

        if (dataInicio != null) 
        {
            dataInicioL = dataInicio.format(formatter); // supondo que dataInicioL é um atributo da classe
            System.out.println(dataInicioL);
        } 
        else 
        {
            System.out.println("Nenhuma data de início foi selecionada.");
        }
    }
   public void selecionaDataFim(ActionEvent event){
    LocalDate dataFim = dateFim.getValue();
    
    if (dataFim != null) {
        dataFimL = dataFim.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(dataFimL);
    } else {
        System.out.println("Nenhuma data de fim foi selecionada.");
    }
}

    
    @FXML
    private void btnAddLocal_Click(ActionEvent event) {
        App.carregarCena("NovaLocalizacao");
    }

    @FXML
    private void btnAddCat_Click(ActionEvent event) {
        App.carregarCena("NovaCategoria");
    }

    @FXML
    private void btnCriarEvento_Click(ActionEvent event) {
        evento = new Evento(null, null);

        if(validarDados()){
            try {
                //carregarDatas();
                evento = carregarModel();
                
                if(isModoEdicao)
                {
                    eventoDAO.alterar(evento);
                    App.mensagem("SUCESSO", "Evento alterado com sucesso!");
                }
                else
                {
                    eventoDAO.inserir(evento);
                    App.mensagem("SUCESSO", "Evento criado com sucesso!");
                }
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
        desativarEdicao();
        App.voltarHierarquia("MenuPrincipal", "GerenEventos");
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
        
        if(isModoEdicao) model.setCodEvento(eventoAEditar.getCodEvento());
        System.out.println("Código: " + model.getCodEvento());
        
        model.setNomeEvento(txtNomeEvento.getText().trim());
        System.out.println("Nome do evento: " + model.getNomeEvento());
        
        model.setLocalizacao(cmbLocal.getValue());
        System.out.println("Local selecionado: " + model.getLocalizacao().getNomeLocal());
        
        model.setCategoria(cmbCategoria.getValue());
        System.out.println("Categoria selecionada: " + model.getCategoria().getNomeCat());
        
        model.setDataInicio(String.valueOf(dateInicio.getValue()));
        System.out.println("Data início: " + model.getDataInicio());
        
        model.setDataFim(String.valueOf(dateFim.getValue()));
        System.out.println("Data fim: " + model.getDataFim());
        
        model.setPrecoPadrao(Double.parseDouble(txtPrecoPadrao.getText().trim()));
        System.out.println("Preço: " + String.valueOf(model.getPrecoPadrao()));
        
        try
        {
            model.setStatusEvento(carregarDatas());
            System.out.println("Status: " + model.getStatusEvento());
        }
        catch(PeriodoEventoInvalidoException ex)
        {
            App.mensagem("DATA IMPOSSÍVEL", ex.getMessage(), Alert.AlertType.ERROR);
            //limparDados();
            dateInicio.setValue(null);
            dateFim.setValue(null);
        }
                
        return model;
    }
    
    private boolean validarDados(){
        if(txtNomeEvento.getText().isEmpty() ||
           cmbLocal.getValue() == null ||
           cmbCategoria.getValue() == null ||
           dateInicio.getValue() == null ||
           dateFim.getValue() == null ||
           txtPrecoPadrao.getText().isEmpty()){
            App.mensagem("Erro","Por favor preencha todos os campos!");
            return false;
        }else{
            System.out.println("dados validados com sucesso!");
            return true;
        }
    }  
   private String carregarDatas() throws PeriodoEventoInvalidoException
   {
        String status = null;
        LocalDate atual = LocalDate.now();
        LocalDate inicio = LocalDate.parse(String.valueOf(dateInicio.getValue()),formatter);
        LocalDate fim = LocalDate.parse(String.valueOf(dateFim.getValue()),formatter);
            
        if(inicio.isAfter(fim))       
        {
            throw new PeriodoEventoInvalidoException("O dia final foi configurado para uma data anterior ao dia inicial.");
        }
        else if(atual.isBefore(inicio))
        {
            //set pendente no status
            status = "PENDENTE";
        }
        else if(atual.isAfter(fim)){
            //set encerrado status 
            status = "ENCERRADO";
        }
        else
        { 
            //set emandamento status
            status = "EM ANDAMENTO";
        }       
       return status;
            
    }
   
   private void carregarModoGravacao()
   {
       if(isModoEdicao)
        {
            //App.mensagem("POR FAVOR(2)", "Editando Código: " + eventoAEditar.getCodEvento());
            // Muda a aparência para se encaixar no modo de Edição 
            lblTitulo.setText("EDITAR EVENTO");
            btnCriarEvento.setText("ALTERAR");
            btnLimpar.setText("SAIR DA EDIÇÃO");
            txtNomeEvento.setText(eventoAEditar.getNomeEvento());
            txtPrecoPadrao.setText(String.valueOf(eventoAEditar.getPrecoPadrao()));
            cmbCategoria.setValue(eventoAEditar.getCategoria());
            cmbLocal.setValue(eventoAEditar.getLocalizacao());
            dateInicio.setValue(LocalDate.parse(eventoAEditar.getDataInicio(), formatter));
            dateFim.setValue(LocalDate.parse(eventoAEditar.getDataFim(), formatter));
        }
        else
        {
            // Muda a aparência para se encaixar no modo de Cadastro
            lblTitulo.setText("NOVO EVENTO");
            btnCriarEvento.setText("NOVO EVENTO");
            btnLimpar.setText("LIMPAR");
        }
   }
   
   private void desativarEdicao()
   {
       isModoEdicao = false;
       eventoAEditar = null;
       carregarModoGravacao();
   }
   
   private void limparDados()
   {
        txtNomeEvento.clear();
        cmbLocal.setValue(null);
        cmbCategoria.setValue(null);
        dateFim.setValue(null);
        dateInicio.setValue(null);
        txtPrecoPadrao.clear();
        desativarEdicao();
    }
}
