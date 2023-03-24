package com.tfg.adoptaunamascota.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {

    private EditText nombreET, apellidosET, emailET, passwordET, passwords2ET;
    private Button registrarseBTN, regresarBTN;
    private CheckBox aceptoCB;
    private SharedPreferences sharedPreferences;
    private UserRepository userRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userRepository = new UserRepository(this);

        apellidosET = findViewById(R.id.apellidosET);
        nombreET = findViewById(R.id.nombreET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        passwords2ET = findViewById(R.id.password2ET);
        aceptoCB = findViewById(R.id.aceptoCB);
        registrarseBTN = findViewById(R.id.registrarseBTN);
        regresarBTN = findViewById(R.id.regresarBTN);

        regresarBTN.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        registrarseBTN.setOnClickListener(v -> {
            if (validateFields() && validateCheckbox()) {
                registerUser();
            }
        });
    }

    private boolean validateFields() {
        if (nombreET.getText().toString().isEmpty() || apellidosET.getText().toString().isEmpty()
                || emailET.getText().toString().isEmpty() || passwordET.getText().toString().isEmpty()
                || passwords2ET.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Debe rellenar todos los campos",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (nombreET.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Rellene el campo de nombre",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (apellidosET.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Rellene el campo de apellidos",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (emailET.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Rellene el campo de email",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordET.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Rellene el campo de contraseña",
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (!passwordET.getText().toString().equals(passwords2ET.getText().toString())) {
            Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateCheckbox() {
        if (aceptoCB.isChecked()) {
            return true;
        } else {
            Toast.makeText(RegisterActivity.this, "Debe aceptar los términos y condiciones",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void registerUser() {
        String password = passwordET.getText().toString();
        String hashedPassword = hashPassword(password);
        userRepository.registerUser(
                emailET.getText().toString(),
                hashedPassword,
                nombreET.getText().toString(),
                apellidosET.getText().toString()
        );
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
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
}