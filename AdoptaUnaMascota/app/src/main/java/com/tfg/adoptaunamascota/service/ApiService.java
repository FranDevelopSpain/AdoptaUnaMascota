package com.tfg.adoptaunamascota.service;

import com.tfg.adoptaunamascota.models.users.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("api/users/auth")
    Call<User> loginUser(@QueryMap Map<String, String> params);

    @GET("api/users/admin/auth")
    Call<User> loginAdmin(@QueryMap Map<String, String> params);

    @POST("/api/users/")
    Call<User> createUser(@Body User user);
    @POST("/api/users/updatePassword")
    Call<User> updateUserPassword(@Query("email") String email, @Query("newPassword") String newPassword);
    @GET("/api/users")
    Call<List<User>> getUsers();
}

  /*  @GET("animals/{id}")
    Call<List<Animal>>getAllAnimals();
    @POST("animals")
    Call<Void> createAnimal(@Body Animal animal);
    @PUT("animals/{id}")
    Call<Void> updateAnimal(@Path("id") int id, @Body Animal animal);
    @DELETE("animals/{id}")
    Call<Void> deleteAnimal(@Path("id") int id);
*/
