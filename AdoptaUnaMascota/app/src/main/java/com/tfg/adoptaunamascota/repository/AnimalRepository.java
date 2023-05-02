package com.tfg.adoptaunamascota.repository;

import android.content.Context;

import com.tfg.adoptaunamascota.service.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimalRepository {
    private ApiService apiService;

    public AnimalRepository(Context context, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

}
