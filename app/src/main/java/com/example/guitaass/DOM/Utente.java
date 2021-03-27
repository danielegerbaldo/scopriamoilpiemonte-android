package com.example.guitaass.DOM;

public class Utente {
    private Long id;

    private String nome;

    private String cognome;

    private String cf;

    private String telefono;

    private long comuneResidenza;

    private String email;

    private String password;

    private String ruolo;

    private long comune;

    public Utente(String nome, String cognome, String cf, String telefono, long comuneResidenza, String email,
                  String password, String ruolo, long comune) {
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        this.telefono = telefono;
        this.comuneResidenza = comuneResidenza;
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public long getComuneResidenza() {
        return comuneResidenza;
    }

    public void setComuneResidenza(long comuneResidenza) {
        this.comuneResidenza = comuneResidenza;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public long getComune() {
        return comune;
    }

    public void setComune(long comune) {
        this.comune = comune;
    }
}
