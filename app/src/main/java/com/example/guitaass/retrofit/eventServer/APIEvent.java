package com.example.guitaass.retrofit.eventServer;

import android.util.EventLog;

import com.example.guitaass.DOM.Evento;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIEvent {
    @POST(".")
    Call<Evento> creaNuovoEvento(@Body Evento evento);

    @GET("eventi-comune/{comune}")
    Call<List<Evento>> ottieniEventiDiComune(@Path("comune") long comune);

    @GET("eventi-non-iscritto/{utenteID}")
    Call<List<Evento>> ottieniEventiUtenteNonIscritto(@Path("utenteID" )long utenteID);

    @POST("prenota")
    Call<Map<String,String>> prenotaEvento(@Body Map<String, Long> body);

    @GET("iscrizione-utente/{utenteID}")
    Call<List<Evento>> prenotazioniUtente(@Path("utenteID") long utenteID);

    @POST("disiscrivi")
    Call<Map<String,String>> disdiciPrenotazione(@Body Map<String, Long> body);

    @DELETE("deleteById/{eventoID}")
    Call<Map<String, String>> eliminaEvento (@Path("eventoID") long eventoID);
}
