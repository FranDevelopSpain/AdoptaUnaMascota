package com.tfg.adoptaunamascota;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText apellidosET;
    EditText nombreET;
    EditText emailET;
    EditText passwordET;
    EditText passwords2ET;
    Button registrarseBTN;

    CheckBox aceptoCB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_login);
        apellidosET = findViewById(R.id.apellidosET);
        nombreET = findViewById(R.id.nombreET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        passwords2ET = findViewById(R.id.password2ET);
        registrarseBTN = findViewById(R.id.registrarseBTN);
        aceptoCB = findViewById(R.id.aceptoCB);

        registrarseBTN.setOnClickListener(v -> {
            String nombre = nombreET.getText().toString().trim();
            String apellido = apellidosET.getText().toString().trim();
            String email = emailET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();
            String password2 = passwords2ET.getText().toString().trim();
        });


    }
}
