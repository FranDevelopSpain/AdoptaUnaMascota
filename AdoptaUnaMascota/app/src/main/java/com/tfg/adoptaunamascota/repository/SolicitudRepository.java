package com.tfg.adoptaunamascota.repository;

import android.content.Context;

import com.tfg.adoptaunamascota.models.solicitud.Solicitud;
import com.tfg.adoptaunamascota.service.ApiService;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SolicitudRepository {

    private final ApiService apiService;


    public SolicitudRepository(Context context, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }
    public void registerSolicitud(Solicitud solicitud, Callback<Solicitud> callback){

    }

}
