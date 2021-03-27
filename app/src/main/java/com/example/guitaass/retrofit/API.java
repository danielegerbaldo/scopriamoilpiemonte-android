package com.example.guitaass.retrofit;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.guitaass.DOM.Utente;
import com.example.guitaass.classiComode.RichiestaLogin;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.OPTIONS;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;
import retrofit2.http.Tag;

public interface API {

    @GET("")
    Call<List<Utente>> getUsers();

    @POST("login")
    Call<Utente> login(@Body Map<String, String> body);

    @POST(".")
    Call<Map<String, String>> registrati(@Body Map<String, String> body);

}
