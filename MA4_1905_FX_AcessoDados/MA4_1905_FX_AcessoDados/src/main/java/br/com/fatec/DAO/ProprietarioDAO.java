/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Proprietario;
import br.com.fatec.persistencia.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Aluno
 */
public class ProprietarioDAO implements DAO <Proprietario> {
//variaveis auxiliares
    private Proprietario proprietario;
    //auxiliares para acesso aos dados
    
    //para conter os comandos DML
    private PreparedStatement pst; //pacote java.sql
    //para conter os dados vindos do BD
    private ResultSet rs; //pacote java.sql
    
    @Override
    public boolean inserir(Proprietario model) throws SQLException {
        
        String sql = "INSERT INTO proprietario (codProprietario, nome) "
        + "VALUES (?, ?);";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getCodProprietario());
        pst.setString(2, model.getNome());
        
        //executa o comando
        if(pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public boolean remover(Proprietario model) throws SQLException {
        String sql = "DELETE FROM proprietario WHERE codProprietario = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getCodProprietario());
        
        //executa o comando
        if(pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public boolean alterar(Proprietario model) throws SQLException {
        String sql = "UPDATE proprietario SET nome = ? "
        + "WHERE codProprietario = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNome());
        pst.setInt(2, model.getCodProprietario());
        
        //executa o comando
        if(pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public Proprietario buscarID(Proprietario model) throws SQLException {
        
        proprietario = null;
        
        //Comando SELECT
        String sql = "SELECT * FROM Proprietario WHERE codProprietario = ?;";
        
        //conecta ao banco
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //troca a ?
        pst.setInt(1, model.getCodProprietario());
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        if(rs.next()) { //achou 1 registro
            //cria o objeto proprietario
            proprietario = new Proprietario();
            //move os dados do resultSet para o objeto proprietario
            proprietario.setCodProprietario(rs.getInt("codProprietario"));
            proprietario.setNome(rs.getString("nome"));
        }
        
        Banco.desconectar();
        //fechar o resultSet
        rs.close();
        
        return proprietario;        
    }

    @Override
    public Collection<Proprietario> listar(String criterio) throws SQLException {
                //criar uma coleção
        Collection<Proprietario> listagem = new ArrayList<>();
        
        proprietario = null;
        //Comando SELECT
        String sql = "SELECT * FROM Proprietario ";
        //colocar filtro ou nao
        if(criterio.length() != 0) {
            sql += "WHERE " + criterio;
        }
        
        //conecta ao banco
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        while(rs.next()) { //achou 1 registro
            //cria o objeto veiculo
            proprietario = new Proprietario();
            //move os dados do resultSet para o objeto veiculo
            proprietario.setCodProprietario(rs.getInt("codProprietario"));
            proprietario.setNome(rs.getString("nome"));
            
            //adicionar na coleção
            listagem.add(proprietario);
        }
        
        Banco.desconectar();
        rs.close();
        
        
        return listagem;
    }
    
}
