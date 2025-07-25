/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Categoria;
import br.com.fatec.model.Evento;
import br.com.fatec.model.Localizacao;
import br.com.fatec.persistencia.Banco;
import java.sql.SQLException;
import java.util.Collection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author jonat
 */
public class EventoDAO implements DAO <Evento>{
    private Evento evento;
    private PreparedStatement pst;
    private ResultSet rs;
    
    @Override
    public boolean inserir(Evento model) throws SQLException {
        String sql = "INSERT INTO Evento (nomeEvento,dataInicio,DataFim,statusEvento,codLocal,codCat,precoIngresso)"
        +"VALUES(?,?,?,?,?,?,?);";
        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setString(1, model.getNomeEvento());
        pst.setString(2,model.getDataInicio());
        pst.setString(3, model.getDataFim());
        pst.setString(4,model.getStatusEvento());
        pst.setInt(5,model.getLocalizacao().getCodLocal());
        pst.setInt(6,model.getCategoria().getCodCat());
        pst.setDouble(7,model.getPrecoPadrao());
        
        if(pst.executeUpdate()>=1){
            Banco.desconectar();
            return true;
        }
        else{
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public boolean remover(Evento model) throws SQLException {
        String sql = "DELETE FROM Evento Where codEvento = ?;";
        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);
        pst.setInt(1, model.getCodEvento());
        
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
    public boolean alterar(Evento model) throws SQLException {
        String sql = "UPDATE Evento SET nomeEvento = ?,"
                + " dataInicio = ?, dataFim = ?, statusEvento = ?, codLocal = ?, codCat = ?, precoIngresso = ?"
                +"WHERE codEvento = ?;";
        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);
        pst.setString(1, model.getNomeEvento());
        pst.setString(2,model.getDataInicio());
        pst.setString(3, model.getDataFim());
        pst.setString(4,model.getStatusEvento());
        pst.setInt(5,model.getLocalizacao().getCodLocal());
        pst.setInt(6,model.getCategoria().getCodCat());
        pst.setDouble(7,model.getPrecoPadrao());
        pst.setInt(8, model.getCodEvento());
        
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
    public Evento buscarID(Evento model) throws SQLException {
        evento = null;
        
        String sql = "SELECT * FROM Evento WHERE codEvento = ?;";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setInt(1, model.getCodEvento());
        
        rs = pst.executeQuery();
        
        if (rs.next())
        {   
            Localizacao local = new Localizacao();
            local.setCodLocal(rs.getInt("codLocal"));
            LocalizacaoDAO localDAO = new LocalizacaoDAO();
            local = localDAO.buscarID(local);
            
            Categoria cat = new Categoria();
            cat.setCodCat(rs.getInt("codCat"));
            CategoriaDAO catDAO = new CategoriaDAO();
            cat = catDAO.buscarID(cat);
            
            Evento evento = new Evento(cat, local);
            evento.setCodEvento(rs.getInt("codEvento"));
            evento.setNomeEvento(rs.getString("nomeEvento"));
            evento.setDataInicio(rs.getString("dataInicio"));
            evento.setDataFim(rs.getString("dataFim"));
            evento.setStatusEvento(rs.getString("statusEvento"));
            evento.setPrecoPadrao(rs.getDouble("precoIngresso"));
        }
        Banco.desconectar();
        rs.close();
        
        return evento;
    }

    @Override
    public Collection<Evento> listar(String criterio) throws SQLException {
       Collection<Evento> listagem = new ArrayList<>();
        evento = null;
        
        String sql = "SELECT * FROM Evento";
        
        if(criterio.length() !=0){
            sql+= "WHERE" + criterio;
        }
        Banco.conectar();
        pst= Banco.obterConexao().prepareStatement(sql);
        rs = pst.executeQuery();
        while(rs.next())
        {
            Categoria cat = new Categoria();
            cat.setCodCat(rs.getInt("codCat"));
            CategoriaDAO daoCat = new CategoriaDAO();
            cat = daoCat.buscarID(cat);
            
            Localizacao local = new Localizacao();
            local.setCodLocal(rs.getInt("codLocal"));
            LocalizacaoDAO daoLocal = new LocalizacaoDAO();
            local = daoLocal.buscarID(local);
            
            evento = new Evento(cat, local);
            
            evento.setCodEvento(rs.getInt("codEvento"));
            evento.setNomeEvento(rs.getString("nomeEvento"));
            evento.setDataInicio(rs.getString("dataInicio"));
            evento.setDataFim(rs.getString("dataFim"));
            evento.setStatusEvento(rs.getString("statusEvento"));
            evento.setPrecoPadrao(rs.getDouble("precoIngresso"));
            
            listagem.add(evento);
        }
        Banco.desconectar();
        rs.close();
        
        return listagem;
    }
 
}
