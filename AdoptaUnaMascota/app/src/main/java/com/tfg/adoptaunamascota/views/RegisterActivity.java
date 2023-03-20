package com.tfg.adoptaunamascota.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.tfg.adoptaunamascota.R;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class RegisterActivity extends AppCompatActivity {

    EditText nombreET;
    EditText apellidosET;
    EditText emailET;
    EditText passwordET;
    EditText passwords2ET;
    Button registrarseBTN;
    Button regresarBTN;
    CheckBox aceptoCB;

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
            String nombre = nombreET.getText().toString().trim();
            String apellido = apellidosET.getText().toString().trim();
            String email = emailET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();
            String password2 = passwords2ET.getText().toString().trim();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            validateFields(v);
            validateCheckbox(v);

        });
    }
    public void validateFields (View view){
        if (nombreET.getText().toString().isEmpty() && apellidosET.getText().toString().isEmpty()
                && emailET.getText().toString().isEmpty() && passwordET.getText().toString().isEmpty()
                && passwords2ET.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Debe rellenar todos los campos",
                    Toast.LENGTH_SHORT).show();
        } else if (nombreET.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Rellene el campo de nombre",
                    Toast.LENGTH_SHORT).show();
        } else if (apellidosET.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Rellene el campo de apellidos",
                    Toast.LENGTH_SHORT).show();
        } else if (emailET.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Rellene el campo de email",
                    Toast.LENGTH_SHORT).show();
        } else if (passwordET.getText().toString().isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Rellene el campo de contraseña",
                    Toast.LENGTH_SHORT).show();
        } else if (passwordET.getText().toString().length() < 8 && passwords2ET.getText().toString().length() < 8
                && passwordET.getText().equals(passwords2ET.getText())) {
            Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(RegisterActivity.this, "Registrado con éxito",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void validateCheckbox (View view){
        if (aceptoCB.isChecked()) {
            Toast.makeText(RegisterActivity.this, "Registrado con éxito",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(RegisterActivity.this, "Debe aceptar los términos y condiciones",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
