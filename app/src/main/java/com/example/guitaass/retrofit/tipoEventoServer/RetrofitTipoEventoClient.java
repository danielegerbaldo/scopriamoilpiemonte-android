package com.example.guitaass.retrofit.tipoEventoServer;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.guitaass.retrofit.eventServer.APIEvent;
import com.example.guitaass.retrofit.eventServer.RetrofitEventClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitTipoEventoClient {
    private static RetrofitTipoEventoClient instance = null;
    private APITipoEvento myAPI;
    private Context context;

    public RetrofitTipoEventoClient(Context context) {
        this.context = context;
        SharedPreferences shpr = PreferenceManager.getDefaultSharedPreferences(context);
        String ip = shpr.getString("IP", "localhost");
        String BASE_URL = "http://" + ip + "/api/v1/tipo-evento/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myAPI = retrofit.create(APITipoEvento.class);
    }

    public static synchronized RetrofitTipoEventoClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitTipoEventoClient(context);
        }
        return instance;
    }

    public APITipoEvento getMyAPI(){ return myAPI; }
}
