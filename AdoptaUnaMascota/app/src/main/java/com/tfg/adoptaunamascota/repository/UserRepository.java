package com.tfg.adoptaunamascota.repository;

import android.content.Context;
import android.util.Log;

import com.tfg.adoptaunamascota.models.users.User;
import com.tfg.adoptaunamascota.service.ApiService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class UserRepository {
    private final ApiService apiService;

    public UserRepository(Context context, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public void getUserByEmailAndPassword(String email, String rawPassword, Callback<User> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", rawPassword);
        Call<User> call = apiService.loginUser(params);
        Log.d("UserRepository", "getUserByEmailAndPassword: URL=" + call.request().url());
        call.enqueue(callback);
    }


    public void getIsAdmin(String email, String rawPassword, Callback<User> callback) {
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", rawPassword);
        Call<User> call = apiService.loginAdmin(params);
        Log.d("UserRepository", "getIsAdmin: URL=" + call.request().url()); // Agrega este registro
        call.enqueue(callback);
    }

    public void registerUser(User user, Callback<User> callback) {
        Call<User> call = apiService.createUser(user);
        call.enqueue(callback);
    }
    public void updateUserPassword(String email, String newPassword, Callback<User> callback) {
        Call<User> call = apiService.updateUserPassword(email, newPassword);
        call.enqueue(callback);
    }
    public void getUsers(Callback<List<User>> callback) {
        Call<List<User>> call = apiService.getUsers();
        Log.d("UserRepository", "getUsers: URL=" + call.request().url()); // Agrega este registro
        call.enqueue(callback);
    }
    public void createUser(User user, Callback<User> callback) {
        Call<User> call = apiService.createUser(user);
        call.enqueue(callback);
    }
    public void updateUser(long id, User user, Callback<User> callback) {
        Call<User> call = apiService.updateUser(id, user);
        call.enqueue(callback);
    }

    public void deleteUser(long id, Callback<Void> callback) {
        Call<Void> call = apiService.deleteUser(id);
        call.enqueue(callback);
    }
}
