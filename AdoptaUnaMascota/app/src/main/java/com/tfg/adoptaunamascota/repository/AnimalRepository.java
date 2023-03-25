package com.tfg.adoptaunamascota.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.tfg.adoptaunamascota.service.ApiService;
import com.tfg.adoptaunamascota.service.StoreManager;

public class AnimalRepository {
    private ApiService apiService;

    public AnimalRepository(Context context){
        StoreManager storeManager = new StoreManager(context);
        apiService = storeManager.getApiService();
    }
    // Agrega métodos para realizar operaciones CRUD en animales utilizando
}
