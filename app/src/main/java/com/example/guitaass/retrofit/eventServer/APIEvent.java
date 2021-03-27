package com.example.guitaass.retrofit.eventServer;

import android.util.EventLog;

import com.example.guitaass.DOM.Evento;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIEvent {
    @POST(".")
    Call<Evento> creaNuovoEvento(@Body Evento evento);
}
