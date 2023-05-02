package com.tfg.adoptaunamascota.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.models.users.User;
import com.tfg.adoptaunamascota.repository.UserRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoverPasswordActivity extends AppCompatActivity {

    EditText emailET;
    EditText passwordET;
    EditText passwords2ET;
    Button enviar;
    Button regresarBTN;
    UserRepository userRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        passwords2ET = findViewById(R.id.password2ET);
        enviar = findViewById(R.id.enviarBtn);
        regresarBTN = findViewById(R.id.regresarBTN);

        userRepository = new UserRepository(this, "http://10.0.2.2:8080");

        regresarBTN.setOnClickListener(v -> {
            Intent intent = new Intent(RecoverPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
        });


        enviar.setOnClickListener(v -> {
            String email = emailET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();
            String password2 = passwords2ET.getText().toString().trim();
            if (validateFields(email, password, password2)) {
                updatePassword(email, password);
            }
        });
    }

    public boolean validateFields(String email, String password, String password2) {
        if (email.isEmpty()) {
            Toast.makeText(RecoverPasswordActivity.this, "El campo email no puede estar vacio", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()) {
            Toast.makeText(RecoverPasswordActivity.this, "El campo password no puede estar vacio", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password2.isEmpty()) {
            Toast.makeText(RecoverPasswordActivity.this, "El campo password no puede estar vacio", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!password.equals(password2)) {
            Toast.makeText(RecoverPasswordActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void updatePassword(String email, String password) {
        userRepository.updateUserPassword(email, password, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User userRecoverPassword = response.body();
                    Toast.makeText(RecoverPasswordActivity.this, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RecoverPasswordActivity.this, LoginActivity.class);
                    assert userRecoverPassword != null;
                    intent.putExtra("email", userRecoverPassword.getEmail());
                    intent.putExtra("password", password);
                    startActivity(intent);
                } else {
                    Toast.makeText(RecoverPasswordActivity.this, "No se pudo actualizar la contraseña", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RecoverPasswordActivity.this, "Error al actualizar la contraseña", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
