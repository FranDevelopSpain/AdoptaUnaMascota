package com.tfg.adoptaunamascota.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.tfg.adoptaunamascota.models.User;

public class UserRepository {
    private SharedPreferences sharedPreferences;

    public UserRepository(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }
    public void registerUser(String mail, String password, String name, String surname) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("mail", mail);
        editor.putString("password", password);
        editor.putString("name", name);
        editor.putString("surname", surname);
        editor.apply();
    }
    public User getUser(String mail, String password) {
        String userEmail = sharedPreferences.getString("mail", null);
        String userPassword = sharedPreferences.getString("password", null);

        if (userEmail != null && userPassword != null && userEmail.equals(mail) && userPassword.equals(password)) {
            String userName = sharedPreferences.getString("name", "");
            String userSurname = sharedPreferences.getString("surname", "");
            return new User(mail, password, userName, userSurname);
        }

        return null;
    }
}
