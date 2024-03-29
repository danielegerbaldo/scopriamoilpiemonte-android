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
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIEvent {
    @POST(".")
    Call<Evento> creaNuovoEvento(@Body Evento evento, @Header("Authorization") String token);

    @GET("eventi-comune/{comune}")
    Call<List<Evento>> ottieniEventiDiComune(@Path("comune") long comune);

    @GET("eventi-comune-non-scaduti/{comune}")
    Call<List<Evento>> ottieniEventiDiComuneNonScaduti(@Path("comune") long comune, @Header("Authorization") String token);

    @GET("eventi-non-iscritto/{utenteID}")
    Call<List<Evento>> ottieniEventiUtenteNonIscritto(@Path("utenteID" )long utenteID);

    @GET("eventi-non-iscritto-non-scaduti/{utenteID}")
    Call<List<Evento>> ottieniEventiUtenteNonIscrittoNonScaduti(@Path("utenteID" )long utenteID, @Header("Authorization") String token);

    @POST("iscrivi")
    Call<Evento> prenotaEvento(@Body IscriviEvento body, @Header("Authorization") String token);

    @GET("iscrizione-utente/{utenteID}")
    Call<List<Evento>> prenotazioniUtente(@Path("utenteID") long utenteID);

    @GET("iscrizione-utente-non-scaduti/{utenteID}")
    Call<List<Evento>> prenotazioniUtenteNonScadute(@Path("utenteID") long utenteID, @Header("Authorization") String token);

    @POST("disiscrivi")
    Call<Map<String,String>> disdiciPrenotazione(@Body Map<String, Long> body, @Header("Authorization") String token);

    @DELETE("deleteById/{eventoID}")
    Call<String> eliminaEvento (@Path("eventoID") long eventoID, @Header("Authorization") String token);

    @PUT("aggiorna/{id}")
    Call<Evento> modificaEvento (@Path("id") long id, @Body Evento evento, @Header("Authorization") String token);
}
