package com.tfg.adoptaunamascota.repository;

import android.content.Context;
import com.tfg.adoptaunamascota.models.users.User;
import com.tfg.adoptaunamascota.service.ApiService;
import com.tfg.adoptaunamascota.service.StoreManager;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private ApiService apiService;

    public UserRepository(Context context) {
        StoreManager storeManager = new StoreManager(context);
        apiService = storeManager.getApiService();
    }

    public void registerUser(String mail, String password, String name, String surname) {
        User user = new User(mail, password, name, surname);
        Call<Void> call = apiService.createUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Usuario creado con éxito
                } else {
                    // Error en la creación del usuario
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Error en la llamada a la API
            }
        });
    }
}
