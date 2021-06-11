package com.example.listapersonagem.model;

import java.io.Serializable;

public class Personagem implements Serializable {
    //setando as variaveis
    private String nome;
    private String altura;
    private String nascimento;
    private String telefone;
    private String endereco;
    private String rg;
    private String cep;
    private String genero;
    private int id = 0;

    public Personagem(String nome, String altura, String nascimento, String telefone, String endereco, String rg, String cep, String genero) {
        //Adicioando o nome que foi adicionado, com o nome que esta no local
        this.nome = nome;
        this.altura = altura;
        this.nascimento = nascimento;
        this.telefone = telefone;
        this.endereco = endereco;
        this.rg = rg;
        this.cep = cep;
        this.genero = genero;
    }

    public Personagem() {

    }

    //Metodos Getter e Setter
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getTelefone() { return telefone; }

    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEndereco() { return endereco; }

    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getRg() { return rg; }

    public void setRg(String rg) { this.rg = rg; }

    public String getCep() { return cep; }

    public void setCep(String cep) { this.cep = cep; }

    public String getGenero() { return genero; }

    public void setGenero(String genero) { this.genero = genero; }

    //Sobreescrevendo o metodo toString para retornar o nome
    @Override
    public String toString() {
        return nome;
    }

    //setar a Localizacao na lista pelo id
    public void setId(int id) {
        this.id = id;
    }

    //Pegar a Localizacao na lista pelo id
    public int getId() {
        return id;
    }

    //Verificador de o id e valido
    public boolean IdValido() {
        return id > 0;
    }
}
