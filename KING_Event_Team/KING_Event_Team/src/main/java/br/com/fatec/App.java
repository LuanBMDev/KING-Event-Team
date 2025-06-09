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
    private static Scene novoEvento;
    private static Scene novaCategoria;
    private static Scene novoLocal;
    private static Scene gerenEventos;
    private static Scene gerenCategorias;
    private static Scene gerenLocais;
    private static Scene novaPessoa;
    private static Scene gerenPessoas;
    private static Scene novoExpositor;
    private static Scene gerenExpositores;
    private static Scene novoIngresso;
    private static Scene gerenIngressos;
    private static Scene novaExposicao;
    private static Scene gerenExposicoes;
    
    private static Scene cenaAnterior;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        
        stage.initStyle(StageStyle.UNDECORATED);
        
        
        scene = new Scene(loadFXML("view/MenuPrincipal"));
        tornarTelaMovimentavel(scene);
        
        novoEvento = new Scene(loadFXML("view/NovoEvento"));
        tornarTelaMovimentavel(novoEvento);
        
        novaCategoria = new Scene(loadFXML("view/NovaCategoria"));
        tornarTelaMovimentavel(novaCategoria);
        
        novoLocal = new Scene(loadFXML("view/NovaLocalizacao"));
        tornarTelaMovimentavel(novoLocal);
        
        gerenCategorias = new Scene(loadFXML("view/GerenCategorias"));
        tornarTelaMovimentavel(gerenCategorias);
        
        gerenLocais = new Scene(loadFXML("view/GerenLocais"));
        tornarTelaMovimentavel(gerenLocais);
        
        gerenEventos = new Scene(loadFXML("view/GerenEventos"));
        tornarTelaMovimentavel(gerenEventos);
        
        novaPessoa = new Scene(loadFXML("view/NovaPessoa"));
        tornarTelaMovimentavel(novaPessoa);
        
        gerenPessoas = new Scene(loadFXML("view/GerenPessoas"));
        tornarTelaMovimentavel(gerenPessoas);
        
        novoExpositor = new Scene(loadFXML("view/NovoExpositor"));
        tornarTelaMovimentavel(novoExpositor);
        
        gerenExpositores = new Scene(loadFXML("view/GerenExpositores"));
        tornarTelaMovimentavel(gerenExpositores);
        
        novoIngresso = new Scene(loadFXML("view/NovoIngresso"));
        tornarTelaMovimentavel(novoIngresso);
        
        gerenIngressos = new Scene(loadFXML("view/GerenIngressos"));
        tornarTelaMovimentavel(gerenIngressos);
        
        novaExposicao = new Scene(loadFXML("view/NovaExposicao"));
        tornarTelaMovimentavel(novaExposicao);
        
        gerenExposicoes = new Scene(loadFXML("view/GerenExposicoes"));
        tornarTelaMovimentavel(gerenExposicoes);
        
        stage.setResizable(false);
        stage.setMaximized(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
        private void mensagem(String msg) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensagem");
        alerta.setHeaderText(msg);
        alerta.setContentText("");

        alerta.showAndWait(); //exibe a mensage
    }
   
    public static void changeScene(Scene cena)
    {
        cenaAnterior = stage.getScene();
        
        stage.setScene(cena);
    }
    
    public static void addScene(Scene cena)
    {
        Stage stage2 = new Stage();
        stage2.initStyle(StageStyle.UNDECORATED);
        tornarTelaMovimentavel(cena, stage2);
        stage2.setResizable(false);
        stage2.setMaximized(false);
        stage2.setScene(cena);
        stage2.show();
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

    public static Scene getNovaPessoa() {
        return novaPessoa;
    }

    public static Scene getGerenPessoas() {
        return gerenPessoas;
    }

    public static Scene getNovoExpositor() {
        return novoExpositor;
    }

    public static Scene getGerenExpositores() {
        return gerenExpositores;
    }

    public static Scene getNovoIngresso() {
        return novoIngresso;
    }

    public static Scene getGerenIngressos() {
        return gerenIngressos;
    }
    public static Scene getNovaExposicao() {
        return novaExposicao;
    }

    public static Scene getGerenExposicoes() {
        return gerenExposicoes;
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
        // TESTE DE BANCO
        try {
            System.out.println("Conectando ...");
            Banco.conectar();
            System.out.println("Conectado...");
            
            //tentando inserir um prop.
            Pessoa p = new Pessoa();
            p.setCPF("02103210");
            p.setEmail("cuzao@hotmail.com");
            p.setNome("cuzudo");
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