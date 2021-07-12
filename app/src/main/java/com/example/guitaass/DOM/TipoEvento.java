package com.example.guitaass.DOM;

public class TipoEvento {
    private Long id;
    private String nome;
    private String descrizione;

    public TipoEvento(Long id, String nome, String descrizione) {
        this.id = null;
        this.nome = nome;
        this.descrizione = descrizione;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
