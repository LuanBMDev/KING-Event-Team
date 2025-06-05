/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

/**
 *
 * @author luann
 */
public class Pessoa 
{
    private String CPF;
    private String nome;
    private String email;
    private String telefone;

    public Pessoa() 
    {
        
    }

    public Pessoa(String CPF, String nome, String email, String telefone) 
    {
        this.CPF = CPF;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }
    
    public String getCPF() 
    {
        return CPF;
    }

    public void setCPF(String CPF) 
    {
        this.CPF = CPF;
    }

    public String getNome() 
    {
        return nome;
    }

    public void setNome(String nome) 
    {
        this.nome = nome;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getTelefone() 
    {
        return telefone;
    }

    public void setTelefone(String telefone) 
    {
        this.telefone = telefone;
    }
}
