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
public class Exposicao 
{
    private Evento evento;
    private Expositor expositor;
    
    private String descricao;

    public Exposicao(Evento evento, Expositor expositor) 
    {
        this.evento = evento;
        this.expositor = expositor;
    }

    public Exposicao(Evento evento, Expositor expositor, String descricao) 
    {
        this.evento = evento;
        this.expositor = expositor;
        this.descricao = descricao;
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.evento);
        hash = 83 * hash + Objects.hashCode(this.expositor);
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
        final Exposicao other = (Exposicao) obj;
        if (!Objects.equals(this.evento, other.evento)) {
            return false;
        }
        return Objects.equals(this.expositor, other.expositor);
    }

    @Override
    public String toString() {
        return getEvento() + "-" + getExpositor();
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Expositor getExpositor() {
        return expositor;
    }

    public void setExpositor(Expositor expositor) {
        this.expositor = expositor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    
}
