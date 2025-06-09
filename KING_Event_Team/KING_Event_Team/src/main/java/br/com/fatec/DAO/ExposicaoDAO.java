/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Evento;
import br.com.fatec.model.Ingresso;
import br.com.fatec.model.Exposicao;
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
public class ExposicaoDAO implements DAO<Exposicao>{

    private Exposicao exposicao;
    
    private PreparedStatement pst;
    private ResultSet rs;
    
    @Override
    public boolean inserir(Exposicao model) throws SQLException {
        String sql = "INSERT INTO Exposicao (codEvento,codExpo,descricao)"
                + "VALUES (?,?,?);"; 
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setInt(1,model.getEvento().getCodEvento());
        pst.setInt(2,model.getExpositor().getCodExpo());
        pst.setString(3,model.getDescricao());
        
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
    public boolean remover(Exposicao model) throws SQLException {
       String sql ="DELETE FROM Exposicao WHERE codEvento = ? AND codExpo = ?";
       Banco.conectar();
       
       pst = Banco.obterConexao().prepareStatement(sql);
       
       pst.setInt(1,model.getEvento().getCodEvento());
       pst.setInt(2,model.getExpositor().getCodExpo());
       
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
    public boolean alterar(Exposicao model) throws SQLException {
        String sql = "UPDATE Exposicao SET descricao = ?"
                + "WHERE codEvento = ? AND codExpo = ?;";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setString(1,model.getDescricao());
        pst.setInt(2,model.getEvento().getCodEvento());
        pst.setInt(3,model.getExpositor().getCodExpo());
        
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
    public Exposicao buscarID(Exposicao model) throws SQLException {
        
        exposicao = null;
        
        String sql = "SELECT * FORM Exposicao WHERE codEvento = ? AND codExpo = ?";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setInt(1,model.getEvento().getCodEvento());
        pst.setInt(2,model.getExpositor().getCodExpo());
        
        rs = pst.executeQuery();
        
        if(rs.next())
        {
            Evento evento = new Evento(null, null);
            evento.setCodEvento(rs.getInt("codEvento"));
            EventoDAO dao = new EventoDAO();
            evento = dao.buscarID(evento);

            Expositor expositor = new Expositor();
            expositor.setCodExpo(rs.getInt("codExpo"));
            ExpositorDAO daoEx = new ExpositorDAO();
            expositor = daoEx.buscarID(expositor);

            Exposicao exposicao = new Exposicao(evento, expositor);
            exposicao.setDescricao(rs.getString("descricao"));

            Banco.desconectar();
            rs.close();
        }
        return exposicao;
    }

    @Override
    public Collection<Exposicao> listar(String criterio) throws SQLException {
        Collection<Exposicao> listagem = new ArrayList<>();
        
        exposicao = null;
        
        String sql = "SELECT * FROM Exposicao";
        
        if(criterio.length() != 0)
        {
            sql += " WHERE " + criterio;
        }
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareCall(sql);
        
        rs = pst.executeQuery();
        
        while (rs.next())
        {
            Evento evento = new Evento(null, null);
            evento.setCodEvento(rs.getInt("codEvento"));
            EventoDAO dao = new EventoDAO();
            evento = dao.buscarID(evento);

            Expositor expositor = new Expositor();
            expositor.setCodExpo(rs.getInt("codExpo"));
            ExpositorDAO daoEx = new ExpositorDAO();
            expositor = daoEx.buscarID(expositor);

            Exposicao exposicao = new Exposicao(evento, expositor);
            exposicao.setDescricao(rs.getString("descricao"));

            Banco.desconectar();
            rs.close();
        }
        Banco.desconectar();
        rs.close();
        
        return listagem;   
    }
}
