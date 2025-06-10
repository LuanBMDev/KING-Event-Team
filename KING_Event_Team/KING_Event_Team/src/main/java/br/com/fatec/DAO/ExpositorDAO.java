/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Expositor;
import br.com.fatec.persistencia.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author jonat
 */
public class ExpositorDAO implements DAO<Expositor>
{
    private Expositor expositor;
    
    private PreparedStatement pst;
    private ResultSet rs;

    @Override
    public boolean inserir(Expositor model) throws SQLException {
        String sql = "INSERT INTO Expositor (nomeFant, CPFCNPJ,logoExpo,emailExpo,telefoneExpo)"
                + "VALUES (?, ?, ?, ?, ?);";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setString(1,model.getNomeFant());
        pst.setString(2,model.getCPFCNPJ());
        pst.setString(3,model.getLogoExpo());
        pst.setString(4,model.getEmailExpo());
        pst.setString(5,model.getTelefoneExpo());
        
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
    public boolean remover(Expositor model) throws SQLException {
        String sql = "DELETE FROM Expositor WHERE codExpo = ?;";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setInt(1, model.getCodExpo());
        
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
    public boolean alterar(Expositor model) throws SQLException {
        String sql = "ÜPDATE Expositor SET nomeFant = ?, CPFCNPJ = ?, logoExpo = ?, emailExpo = ?, telefoneExpo = ?"
                + "WHERE codExpo = ?;";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setInt(6,model.getCodExpo());
        pst.setString(1,model.getNomeFant());
        pst.setString(2,model.getCPFCNPJ());
        pst.setString(3,model.getLogoExpo());
        pst.setString(4,model.getEmailExpo());
        pst.setString(5,model.getTelefoneExpo());
        
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
    public Expositor buscarID(Expositor model) throws SQLException {
        expositor = null;
        
        String sql = "SELECT * FROM Expositor WHERE codExpo = ?;";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        rs = pst.executeQuery();
        
        if(rs.next())
        {
            expositor = new Expositor();
            
            expositor.setNomeFant(rs.getString("nomeFant"));
            expositor.setCPFCNPJ(rs.getString("CPFCNPJ"));
            expositor.setLogoExpo(rs.getString("logoExpo"));
            expositor.setEmailExpo(rs.getString("emailExpo"));
            expositor.setTelefoneExpo(rs.getString("telefoneExpo"));
        }
        Banco.desconectar();
        rs.close();
        
        return expositor;
    }

    @Override
    public Collection<Expositor> listar(String criterio) throws SQLException {
        Collection<Expositor> listagem = new ArrayList<>();
        
        expositor = null;
        
        String sql = "SELECT * FROM Expositor";
        
        if(criterio.length() != 0){
            
            sql +="WHERE"+ criterio;
        }
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        rs = pst.executeQuery();
        
        while(rs.next()){
            expositor = new Expositor();
            
            expositor.setNomeFant(rs.getString("nomeFant"));
            expositor.setCPFCNPJ(rs.getString("CPFCNPJ"));
            expositor.setLogoExpo(rs.getString("logoExpo"));
            expositor.setEmailExpo(rs.getString("emailExpo"));
            expositor.setTelefoneExpo(rs.getString("telefoneExpo"));
            
            listagem.add(expositor);
        }
        Banco.desconectar();
        rs.close();
        
        return listagem;
    }
    
}
