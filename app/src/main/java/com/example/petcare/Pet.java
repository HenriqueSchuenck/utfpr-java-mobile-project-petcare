package com.example.petcare;

public class Pet {
    private String nome;
    private String especie;
    private String raca;
    private String dataNascimento;

    public Pet(String nome, String especie, String raca, String dataNascimento) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() { return nome; }
    public String getEspecie() { return especie; }
    public String getRaca() { return raca; }
    public String getDataNascimento() { return dataNascimento; }
}