package com.example.guitaass.DOM;

public class Comune {

    private Long id;
    private String nomeComune;

    public Comune(Long id, String nomeComune){
        this.id = id;
        this.nomeComune = nomeComune;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeComune() {
        return nomeComune;
    }

    public void setNomeComune(String nomeComune) {
        this.nomeComune = nomeComune;
    }
}
