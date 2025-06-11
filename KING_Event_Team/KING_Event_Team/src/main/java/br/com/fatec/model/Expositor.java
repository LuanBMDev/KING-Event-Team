/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

/**
 *
 * @author luann
 */
public class Expositor 
{
    private int codExpo;
    private String nomeFant, CPFCNPJ, emailExpo, telefoneExpo;
    private String logoExpo;

    public Expositor() 
    {
        
    }

    public Expositor(int codExpo, String nomeFant, String CPFCNPJ, String emailExpo, String telefoneExpo, String logoExpo) 
    {
        this.codExpo = codExpo;
        this.nomeFant = nomeFant;
        this.CPFCNPJ = CPFCNPJ;
        this.emailExpo = emailExpo;
        this.telefoneExpo = telefoneExpo;
        this.logoExpo = logoExpo;
    }
    
    public int getCodExpo() 
    {
        return codExpo;
    }

    public void setCodExpo(int codExpo) 
    {
        this.codExpo = codExpo;
    }

    public String getNomeFant() 
    {
        return nomeFant;
    }

    public void setNomeFant(String nomeFant) 
    {
        this.nomeFant = nomeFant;
    }

    public String getCPFCNPJ() 
    {
        return CPFCNPJ;
    }

    public void setCPFCNPJ(String CPFCNPJ) 
    {
        this.CPFCNPJ = CPFCNPJ;
    }

    public String getEmailExpo() 
    {
        return emailExpo;
    }

    public void setEmailExpo(String emailExpo) 
    {
        this.emailExpo = emailExpo;
    }

    public String getTelefoneExpo() 
    {
        return telefoneExpo;
    }

    public void setTelefoneExpo(String telefoneExpo) 
    {
        this.telefoneExpo = telefoneExpo;
    }

    public String getLogoExpo() 
    {
        return logoExpo;
    }

    public void setLogoExpo(String logoExpo) 
    {
        this.logoExpo = logoExpo;
    }
   public String toString(){
       return codExpo + " - "+ nomeFant;
   }    
}
