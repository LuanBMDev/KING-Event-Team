/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Proprietario;
import br.com.fatec.model.Veiculo;
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
public class VeiculoDAO implements DAO<Veiculo> {
    
    //variaveis auxiliares
    private Veiculo veiculo;
    //auxiliares para acesso aos dados
    
    //para conter os comandos DML
    private PreparedStatement pst; //pacote java.sql
    //para conter os dados vindos do BD
    private ResultSet rs; //pacote java.sql
    
    @Override
    public boolean inserir(Veiculo model) throws SQLException {
        String sql = "INSERT INTO veiculo (placa, codProprietario, modelo, valor) "
                + "VALUES (?, ?, ?, ?);";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getPlaca());
        pst.setInt(2, model.getProprietario().getCodProprietario());
        pst.setString(3, model.getModelo());
        pst.setDouble(4, model.getValor());
                
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
    public boolean remover(Veiculo model) throws SQLException {
        String sql = "DELETE FROM veiculo WHERE placa = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getPlaca());
        
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
    public boolean alterar(Veiculo model) throws SQLException {
        String sql = "UPDATE veiculo SET codProprietario = ?, modelo = ?, "
                + "valor = ? WHERE placa = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getProprietario().getCodProprietario());
        pst.setString(2, model.getModelo());
        pst.setDouble(3, model.getValor());
        pst.setString(4, model.getPlaca());
                
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
    public Veiculo buscarID(Veiculo model) throws SQLException {
        veiculo = null;
        //Comando SELECT
        String sql = "SELECT * FROM veiculo WHERE placa = ?;";
        
        //conecta ao banco
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //troca a ?
        pst.setString(1, model.getPlaca());
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        if(rs.next()) { //achou 1 registro
            //busca os dados do proprietario
            Proprietario p = new Proprietario();
            p.setCodProprietario(rs.getInt("codProprietario"));
            //faz a busca via DAO 
            ProprietarioDAO dao = new ProprietarioDAO();
            p = dao.buscarID(p);
            
            //cria o objeto veiculo
            veiculo = new Veiculo(p);
            //move os dados do resultSet para o objeto veiculo
            veiculo.setPlaca(rs.getString("placa"));
            veiculo.setModelo(rs.getString("modelo"));
            veiculo.setValor(rs.getDouble("valor"));
        }
        
        Banco.desconectar();
        rs.close();
        
        return veiculo;        
    }

    @Override
    public Collection<Veiculo> listar(String criterio) 
                throws SQLException {
        //criar uma coleção
        Collection<Veiculo> listagem = new ArrayList<>();
        
        veiculo = null;
        //Comando SELECT
        String sql = "SELECT * FROM Veiculo ";
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
            //busca os dados do proprietario
            Proprietario p = new Proprietario();
            p.setCodProprietario(rs.getInt("codProprietario"));
            //faz a busca via DAO 
            ProprietarioDAO dao = new ProprietarioDAO();
            p = dao.buscarID(p);
            
            //cria o objeto veiculo
            veiculo = new Veiculo(p);
            //move os dados do resultSet para o objeto veiculo
            veiculo.setPlaca(rs.getString("placa"));
            veiculo.setModelo(rs.getString("modelo"));
            veiculo.setValor(rs.getDouble("valor"));
            
            //adicionar na coleção
            listagem.add(veiculo);
        }
        
        Banco.desconectar();
        rs.close();
        
        return listagem;
    }

}
