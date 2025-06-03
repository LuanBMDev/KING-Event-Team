package br.com.fatec;

import br.com.fatec.DAO.ProprietarioDAO;
import br.com.fatec.model.Proprietario;
import br.com.fatec.persistencia.Banco;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import javafx.scene.paint.Color;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage stage;
    
    private static Scene scene;
    private static Scene novaCategoria;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        
        scene = new Scene(loadFXML("view/MenuPrincipal"));
        novaCategoria = new Scene(loadFXML("view/NovaCategoria"));
        
        stage.setResizable(false);
        stage.setMaximized(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void changeScene(String nomeCena)
    {
        switch(nomeCena)
        {
            case "MenuPrincipal":
                stage.setScene(scene);
                break;
            
            case "NovaCategoria":
                stage.setScene(novaCategoria);
                break;
        }
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
        try {
            /*
            System.out.println("Conectando ...");
            Banco.conectar();
            System.out.println("Conectado...");
            System.out.println("Fechando...");
            Banco.desconectar();
            System.out.println("Fechado...");
            
            
            //tentando inserir um prop.
            Proprietario p = new Proprietario(10, "Cristiane Souzas");
            ProprietarioDAO dao = new ProprietarioDAO();
            dao.inserir(p);
        }
        catch(SQLException ex) {
            System.out.println("Erro de Banco: " + ex.getMessage());
        }
        */
    }

}