package com.tfg.adoptaunamascota.repository;

import android.content.Context;
import com.tfg.adoptaunamascota.models.users.User;
import com.tfg.adoptaunamascota.service.ApiService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {
    private ApiService apiService;
    private User user;

    public UserRepository(Context context, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public void getUserByEmailAndPassword(String email, String hashedPassword, Callback<User> callback) {
        Call<User> call = apiService.getUserByEmailAndPassword(email, hashedPassword);
        call.enqueue(callback);
    }


    public void registerUser(User user, Callback<User> callback) {
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        Call<User> call = apiService.createUser(user);
        call.enqueue(callback);
    }


    private String hashPassword(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for (byte hashByte : hashBytes) {
                stringBuilder.append(String.format("%02x", hashByte));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
