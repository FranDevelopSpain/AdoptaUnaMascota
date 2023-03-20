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
import com.tfg.adoptaunamascota.models.Admin;
import com.tfg.adoptaunamascota.models.User;
import com.tfg.adoptaunamascota.services.LoginService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString().trim();
                String password = passwordEt.getText().toString().trim();

                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(logging);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://adoptaunamascota.com/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                LoginService loginService = retrofit.create(LoginService.class);
                Call<User> call = loginService.USER_CALL(email, password);
                Call<Admin> call2 = loginService.ADMIN_CALL(email, password);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            mail.getText().clear();
                            passwordEt.getText().clear();
                            Toast.makeText(LoginActivity.this, "Se ha accedido con éxito", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        } else if (response.code() == 404) {
                            Toast.makeText(LoginActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 401) {
                            Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
                call2.enqueue(new Callback<Admin>() {
                    @Override
                    public void onResponse(Call<Admin> call, Response<Admin> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            mail.getText().clear();
                            passwordEt.getText().clear();
                            Toast.makeText(LoginActivity.this, "Se ha accedido con éxito", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivityAdmin.class);
                        } else if (response.code() == 404) {
                            Toast.makeText(LoginActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                        } else if (response.code() == 401) {
                            Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Admin> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View view, int position) {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}