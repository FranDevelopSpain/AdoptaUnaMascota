package com.tfg.adoptaunamascota.service;

import android.content.Context;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoreManager {

    private ApiService apiService;

    public StoreManager(Context context, String baseUrl) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService userService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}