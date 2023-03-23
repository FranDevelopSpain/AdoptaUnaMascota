package com.tfg.adoptaunamascota.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tfg.adoptaunamascota.Adapter;
import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.consts.Constanst;
import com.tfg.adoptaunamascota.services.LoginService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements Adapter.ItemClickListener {

    EditText mail;
    EditText passwordEt;
    TextView register;
    TextView forgetPassword;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

            if (mail.getText().toString().isEmpty() || passwordEt.getText().toString().isEmpty()) {
                validateFills(email, password);
            } else {
                if (validateFills(email, password)) { // Si ambos campos tienen datos
                    verifyIfLoggedAdmin();
                    Toast.makeText(LoginActivity.this, "Se ha accedido con éxito", Toast.LENGTH_SHORT).show();
                }
            }

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constanst.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            LoginService loginService = retrofit.create(LoginService.class);
        });
    }
    public boolean validateFills(String email, String password){
        if (mail.getText().toString().isEmpty() && passwordEt.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mail.getText().toString().isEmpty()){
            Toast.makeText(LoginActivity.this, "Rellene el campo de email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (passwordEt.getText().toString().isEmpty()){
            Toast.makeText(LoginActivity.this, "Rellene el campo de contraseña", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
    public void verifyIfLoggedAdmin() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("mail", "");
        String savedPassword = sharedPreferences.getString("password", "");

        if (mail.getText().toString().equals("admin@mail.com") && passwordEt.getText().toString().equals("admin")) {
            Intent intent = new Intent(LoginActivity.this, HomeActivityAdmin.class);
            startActivity(intent);
        } else if (mail.getText().toString().equals(savedEmail) && passwordEt.getText().toString().equals(savedPassword)) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "No se ha encontrado usuario", Toast.LENGTH_SHORT).show();
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
