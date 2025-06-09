/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Pessoa;
import br.com.fatec.persistencia.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author luann
 */
public class PessoaDAO implements DAO<Pessoa>
{
    private Pessoa pessoa;
    
    private PreparedStatement pst;
    private ResultSet rs;

    @Override
    public boolean inserir(Pessoa model) throws SQLException {
        String sql = "INSERT INTO Pessoa (nome, email, telefone) "
                + "VALUES (?, ?, ?);";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setString(1, model.getNome());
        pst.setString(2, model.getEmail());
        pst.setString(3, model.getTelefone());
        
        if (pst.executeUpdate() >= 1)
        {
            Banco.desconectar();
            return true;
        }
        else
        {
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public boolean remover(Pessoa model) throws SQLException {
        String sql = "DELETE FROM Pessoa WHERE CPF = ?;";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setString(1, model.getCPF());
        
        if (pst.executeUpdate() >= 1)
        {
            Banco.desconectar();
            return true;
        }
        else
        {
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public boolean alterar(Pessoa model) throws SQLException {
        String sql = "UPDATE Pessoa SET nome = ?, email = ?, telefone = ? "
                + "WHERE CPF = ?;";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setString(1, model.getNome());
        pst.setString(2, model.getEmail());
        pst.setString(3, model.getTelefone());
        pst.setString(4, model.getCPF());
        
        if (pst.executeUpdate() >= 1)
        {
            Banco.desconectar();
            return true;
        }
        else
        {
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public Pessoa buscarID(Pessoa model) throws SQLException {
        pessoa = null;
        
        String sql = "SELECT * FROM Pessoa WHERE CPF = ?;";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setString(1, model.getCPF());
        
        rs = pst.executeQuery();
        
        if(rs.next())
        {
            pessoa = new Pessoa();
            
            pessoa.setCPF(rs.getString("CPF"));
            pessoa.setNome(rs.getString("nome"));
            pessoa.setEmail(rs.getString("email"));
            pessoa.setTelefone(rs.getString("telefone"));
        }
        
        Banco.desconectar();
        rs.close();
        
        return pessoa;
    }

    @Override
    public Collection<Pessoa> listar(String criterio) throws SQLException {
        Collection<Pessoa> listagem = new ArrayList<>();
        
        pessoa = null;
        
        String sql = "SELECT * FROM Pessoa";
        
        if(criterio.length() != 0)
        {
            sql += " WHERE " + criterio;
        }
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        rs = pst.executeQuery();
        
        while(rs.next())
        {
            pessoa = new Pessoa();
            
            pessoa.setCPF(rs.getString("CPF"));
            pessoa.setNome(rs.getString("nome"));
            pessoa.setEmail(rs.getString("email"));
            pessoa.setTelefone(rs.getString("telefone"));
            
            listagem.add(pessoa);
        }
        
        Banco.desconectar();
        rs.close();
        
        return listagem;
    }
}
