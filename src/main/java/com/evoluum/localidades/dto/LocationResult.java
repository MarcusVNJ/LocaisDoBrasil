package com.evoluum.localidades.dto;

import com.evoluum.localidades.model.City;
import com.evoluum.localidades.model.State;

public class LocationResult {

    private Integer idEstado;
    private String siglaEstado;
    private String regiaoNome;
    private String nomeCidade;
    private String nomeMesorregiao;
    private String nomeFormatado;

    public LocationResult(Integer idEstado, String siglaEstado, String regiaoNome, String nomeCidade, String nomeMesorregiao, String nomeFormatado) {
        this.idEstado = idEstado;
        this.siglaEstado = siglaEstado;
        this.regiaoNome = regiaoNome;
        this.nomeCidade = nomeCidade;
        this.nomeMesorregiao = nomeMesorregiao;
        this.nomeFormatado = nomeFormatado;
    }

    public static LocationResult createdLocationDto(City city, State state) {
        String formatted = String.format("%s/%s", city.getNome(), state.getSigla());
        return new LocationResult(state.getId(), state.getSigla(), state.getNome(), city.getNome(), city.getNomeMesorregiao(), formatted);
    }

    public String toStringCsv() {
        return new StringBuilder().append(this.idEstado).append(", ").append(this.siglaEstado).append(",")
            .append(this.regiaoNome).append(",").append(this.nomeCidade).append(",")
            .append(this.nomeMesorregiao).append(",").append(this.nomeFormatado).append("\n").toString();
    }

    public static StringBuilder getHeader() {
        return new StringBuilder().append("Id estado, ").append("Sigla do Estado, ")
            .append("Nome da Região, ").append("nome da Cidade, ")
            .append("Nome da Messorregião, ").append("Cidade/Estado\n");
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public String getRegiaoNome() {
        return regiaoNome;
    }

    public void setRegiaoNome(String regiaoNome) {
        this.regiaoNome = regiaoNome;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getNomeMesorregiao() {
        return nomeMesorregiao;
    }

    public void setNomeMesorregiao(String nomeMesorregiao) {
        this.nomeMesorregiao = nomeMesorregiao;
    }

    public String getNomeFormatado() {
        return nomeFormatado;
    }

    public void setNomeFormatado(String nomeFormatado) {
        this.nomeFormatado = nomeFormatado;
    }

}
