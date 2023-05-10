package com.tfg.adoptaunamascota.service;

import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.models.users.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    //Usuarios

    @GET("api/users/auth")
    Call<User> loginUser(@QueryMap Map<String, String> params);

    @GET("api/users/admin/auth")
    Call<User> loginAdmin(@QueryMap Map<String, String> params);

    @POST("/api/users/")
    Call<User> createUser(@Body User user);

    @POST("/api/users/updatePassword")
    Call<User> updateUserPassword(@Query("email") String email, @Query("newPassword") String newPassword);

    //Para CRUD
    @GET("/api/users/")
    Call<List<User>> getUsers();

    @PUT("api/users/{id}")
    Call<User> updateUser(@Path("id") long id, @Body User user);

    @DELETE("api/users/{id}")
    Call<Void> deleteUser(@Path("id") long id);

    //Animales
    @GET("api/animals/")
    Call<List<Animal>> getAnimals();

    @Multipart
    @POST("api/animals/")
    Call<Animal> createAnimal(
            @Part("animal") RequestBody animal,
            @Part MultipartBody.Part image
    );


    @PUT("api/animals/{id}")
    Call<Animal> updateAnimal(@Path("id") long id, @Body Animal animal);

    @DELETE("api/animals/{id}")
    Call<Void> deleteAnimal(@Path("id") long id);
}
