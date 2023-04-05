package com.tfg.adoptaunamascota.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.models.users.User;


public class LoadingActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        progressBar = findViewById(R.id.progressBar);
        user = (User) getIntent().getSerializableExtra("user");

        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus++;
                android.os.SystemClock.sleep(50);
                runOnUiThread(() -> progressBar.setProgress(progressStatus));
            }
            if (user != null && user.getId() != null) {
                Log.d("LoadingActivity", "Usuario autenticado con éxito, id: " + user.getId());
                saveUserId(user.getId());
                Intent intent;
                if (user.getEmail().equals("admin") && user.getPassword().equals("admin")) {
                    intent = new Intent(LoadingActivity.this, HomeActivityAdmin.class);
                } else {
                    intent = new Intent(LoadingActivity.this, HomeActivity.class);
                }
                startActivity(intent);
                finish();
            } else {
                Log.d("LoadingActivity", "Usuario o ID nulos.");
                Toast.makeText(LoadingActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).start();
    }

    private void saveUserId(Long userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("userId", userId);
        editor.apply();

        long savedUserId = sharedPreferences.getLong("userId", 0);
        if (savedUserId == userId) {
            Log.d("LoadingActivity", "UserId guardado correctamente: " + userId);
        } else {
            Log.e("LoadingActivity", "Error al guardar UserId. Se esperaba " + userId + " pero se guardó " + savedUserId);
        }
    }
}