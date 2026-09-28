package com.example.petcare;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "pets")
public class Pet {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nome;
    private String especie;
    private String raca;
    private Date dataNascimento;
    private String porte;
    private boolean castrado;

    public Pet(String nome, String especie, String raca, Date dataNascimento, String porte, boolean castrado) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.porte = porte;
        this.castrado = castrado;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaca() {
        return raca;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public String getPorte() {
        return porte;
    }

    public boolean isCastrado() {
        return castrado;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public void setCastrado(boolean castrado) {
        this.castrado = castrado;
    }
}