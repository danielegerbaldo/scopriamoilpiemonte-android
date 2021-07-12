package com.example.guitaass.retrofit.utente;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private API myApi;
    private Context context;


    private RetrofitClient(Context context) {
        SharedPreferences shpr = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = shpr.getString("IP", "localhost");


        String BASE_URL = "http://" + ip + "/api/v1/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.context = context;
        myApi = retrofit.create(API.class);
    }

    public static synchronized RetrofitClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitClient(context);
        }
        return instance;
    }

    public API getMyApi() {
        return myApi;
    }
}
