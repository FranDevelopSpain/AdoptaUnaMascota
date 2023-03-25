package com.tfg.adoptaunamascota.service;

import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.models.users.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    //Creamos la llamada a la API para obtener el CRUD de usuarios

    @GET("api/v1/users/")
    Call<List<User>>getAllUsers();
    @POST("api/v1/users")
    Call<Void> createUser(@Body User user);
    @PUT("api/v1/users/{id}")
    Call<Void> updateUser(@Path("id") int id, @Body User user);
    @DELETE("api/v1/users/{id}")
    Call<Void> deleteUser(@Path("id") int id);

    //Creamos la llamada a la API para obtener el CRUD de animales

    @GET("animals/{id}")
    Call<List<Animal>>getAllAnimals();
    @POST("animals")
    Call<Void> createAnimal(@Body Animal animal);
    @PUT("animals/{id}")
    Call<Void> updateAnimal(@Path("id") int id, @Body Animal animal);
    @DELETE("animals/{id}")
    Call<Void> deleteAnimal(@Path("id") int id);

}
