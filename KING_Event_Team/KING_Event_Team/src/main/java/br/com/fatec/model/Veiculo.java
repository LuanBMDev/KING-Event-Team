/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fatec.model;

import java.util.Objects;

/**
 *
 * @author Aluno
 */
public class Veiculo {
    private String placa;
    private String modelo;
    private double valor;
    //composição
    private Proprietario proprietario;

    @Override
    public String toString() {
        return getPlaca();
    }
    
    public Veiculo(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public Veiculo(String placa, String modelo, double valor, 
                    Proprietario proprietario) {
        this.placa = placa;
        this.modelo = modelo;
        this.valor = valor;
        this.proprietario = proprietario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.placa);
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
        final Veiculo other = (Veiculo) obj;
        if (!Objects.equals(this.placa, other.placa)) {
            return false;
        }
        return true;
    }

    
    
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }
    
    
}
