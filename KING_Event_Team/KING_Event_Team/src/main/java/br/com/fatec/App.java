package br.com.fatec;

import br.com.fatec.persistencia.Banco;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage stage;
    
    private static Scene scene;
    private static Scene novoEvento;
    private static Scene novaCategoria;
    private static Scene novoLocal;
    private static Scene gerenEventos;
    private static Scene gerenCategorias;
    private static Scene gerenLocais;
    private static Scene novoVisitante;
    private static Scene novoExpositor;
    private static Scene gerenExpositores;
    
    private static Scene cenaAnterior;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        
        scene = new Scene(loadFXML("view/MenuPrincipal"));
        novoEvento = new Scene(loadFXML("view/NovoEvento"));
        novaCategoria = new Scene(loadFXML("view/NovaCategoria"));
        novoLocal = new Scene(loadFXML("view/NovaLocalizacao"));
        gerenCategorias = new Scene(loadFXML("view/GerenCategorias"));
        gerenLocais = new Scene(loadFXML("view/GerenLocais"));
        gerenEventos = new Scene(loadFXML("view/GerenEventos"));
        novoVisitante = new Scene(loadFXML("view/NovoVisitante"));
        novoExpositor = new Scene(loadFXML("view/NovoExpositor"));
        gerenExpositores = new Scene(loadFXML("view/GerenExpositores"));
        
        stage.setResizable(false);
        stage.setMaximized(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
   
    public static void changeScene(Scene cena)
    {
        cenaAnterior = stage.getScene();
        
        stage.setScene(cena);
    }
    
    /**
     * Volta para a cena pai de uma tela caso ela seja a última cena em que o usuário
     * esteve. Caso contrário, volta para o MenuPrincipal.
     * <br><br>
     * AVISO: Só utilize essa função em botões de voltar, nos quais sua tela pode ser 
     * acessada por outra tela, que não o MenuPrincipal.
     * 
     * @param cenaPai  Uma outra tela em que é possível acessar a tela atual, e que não seja o MenuPrincipal.
     */
    public static void voltarHierarquia(Scene cenaPai)
    {
        if (getCenaAnterior().equals(cenaPai))
        {
            changeScene(getCenaAnterior());
        }
        else
        {
            changeScene(getScene()); 
        }
    }
    
    /**
     * Volta para a cena pai de uma tela caso ela seja a última cena em que o usuário
     * esteve. Caso contrário, volta para a cena avô.
     * <br><br>
     * AVISO: Só utilize essa função em botões de voltar, nos quais sua tela pode ser 
     * acessada por outra tela, que não a cena avô.
     * 
     * @param cenaPai  Uma outra tela em que é possível acessar a tela atual, e que não seja a cena pai.
     * @param cenaAvo  Uma outra tela, na qual acessa-se a cena pai.
     */
    public static void voltarHierarquia(Scene cenaPai, Scene cenaAvo)
    {
        if (getCenaAnterior().equals(cenaPai))
        {
            changeScene(getCenaAnterior());
        }
        else
        {
            changeScene(cenaAvo); 
        }
    }

    public static Scene getScene() {
        return scene;
    }

    public static Scene getNovoEvento() {
        return novoEvento;
    }

    public static Scene getNovaCategoria() {
        return novaCategoria;
    }

    public static Scene getNovoLocal() {
        return novoLocal;
    }

    public static Scene getGerenEventos() {
        return gerenEventos;
    }

    public static Scene getGerenCategorias() {
        return gerenCategorias;
    }

    public static Scene getGerenLocais() {
        return gerenLocais;
    }

    public static Scene getNovoVisitante() {
        return novoVisitante;
    }

    public static Scene getNovoExpositor() {
        return novoExpositor;
    }

    public static Scene getGerenExpositores() {
        return gerenExpositores;
    }
    
    public static Scene getCenaAnterior() {
        return cenaAnterior;
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