package com.tfg.adoptaunamascota.views;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tfg.adoptaunamascota.R;

public class RecoverPasswordActivity extends AppCompatActivity {

    EditText emailET;
    EditText passwordET;
    EditText passwords2ET;
    Button enviar;
    Button regresarBTN;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        passwords2ET = findViewById(R.id.password2ET);
        enviar = findViewById(R.id.enviarBtn);
        regresarBTN = findViewById(R.id.regresarBTN);

        regresarBTN.setOnClickListener(v -> {
            Intent intent = new Intent(RecoverPasswordActivity.this, LoginActivity.class);
            startActivity(intent);
        });


        enviar.setOnClickListener(v -> {
            String email = emailET.getText().toString().trim();
            String password = passwordET.getText().toString().trim();
            String password2 = passwords2ET.getText().toString().trim();
            ValidateFields();
        });
    }

    public void ValidateFields(){
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String password2 = passwords2ET.getText().toString().trim();
        if (email.isEmpty()){
            Toast.makeText(RecoverPasswordActivity.this, "El campo email no puede estar vacio", Toast.LENGTH_SHORT).show();
        }else if (password.isEmpty()){
            Toast.makeText(RecoverPasswordActivity.this, "El campo password no puede estar vacio", Toast.LENGTH_SHORT).show();
        }else if (password2.isEmpty()){
            Toast.makeText(RecoverPasswordActivity.this, "El campo password no puede estar vacio", Toast.LENGTH_SHORT).show();
        }else if (!password.equals(password2)){
            Toast.makeText(RecoverPasswordActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(RecoverPasswordActivity.this, "Se ha enviado la contraseña", Toast.LENGTH_SHORT).show();
        }
    }
}
