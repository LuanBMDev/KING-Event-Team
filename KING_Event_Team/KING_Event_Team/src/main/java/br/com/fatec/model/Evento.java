/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

/**
 *
 * @author luann
 */
public class Evento 
{
    private int codEvento;
    private String nomeEvento;
    private String dataInicio;
    private String dataFim;
    private String statusEvento;
    private double precoPadrao;
    
    private Categoria categoria;
    private Localizacao localizacao;

    public Evento(Categoria categoria, Localizacao localizacao) 
    {
        this.categoria = categoria;
        this.localizacao = localizacao;
    }

    public Evento(int codEvento, String nomeEvento, String descEvento, String dataInicio, String dataFim, String statusEvento, double precoPadrão, Categoria categoria, Localizacao localizacao) 
    {
        this.codEvento = codEvento;
        this.nomeEvento = nomeEvento;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.statusEvento = statusEvento;
        this.precoPadrao = precoPadrao;
        this.categoria = categoria;
        this.localizacao = localizacao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.codEvento;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evento other = (Evento) obj;
        return this.codEvento == other.codEvento;
    }

    @Override
    public String toString() {
        return String.valueOf(getCodEvento()) + "\n" + 
                getNomeEvento() + "\n\n" + 
                getLocalizacao().getNomeLocal()+ ": " + getLocalizacao().getCodLocal() + "\n" + 
                getCategoria().getNomeCat();
    }

    public int getCodEvento() 
    {
        return codEvento;
    }

    public void setCodEvento(int codEvento) 
    {
        this.codEvento = codEvento;
    }

    public String getNomeEvento() 
    {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) 
    {
        this.nomeEvento = nomeEvento;
    }

    public String getDataInicio() 
    {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) 
    {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() 
    {
        return dataFim;
    }

    public void setDataFim(String dataFim) 
    {
        this.dataFim = dataFim;
    }

    public String getStatusEvento() 
    {
        return statusEvento;
    }

    public void setStatusEvento(String statusEvento) 
    {
        this.statusEvento = statusEvento;
    }

    public double getPrecoPadrao() 
    {
        return precoPadrao;
    }

    public void setPrecoPadrao(double precoPadrao) 
    {
        this.precoPadrao = precoPadrao;
    }

    public Categoria getCategoria() 
    {
        return categoria;
    }

    public void setCategoria(Categoria categoria) 
    {
        this.categoria = categoria;
    }

    public Localizacao getLocalizacao() 
    {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) 
    {
        this.localizacao = localizacao;
    }
}
