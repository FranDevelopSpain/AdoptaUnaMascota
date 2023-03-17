package com.tfg.adoptaunamascota.services;

import com.tfg.adoptaunamascota.models.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST("/login")
    Call<Login> login(@Body Login login);
}
