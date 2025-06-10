package br.com.fatec;

import br.com.fatec.DAO.PessoaDAO;
import br.com.fatec.model.Pessoa;
import br.com.fatec.persistencia.Banco;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage stage;
    
    private static double x,y = 0;
    
    private static Scene scene;
    
    private static String cenaAnterior = null;
    private static String cenaAtual = "MenuPrincipal";

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        
        stage.initStyle(StageStyle.UNDECORATED);
        
        scene = new Scene(loadFXML("view/MenuPrincipal"));
        tornarTelaMovimentavel(scene);
        
        stage.setResizable(false);
        stage.setMaximized(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void mensagem(String titulo, String msg)
    {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensagem");
        alerta.setHeaderText(msg);
        alerta.setContentText("");

        alerta.showAndWait(); //exibe a mensage
    }
    
    public static void mensagem(String msg, Alert.AlertType tipo)
    {
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Mensagem");
        alerta.setHeaderText(msg);
        alerta.setContentText("");

        alerta.showAndWait(); //exibe a mensage
    }
    
    public static void mensagem(String titulo, String msg, Alert.AlertType tipo)
    {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(msg);
        alerta.setContentText("");

        alerta.showAndWait(); //exibe a mensage
    }
   
    public static void carregarCena(String nomeFXML)
    {
        cenaAnterior = cenaAtual;
        cenaAtual = nomeFXML;
        
        try
        {
            Scene cena = new Scene(loadFXML("view/" + nomeFXML));
            tornarTelaMovimentavel(cena);
            
            stage.setScene(cena);
        }
        catch(IOException ex)
        {
            mensagem("ERRO", "Impossível carregar a cena " + nomeFXML +": " + ex.getMessage(),
                    Alert.AlertType.ERROR);
        }
    }
    
    public static void voltarHierarquia(String padraoFXML, String paiFXML)
    {
        if (cenaAnterior.equals(paiFXML))
        {
            carregarCena(paiFXML);
        }
        else
        {
            carregarCena(padraoFXML);
        }
    }
    
    public static void voltarHierarquia(String padraoFXML, String paiFXML, String avoFXML)
    {
        if (cenaAnterior.equals(paiFXML))
        {
            carregarCena(paiFXML);
        }
        else if (cenaAnterior.equals(avoFXML))
        {
            carregarCena(avoFXML);
        }
        else
        {
            carregarCena(padraoFXML);
        }
    }
    
    public static void tornarTelaMovimentavel(Scene cena)
    {
        cena.setOnMousePressed(event -> {
           x = event.getSceneX();
           y = event.getSceneY();
        });
        
        cena.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
    }
    
    public static void tornarTelaMovimentavel(Scene cena, Stage outroStage)
    {
        cena.setOnMousePressed(event -> {
           x = event.getSceneX();
           y = event.getSceneY();
        });
        
        cena.setOnMouseDragged(event -> {
            outroStage.setX(event.getScreenX() - x);
            outroStage.setY(event.getScreenY() - y);
        });
    }

    
    public static Stage getStage() 
    {
        return stage;
    }
    
    public static Scene getScene() {
        return scene;
    }
    
    public static String getCenaAnterior() {
        return cenaAnterior;
    }

    public static String getCenaAtual() {
        return cenaAtual;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
        /*
        // TESTE DE BANCO
        try {
            System.out.println("Conectando ...");
            Banco.conectar();
            System.out.println("Conectado...");
            
            //tentando inserir uma pessoa
            Pessoa p = new Pessoa();
            p.setCPF("02103210");
            p.setEmail("pessoa@hotmail.com");
            p.setNome("pessoa");
            p.setTelefone("707070");
            
            PessoaDAO dao = new PessoaDAO();
            //System.out.println("INSERINDO DADOS NO BANCO");
            dao.inserir(p);
            
            System.out.println("Fechando...");
            Banco.desconectar();
            System.out.println("Fechado...");
        }
        catch(SQLException ex) {
            System.out.println("Erro de Banco: " + ex.getMessage());
        }
        */
    }

}