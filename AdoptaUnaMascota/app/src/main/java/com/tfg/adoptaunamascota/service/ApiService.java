package com.tfg.adoptaunamascota.service;

import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.models.users.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/api/users/login")
    Call<User> getUserByEmailAndPassword(@Query("email") String email, @Query("hashedPassword") String hashedPassword);


    @POST("/api/users/")
    Call<User> createUser(@Body User user);

    @GET("animals/{id}")
    Call<List<Animal>>getAllAnimals();
    @POST("animals")
    Call<Void> createAnimal(@Body Animal animal);
    @PUT("animals/{id}")
    Call<Void> updateAnimal(@Path("id") int id, @Body Animal animal);
    @DELETE("animals/{id}")
    Call<Void> deleteAnimal(@Path("id") int id);

}
