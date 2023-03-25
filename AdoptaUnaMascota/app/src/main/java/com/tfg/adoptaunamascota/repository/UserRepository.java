package com.tfg.adoptaunamascota.repository;

import android.content.Context;
import android.content.SharedPreferences;
import com.tfg.adoptaunamascota.models.users.User;
import org.json.JSONException;
import org.json.JSONObject;

public class UserRepository {
    private SharedPreferences sharedPreferences;

    public UserRepository(Context context) {
        sharedPreferences = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
    }

    public void registerUser(String mail, String password, String name, String surname) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Guardar los datos del usuario en un objeto JSON
        JSONObject userJson = new JSONObject();
        try {
            userJson.put("mail", mail);
            userJson.put("password", password);
            userJson.put("name", name);
            userJson.put("surname", surname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Almacenar el objeto JSON como String en SharedPreferences usando el correo como clave
        editor.putString(mail, userJson.toString());
        editor.apply();
    }

    public User getUser(String mail, String password) {
        String userJsonString = sharedPreferences.getString(mail, null);
        if (userJsonString != null) {
            try {
                JSONObject userJson = new JSONObject(userJsonString);
                String userEmail = userJson.getString("mail");
                String userPassword = userJson.getString("password");

                if (userEmail.equals(mail) && userPassword.equals(password)) {
                    String userName = userJson.getString("name");
                    String userSurname = userJson.getString("surname");
                    return new User(mail, password, userName, userSurname);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
