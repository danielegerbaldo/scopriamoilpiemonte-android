package com.example.guitaass.DOM;

import java.util.Date;

public class Evento {
    private Long id;
    private String nome;
    private int numMaxPartecipanti;
    private int partecipanti;
    private boolean streaming;
    private String descrizione;
    private String note;
    private TipoEvento tipoEvento;
    private Date data;
    private long proprietario;
    private long comune;

    public Evento(Long id, String nome, int numMaxPartecipanti, int partecipanti, boolean streaming, String descrizione, String note, TipoEvento tipoEvento, Date data, long proprietario, long comune) {
        this.id = id;
        this.nome = nome;
        this.numMaxPartecipanti = numMaxPartecipanti;
        this.partecipanti = partecipanti;
        this.streaming = streaming;
        this.descrizione = descrizione;
        this.note = note;
        this.tipoEvento = tipoEvento;
        this.data = data;
        this.proprietario = proprietario;
        this.comune = comune;
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

    public int getNumMaxPartecipanti() {
        return numMaxPartecipanti;
    }

    public void setNumMaxPartecipanti(int numMaxPartecipanti) {
        this.numMaxPartecipanti = numMaxPartecipanti;
    }

    public int getPartecipanti() {
        return partecipanti;
    }

    public void setPartecipanti(int partecipanti) {
        this.partecipanti = partecipanti;
    }

    public boolean isStreaming() {
        return streaming;
    }

    public void setStreaming(boolean streaming) {
        this.streaming = streaming;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public TipoEvento getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(TipoEvento tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public long getProprietario() {
        return proprietario;
    }

    public void setProprietario(long proprietario) {
        this.proprietario = proprietario;
    }

    public long getComune() {
        return comune;
    }

    public void setComune(long comune) {
        this.comune = comune;
    }
}
