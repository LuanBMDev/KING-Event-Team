/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

/**
 *
 * @author luann
 */
public class Categoria 
{
    private int codCat;
    private String nomeCat;

    public Categoria() {
    }

    public Categoria(int codCat, String nomeCat) {
        this.codCat = codCat;
        this.nomeCat = nomeCat;
    }

    public int getCodCat() {
        return codCat;
    }

    public void setCodCat(int codCat) {
        this.codCat = codCat;
    }

    public String getNomeCat() {
        return nomeCat;
    }

    public void setNomeCat(String nomeCat) {
        this.nomeCat = nomeCat;
    }
    
    public String toString(){
        return codCat + "-" + nomeCat;
    }
}
