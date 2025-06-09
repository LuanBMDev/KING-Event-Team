/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

/**
 *
 * @author jonat
 */
import br.com.fatec.model.Categoria;
import br.com.fatec.persistencia.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class CategoriaDAO implements DAO <Categoria> 
{   
    private Categoria categoria;
    private PreparedStatement pst;
    private ResultSet rs;
    
    @Override
    public boolean inserir(Categoria model) throws SQLException {
        String sql = "INSERT INTO Categoria (nomeCat) " 
        + "VALUES (?);";
        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setString(1,model.getNomeCat());
        if(pst.executeUpdate()>-1) {
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        }   
    }
    
    @Override
    public boolean remover(Categoria model) throws SQLException{
        String sql = "DELETE FROM Categoria Where codCat = ?;";
        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);
        pst.setInt(1, model.getCodCat());
        
        if(pst.executeUpdate()>=1) {
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public boolean alterar(Categoria model) throws SQLException {
        String sql = "UPDATE Categoria SET nomeCat = ?" +
                "WHERE codCat = ?;";
        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);
        pst.setString(1, model.getNomeCat());
        pst.setInt(2,model.getCodCat());
        
        if(pst.executeUpdate() >= 1){
            Banco.desconectar();
            return true;
        }
        else{
             Banco.desconectar();
             return false;
        }        
    }
    
    @Override
    public Categoria buscarID(Categoria model) throws SQLException {
        categoria = null;
        String sql ="SELECT * FROM Categoria WHERE codCat= ?;";
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setInt(1, model.getCodCat());
        
        rs = pst.executeQuery();
        
        if(rs.next()) { 
            categoria = new Categoria();
            categoria.setCodCat(rs.getInt("codCat"));
            categoria.setNomeCat(rs.getString("nomeCat"));
        }
        Banco.desconectar();
        rs.close();
        return categoria;
    }

    @Override
    public Collection<Categoria> listar(String criterio) throws SQLException {
        Collection<Categoria> listagem = new ArrayList<>();
        categoria = null;
        String sql = "SELECT * FROM Categoria";
        if(criterio.length() !=0){
            sql+= "WHERE" + criterio;
        }
        Banco.conectar();
        pst= Banco.obterConexao().prepareStatement(sql);
        rs = pst.executeQuery();
        while(rs.next()){
            categoria = new Categoria();
            categoria.setCodCat(rs.getInt("codCat"));
            categoria.setNomeCat(rs.getString("nomeCat"));
            listagem.add(categoria);
        }
        Banco.desconectar();
        rs.close();
        return listagem;
    }
        
}
    