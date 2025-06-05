/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.model.Evento;
import br.com.fatec.model.Ingresso;
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
public class IngressoDAO implements DAO<Ingresso>
{
    private Ingresso ingresso;
    
    private PreparedStatement pst;
    private ResultSet rs;
    
    @Override
    public boolean inserir(Ingresso model) throws SQLException {
        String sql = "INSERT INTO ingressos (codEvento, codVisitante, totalPago, meiaEntrada)"
                + "VALUES (?, ?, ?, ?);";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setInt(1, model.getEvento().getCodEvento());
        pst.setString(2, model.getPessoa().getCPF());
        pst.setDouble(3, model.getTotalPago());
        pst.setInt(4, model.getMeiaEntrada());
        
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
    public boolean remover(Ingresso model) throws SQLException {
        String sql = "DELETE FROM ingressos WHERE codEvento = ? AND codVisitante = ?;";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setInt(1, model.getEvento().getCodEvento());
        pst.setString(2, model.getPessoa().getCPF());
        
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
    public boolean alterar(Ingresso model) throws SQLException {
        String sql = "UPDATE ingressos SET totalPago = ?, meiaEntrada = ? "
                + "WHERE codEvento = ? AND codVisitante = ?;";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setDouble(1, model.getTotalPago());
        pst.setInt(2, model.getMeiaEntrada());
        pst.setInt(3, model.getEvento().getCodEvento());
        pst.setString(4, model.getPessoa().getCPF());
        
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
    public Ingresso buscarID(Ingresso model) throws SQLException {
        ingresso = null;
        
        String sql = "SELECT * FROM ingressos WHERE codEvento = ? AND codVisitante = ?;";
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        pst.setInt(1, model.getEvento().getCodEvento());
        pst.setString(2, model.getPessoa().getCPF());
        
        rs = pst.executeQuery();
        
        if(rs.next())
        {
            Evento evento = new Evento(null, null);
            evento.setCodEvento(rs.getInt("codEvento"));
            EventoDAO dao = new EventoDAO();
            evento = dao.buscarID(evento);
            
            Pessoa pessoa = new Pessoa();
            pessoa.setCPF(rs.getString("CPF"));
            PessoaDAO dao = new PessoaDAO();
            pessoa = dao.buscarID(pessoa);
            
            Ingresso ingresso = new Ingresso(evento, pessoa);
            ingresso.setTotalPago(rs.getDouble("totalPago"));
            ingresso.setMeiaEntrada(rs.getInt("meiaEntrada"));
        }
        
        Banco.desconectar();
        rs.close();
        
        return ingresso;
    }

    @Override
    public Collection<Ingresso> listar(String criterio) throws SQLException {
        Collection<Ingresso> listagem = new ArrayList<>();
        
        ingresso = null;
        
        String sql = "SELECT * FROM ingressos";
        
        if(criterio.length() != 0)
        {
            sql += " WHERE " + criterio;
        }
        
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        rs = pst.executeQuery();
        
        while (rs.next())
        {
            Evento evento = new Evento(null, null);
            evento.setCodEvento(rs.getInt("codEvento"));
            EventoDAO dao = new EventoDAO();
            evento = dao.buscarID(evento);
            
            Pessoa pessoa = new Pessoa();
            pessoa.setCPF(rs.getString("CPF"));
            PessoaDAO dao = new PessoaDAO();
            pessoa = dao.buscarID(pessoa);
            
            Ingresso ingresso = new Ingresso(evento, pessoa);
            ingresso.setTotalPago(rs.getDouble("totalPago"));
            ingresso.setMeiaEntrada(rs.getInt("meiaEntrada"));
            
            listagem.add(ingresso);
        }
        
        Banco.desconectar();
        rs.close();
        
        return listagem;
    }
    
}
