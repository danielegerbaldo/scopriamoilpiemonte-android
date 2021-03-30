package com.example.guitaass.retrofit;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.guitaass.DOM.Utente;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface API {

    @GET("")
    Call<List<Utente>> getUsers();

    @POST("login")
    Call<Utente> login(@Body Map<String, String> body);

    @POST(".")
    Call<Map<String, String>> registrati(@Body Map<String, String> body);

}
