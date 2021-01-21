package com.example.guitaass.DOM;

public class Utente {
    private long id;
    private String nome;
    private String cognome;
    private String status;

    public Utente(long id, String nome, String cognome, String status) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
