package com.tfg.adoptaunamascota.repository;

import android.content.Context;
import com.tfg.adoptaunamascota.models.users.User;
import com.tfg.adoptaunamascota.service.ApiService;
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

    public User getUser(String email, String password) {
        // Implementar la lógica para obtener el usuario en base al email y password
        // desde SharedPreferences o la API
        return user;
    }

    public void registerUser(String mail, String password, String name, String surname) {
        String hashedPassword = hashPassword(password);
        User user = new User(mail, hashedPassword, name, surname);
        Call<User> call = apiService.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // hacer algo con la respuesta
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // manejar el error
            }
        });
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
