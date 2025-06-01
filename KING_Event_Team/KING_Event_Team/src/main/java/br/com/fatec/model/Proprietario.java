/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

/**
 *
 * @author Aluno
 */
public class Proprietario {
    private int codProprietario;
    private String nome;

    public Proprietario() {
    }

    public Proprietario(int codProprietario, String nome) {
        this.codProprietario = codProprietario;
        this.nome = nome;
    }

    
    public int getCodProprietario() {
        return codProprietario;
    }

    public void setCodProprietario(int codProprietario) {
        this.codProprietario = codProprietario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
