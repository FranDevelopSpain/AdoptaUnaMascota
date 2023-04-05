package com.tfg.adoptaunamascota.repository;
import android.content.Context;
import com.tfg.adoptaunamascota.models.users.User;
import com.tfg.adoptaunamascota.service.ApiService;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


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
        call.enqueue(callback);
    }


    public void registerUser(User user, Callback<User> callback) {
        // Elimina la línea que genera el hashedPassword
        Call<User> call = apiService.createUser(user);
        call.enqueue(callback);
    }

    public static String hashPassword(String rawPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
