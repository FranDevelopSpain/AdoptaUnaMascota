package com.tfg.adoptaunamascota.views;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.models.users.User;
import com.tfg.adoptaunamascota.repository.UserRepository;
import com.tfg.adoptaunamascota.security.PasswordUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;

public class LoginActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    TextView registerTextView;
    TextView forgetPasswordTextView;
    Button loginButton;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userRepository = new UserRepository(this, "http://10.0.2.2:8080");
        emailEditText = findViewById(R.id.Email);
        passwordEditText = findViewById(R.id.Password);
        registerTextView = findViewById(R.id.Register);
        forgetPasswordTextView = findViewById(R.id.passwordForget);
        loginButton = findViewById(R.id.BtnRegister);

        registerTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        forgetPasswordTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RecoverPasswordActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String rawPassword = passwordEditText.getText().toString();
            String hashedPassword = PasswordUtil.hashPassword(rawPassword);


            Log.d("LoginActivity", "Iniciando sesión con email: " + email + " y contraseña: " + rawPassword);

            userRepository.getUserByEmailAndPassword(email, hashedPassword, new Callback<User>()  {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User user = response.body();
                        if (user != null && user.getId() != null) {
                            Log.d("LoginActivity", "User autenticado con éxito, id: " + user.getId());
                            saveUserId(user.getId());
                            runOnUiThread(() -> {
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            });
                        } else {
                            Log.d("LoginActivity", "Usuario o ID nulos.");
                            runOnUiThread(() -> {
                                validateFills(email, rawPassword);
                            });
                        }
                    } else {
                        Log.d("LoginActivity", "Respuesta no exitosa: " + response.code());
                        runOnUiThread(() -> {
                            Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                        });
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    runOnUiThread(() -> {
                        Toast.makeText(LoginActivity.this, "Error de red, inténtalo de nuevo", Toast.LENGTH_SHORT).show();
                    });
                }
            });
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        long userId = sharedPreferences.getLong("userId", -1);
        Log.d("LoginActivity", "UserId obtenido de SharedPreferences: " + userId);

        if (userId != -1) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public boolean validateFills(String email, String password) {
        if (email.isEmpty() && password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        } else if (email.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Rellene el campo de email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Rellene el campo de contraseña", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void saveUserId(Long userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("userId", userId);
        editor.commit(); // Cambiar apply() por commit() para asegurar que la escritura se completa antes de continuar

        // Verificación adicional para depurar
        long savedUserId = sharedPreferences.getLong("userId", -1);
        if (savedUserId == userId) {
            Log.d("LoginActivity", "UserId guardado correctamente: " + userId);
        } else {
            Log.e("LoginActivity", "Error al guardar UserId. Se esperaba " + userId + " pero se guardó " + savedUserId);
        }
    }
}
