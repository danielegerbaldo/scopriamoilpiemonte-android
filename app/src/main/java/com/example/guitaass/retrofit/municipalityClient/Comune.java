package com.example.guitaass.retrofit.municipalityClient;

public class Comune {
    public Comune() {
    }

    public Comune(Long istat, String nome, String CAP, String regione, String provincia) {
        this.istat = istat;
        this.nome = nome;
        this.CAP = CAP;
        this.regione = regione;
        this.provincia = provincia;
    }

    private Long istat;

    private String nome;

    private String CAP;

    private String regione;

    private String provincia;

    private Double lat;

    private Double lng;

    public Long getIstat() {
        return istat;
    }

    public void setIstat(Long id) {
        this.istat = istat;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCAP() {
        return CAP;
    }

    public void setCAP(String CAP) {
        this.CAP = CAP;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
