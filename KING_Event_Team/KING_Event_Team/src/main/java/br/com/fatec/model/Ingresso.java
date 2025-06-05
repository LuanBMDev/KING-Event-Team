/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

import java.util.Objects;

/**
 *
 * @author luann
 */
public class Ingresso 
{
    private Evento evento;
    private Pessoa pessoa;
    
    private double totalPago;
    private int meiaEntrada;

    public Ingresso(Evento evento, Pessoa pessoa) 
    {
        this.evento = evento;
        this.pessoa = pessoa;
    }

    public Ingresso(Evento evento, Pessoa pessoa, double totalPago, int meiaEntrada) 
    {
        this.evento = evento;
        this.pessoa = pessoa;
        this.totalPago = totalPago;
        this.meiaEntrada = meiaEntrada;
    }

    @Override
    public int hashCode() 
    {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.evento);
        return hash;
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ingresso other = (Ingresso) obj;
        if (!Objects.equals(this.evento, other.evento)) {
            return false;
        }
        return Objects.equals(this.pessoa, other.pessoa);
    }

    @Override
    public String toString() {
        return getEvento() + "-" + getPessoa();
    }

    public Evento getEvento() 
    {
        return evento;
    }

    public void setEvento(Evento evento) 
    {
        this.evento = evento;
    }

    public Pessoa getPessoa() 
    {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) 
    {
        this.pessoa = pessoa;
    }

    public double getTotalPago() 
    {
        return totalPago;
    }

    public void setTotalPago(double totalPago) 
    {
        this.totalPago = totalPago;
    }

    public int getMeiaEntrada() 
    {
        return meiaEntrada;
    }

    public void setMeiaEntrada(int meiaEntrada) 
    {
        this.meiaEntrada = meiaEntrada;
    }
}
