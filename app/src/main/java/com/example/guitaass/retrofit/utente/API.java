package com.example.guitaass.retrofit.utente;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.guitaass.DOM.Utente;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface API {

    @GET("")
    Call<List<Utente>> getUsers();

    @GET("utente/getUser/{id}")
    Call<Utente> infoUtente(@Path("id") long id, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest body);

    @POST("signUp")
    Call<LoginResponse> signUp(@Body SignUpRequest request);

    @GET("validateToken")
    Call<UserDto> validateToken(@Query("token") String token, @Header("Authorization") String t);

    @GET("utente/getDipendentiDiComune/{idComune}")
    Call<List<Utente>> caricaLavoratoriComune(@Path("idComune") long idComune, @Header("Authorization") String t);

    @GET("utente/getNonDipendenti")
    Call<List<Utente>> caricaNonDipendenti(@Header("Authorization") String t);



}
