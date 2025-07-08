/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.DAO.DAO;
import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *
 * @author luann
 */
public abstract class TelaGravacao<T> 
{
    public boolean isModoEdicao;
    public T modelAEditar;
    
    private T model;
    private DAO dao;
    
    
    private void carregarModoGravacao()
    {
        if(isModoEdicao)
        {
            carregarEdicao();
        }
        else
        {
            carregarCadastro();
        }
    }
    
    
    /**
     * Muda a aparência da tela para se encaixar no modo de Edição 
     */
    public abstract void carregarEdicao();
    
    
    /**
     * Muda a aparência da tela para se encaixar no modo de Cadastro 
     */
    public abstract void carregarCadastro();
    
    
    private void desativarEdicao()
    {
        isModoEdicao = false;
        modelAEditar = null;
        carregarModoGravacao();
    }
    
    
    /**
     * Alimenta o model com os dados informados pelo usuário na Tela
     */
    public abstract T carregarModel();
    
    
    /**
     * Limpa todos os campos da Tela
     */
    public abstract void limpar();
    
    
    private void gravar(T model, DAO dao)
    {
        try
        {
            model = carregarModel();
            
            if (isModoEdicao)
            {
                // script do modo edição
                dao.alterar(model);
                App.mensagem("SUCESSO", "Alterado com sucesso!");
            }
            else
            {
                // script do modo cadastro
                dao.inserir(model);
                App.mensagem("SUCESSO", "Cadastrado com sucesso!");
            }
            
            limpar();
            
        }
        catch(SQLException ex)
        {
            if(isModoEdicao)
            {
                App.mensagem("ERRO", "Erro ao editar", Alert.AlertType.ERROR);
            }
            else
            {
                App.mensagem("ERRO", "Erro ao cadastrar", Alert.AlertType.ERROR);
            }
        }
    }
}
