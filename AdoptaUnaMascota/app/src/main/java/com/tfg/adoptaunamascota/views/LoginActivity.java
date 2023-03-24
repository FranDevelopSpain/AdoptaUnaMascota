package com.tfg.adoptaunamascota.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tfg.adoptaunamascota.Adapter;
import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.models.User;
import com.tfg.adoptaunamascota.repository.UserRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements Adapter.ItemClickListener {

    EditText mail;
    EditText passwordEt;
    TextView register;
    TextView forgetPassword;
    Button loginButton;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userRepository = new UserRepository(this);

        mail = findViewById(R.id.Email);
        passwordEt = findViewById(R.id.Password);
        register = findViewById(R.id.Register);
        forgetPassword = findViewById(R.id.passwordForget);
        loginButton = findViewById(R.id.BtnRegister);

        register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        forgetPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RecoverPasswordActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            String email = mail.getText().toString().trim();
            String password = passwordEt.getText().toString().trim();

            if (validateFills(email, password)) {
                loginUser(email, password);
            }
        });
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

    public void loginUser(String email, String password) {
        String hashedPassword = hashPassword(password);

        if (email.equals("admin@mail.com") && hashedPassword.equals("admin")) {
            Intent intent = new Intent(LoginActivity.this, HomeActivityAdmin.class);
            startActivity(intent);
        } else {
            User user = userRepository.getUser(email, hashedPassword);

            if (user != null) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String hashPassword(String password) {
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
    @Override
    public void onClick(View view, int position) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}