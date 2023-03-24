package com.tfg.adoptaunamascota.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.models.Register;
import com.tfg.adoptaunamascota.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.tfg.adoptaunamascota.consts.Constanst;
import com.tfg.adoptaunamascota.services.ApiClient;
import com.tfg.adoptaunamascota.services.ApiService;

public class RegisterActivity extends AppCompatActivity {

    private EditText nombreET, apellidosET, emailET, passwordET, passwords2ET;
    private Button registrarseBTN, regresarBTN;
    private CheckBox aceptoCB;
    private SharedPreferences sharedPreferences;

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
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        Retrofit retrofit = ApiClient.getClient(Constanst.BASE_URL);
        ApiService service = retrofit.create(ApiService.class);
        Register userData = new Register(
                nombreET.getText().toString(),
                apellidosET.getText().toString(),
                emailET.getText().toString(),
                passwordET.getText().toString()
        );
        Call<User> call = service.USER_CALL_REGISTER(
                nombreET.getText().toString(),
                apellidosET.getText().toString(),
                emailET.getText().toString(),
                passwordET.getText().toString()
        );

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("mail", userData.getMail());
                    editor.putString("password", userData.getPassword());
                    editor.putString("name", userData.getName());
                    editor.putString("surname", userData.getSurname());
                    editor.apply();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Error: " + response.message(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error de red: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}