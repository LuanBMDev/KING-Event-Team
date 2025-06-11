/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Localizacao;
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
public class LocalizacaoDAO implements DAO<Localizacao>
{
    private Localizacao localizacao;
    
    private PreparedStatement pst;
    private ResultSet rs;
    
    @Override
    public boolean inserir(Localizacao model) throws SQLException 
    {
        String sql = "INSERT INTO Localizacao (nomeLocal, CEP, endereco, numeroLocal, cidade, estado, tipoLocal) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setString(1, model.getNomeLocal());
        pst.setString(2, model.getCEP());
        pst.setString(3, model.getEnderecoLocal());
        pst.setString(4, model.getNumeroLocal());
        pst.setString(5, model.getCidade());
        pst.setString(6, model.getEstado());
        pst.setString(7, model.getTipoLocal());
        
        if(pst.executeUpdate() >= 1) 
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
    public boolean remover(Localizacao model) throws SQLException 
    {
        String sql = "DELETE FROM Localizacao WHERE codLocal = ?";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setInt(1, model.getCodLocal());
        
        if(pst.executeUpdate() >= 1)
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
    public boolean alterar(Localizacao model) throws SQLException {
        String sql = "UPDATE Localizacao SET nomeLocal = ?, CEP = ?, endereco = ?, "
                + "numeroLocal = ?, cidade = ?, estado = ?, tipoLocal = ? "
                + "WHERE codLocal = ?;";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setString(1, model.getNomeLocal());
        pst.setString(2, model.getCEP());
        pst.setString(3, model.getEnderecoLocal());
        pst.setString(4, model.getNumeroLocal());
        pst.setString(5, model.getCidade());
        pst.setString(6, model.getEstado());
        pst.setString(7, model.getTipoLocal());
        pst.setInt(8, model.getCodLocal());
        
        if(pst.executeUpdate() >= 1)
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
    public Localizacao buscarID(Localizacao model) throws SQLException {
        localizacao = null;
        
        String sql = "SELECT * FROM Localizacao WHERE codLocal = ?;";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setInt(1, model.getCodLocal());
        
        rs = pst.executeQuery();
        
        if (rs.next())
        {
            localizacao = new Localizacao();
            
            localizacao.setCodLocal(rs.getInt("codLocal"));
            localizacao.setNomeLocal(rs.getString("nomeLocal"));
            localizacao.setCEP(rs.getString("CEP"));
            localizacao.setEnderecoLocal(rs.getString("endereco"));
            localizacao.setNumeroLocal(rs.getString("numeroLocal"));
            localizacao.setCidade(rs.getString("cidade"));
            localizacao.setEstado(rs.getString("estado"));
            localizacao.setTipoLocal(rs.getString("tipoLocal"));
        }
        
        Banco.desconectar();
        rs.close();
        
        return localizacao;
    }

    @Override
    public Collection<Localizacao> listar(String criterio) throws SQLException {
        Collection<Localizacao> listagem = new ArrayList<>();
        
        localizacao = null;
        
        String sql = "SELECT * FROM Localizacao";
        
        if (criterio.length() != 0)
        {
            sql = " WHERE " + criterio + ";";
        }
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        rs = pst.executeQuery();
        
        while (rs.next())
        {
            localizacao = new Localizacao();
            
            localizacao.setCodLocal(rs.getInt("codLocal"));
            localizacao.setNomeLocal(rs.getString("nomeLocal"));
            localizacao.setCEP(rs.getString("CEP"));
            localizacao.setEnderecoLocal(rs.getString("endereco"));
            localizacao.setNumeroLocal(rs.getString("numeroLocal"));
            localizacao.setCidade(rs.getString("cidade"));
            localizacao.setEstado(rs.getString("estado"));
            localizacao.setTipoLocal(rs.getString("tipoLocal"));
            
            listagem.add(localizacao);
        }
        
        Banco.desconectar();
        rs.close();
        
        return listagem;
    }
}
