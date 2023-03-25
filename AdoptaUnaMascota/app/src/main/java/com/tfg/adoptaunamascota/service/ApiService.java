package com.tfg.adoptaunamascota.service;

import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.models.users.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/v1/users")
    Call<Void> createUser(@Body User user);
    @PUT("api/v1/users/{id}")
    Call<Void> updateUser(@Path("id") int id, @Body User user);
    @GET("animals/{id}")
    Call<List<Animal>>getAllAnimals();
}
