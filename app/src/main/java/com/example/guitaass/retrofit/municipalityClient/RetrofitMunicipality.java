package com.example.guitaass.retrofit.municipalityClient;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.guitaass.retrofit.utente.API;
import com.example.guitaass.retrofit.utente.RetrofitClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitMunicipality {
    private static RetrofitMunicipality instance = null;
    private APIMunicipality myApi;
    private Context context;

    public RetrofitMunicipality (Context context){
        SharedPreferences shpr = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = shpr.getString("IP", "localhost");
        String BASE_URL = "http://" + ip + "/api/v1/comune/";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.context = context;
        myApi = retrofit.create(APIMunicipality.class);
    }

    public static synchronized RetrofitMunicipality getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitMunicipality(context);
        }
        return instance;
    }

    public APIMunicipality getMyApi(){return myApi;}
}
