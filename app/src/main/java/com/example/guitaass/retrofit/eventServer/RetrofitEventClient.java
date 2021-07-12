package com.example.guitaass.retrofit.eventServer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitEventClient {
    private static RetrofitEventClient instance = null;
    private APIEvent myAPI;
    private Context context;

    //utiliziamo il pattern singletone
    private RetrofitEventClient(Context context){
        SharedPreferences shpr = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = shpr.getString("IP", "localhost");


        String BASE_URL = "http://" + ip + "/api/v1/evento/";
        //Serve per forzare che la data dell'evento abbia formato corretto
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.context = context;
        myAPI = retrofit.create(APIEvent.class);
    }

    public static synchronized RetrofitEventClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitEventClient(context);
        }
        return instance;
    }

    public APIEvent getMyAPI(){ return myAPI; }
}
