package com.tfg.adoptaunamascota;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Adapter.ItemClickListener {

    EditText mail;
    EditText password;
    TextView register;
    TextView forgetPassword;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mail = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        register = findViewById(R.id.Register);
        forgetPassword = findViewById(R.id.passwordForget);
        loginButton = findViewById(R.id.BtnRegister);
        loginButton.setOnClickListener(v -> {
            String email = mail.getText().toString();
            String passwordEt = password.getText().toString();
            if (email.isEmpty() || passwordEt.isEmpty()) {
                // Si el correo electrónico o la contraseña están vacíos, mostrar un mensaje de error
                Toast.makeText(this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
            } else if (!email.equals("admin@mail.com") && !passwordEt.equals("admin")) {
                // Si el correo electrónico y la contraseña son para el usuario admin, mostrar un mensaje de error
                Toast.makeText(this, "Usuario o contraseña no válido", Toast.LENGTH_SHORT).show();
            } else {
                // En caso contrario, mostrar un mensaje de éxito y abrir la siguiente pantalla
                Toast.makeText(this, "Se ha accedido con éxito", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view, int position) {

    }
}