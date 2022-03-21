package com.evoluum.localidades.model;

import org.springframework.lang.NonNull;

import java.io.Serializable;

public class City implements Serializable {

    private Integer id;
    private String nome;
    private String nomeMesorregiao;

    public City(Integer id, String nome, String nomeMesorregiao) {
        this.id = id;
        this.nome = nome;
        this.nomeMesorregiao = nomeMesorregiao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeMesorregiao() {
        return nomeMesorregiao;
    }

    public void setNomeMesorregiao(String nomeMesorregiao) {
        this.nomeMesorregiao = nomeMesorregiao;
    }

}
