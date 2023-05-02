package com.tfg.adoptaunamascota.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.models.users.User;
import com.tfg.adoptaunamascota.repository.UserRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText nombreET, apellidosET, emailET, passwordET, passwords2ET;
    private Button registrarseBTN, regresarBTN;
    private CheckBox aceptoCB;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        apellidosET = findViewById(R.id.apellidosET);
        nombreET = findViewById(R.id.nombreET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        passwords2ET = findViewById(R.id.password2ET);
        aceptoCB = findViewById(R.id.aceptoCB);
        registrarseBTN = findViewById(R.id.registrarseBTN);
        regresarBTN = findViewById(R.id.regresarBTN);

        String baseUrl = "http://10.0.2.2:8080";

        userRepository = new UserRepository(this, baseUrl);

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
        User user = new User(
                emailET.getText().toString(),
                password, // Cambiado a password
                nombreET.getText().toString(),
                apellidosET.getText().toString()
        );
        userRepository.registerUser(user, new Callback<User>(){
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User registeredUser = response.body();
                    runOnUiThread(() -> {
                        Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        assert registeredUser != null;
                        intent.putExtra("email", registeredUser.getEmail());
                        intent.putExtra("password", password);
                        startActivity(intent);
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(RegisterActivity.this, "Error en el registro", Toast.LENGTH_SHORT).show();
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                // manejar el error
            }
        });
    }

}