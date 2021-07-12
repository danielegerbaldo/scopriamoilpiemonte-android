package com.example.guitaass.retrofit.municipalityClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIMunicipality {

    @GET("info-comune/")
    Call<List<Comune>> getListComuni();

}
