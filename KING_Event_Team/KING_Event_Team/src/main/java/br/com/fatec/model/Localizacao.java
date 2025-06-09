/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

/**
 *
 * @author luann
 */
public class Localizacao 
{
    private int codLocal;
    private String nomeLocal;
    private String CEP;
    private String enderecoLocal;
    private int numeroLocal;
    private String cidade;
    private String tipoLocal;
    
    public Localizacao() {
    }

    public Localizacao(int codLocal, String nomeLocal, String CEP, String enderecoLocal, int numeroLocal, String cidade, String tipoLocal) {
        this.codLocal = codLocal;
        this.nomeLocal = nomeLocal;
        this.CEP = CEP;
        this.enderecoLocal = enderecoLocal;
        this.numeroLocal = numeroLocal;
        this.cidade = cidade;
        this.tipoLocal = tipoLocal;
    }
    
    public int getCodLocal() {
        return codLocal;
    }

    public void setCodLocal(int codLocal) {
        this.codLocal = codLocal;
    }

    public String getNomeLocal() {
        return nomeLocal;
    }

    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getEnderecoLocal() {
        return enderecoLocal;
    }

    public void setEnderecoLocal(String enderecoLocal) {
        this.enderecoLocal = enderecoLocal;
    }

    public int getNumeroLocal() {
        return numeroLocal;
    }

    public void setNumeroLocal(int numeroLocal) {
        this.numeroLocal = numeroLocal;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getTipoLocal() {
        return tipoLocal;
    }

    public void setTipoLocal(String tipoLocal) {
        this.tipoLocal = tipoLocal;
    }
    
    
}
