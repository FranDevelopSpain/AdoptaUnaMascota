package com.tfg.adoptaunamascota.service;

import android.content.Context;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StoreManager {

    private ApiService apiService;

    public StoreManager(Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.1:300/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }
    public ApiService getApiService() {
        return apiService;
    }
}