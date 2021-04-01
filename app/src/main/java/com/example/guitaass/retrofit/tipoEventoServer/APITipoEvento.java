package com.example.guitaass.retrofit.tipoEventoServer;

import com.example.guitaass.DOM.TipoEvento;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APITipoEvento {
    @GET(".")
    Call<List<TipoEvento>> ottieniListaTipiEvento();
}
