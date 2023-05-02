package com.tfg.adoptaunamascota.repository;

import android.content.Context;
import com.tfg.adoptaunamascota.service.ApiService;
import com.tfg.adoptaunamascota.service.StoreManager;

public class AnimalRepository {
    private ApiService apiService;

    public AnimalRepository(Context context, String baseUrl) {
        StoreManager storeManager = new StoreManager(context, baseUrl);
        apiService = storeManager.getApiService();
    }

}
