package com.tfg.adoptaunamascota;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tfg.adoptaunamascota.models.Login;
import com.tfg.adoptaunamascota.services.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Adapter.ItemClickListener {

    EditText mail;
    EditText passwordEt;
    TextView register;
    TextView forgetPassword;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mail = findViewById(R.id.Email);
        passwordEt = findViewById(R.id.Password);
        register = findViewById(R.id.Register);
        forgetPassword = findViewById(R.id.passwordForget);
        loginButton = findViewById(R.id.BtnRegister);

        loginButton.setOnClickListener(v -> {
            String email = mail.getText().toString();
            String password = passwordEt.getText().toString();
            Login login = new Login(email, password);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.adoptaunamascota.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            LoginService loginService = retrofit.create(LoginService.class);
            Call<Login> call = loginService.login(login);

            call.enqueue(new Callback<Login>() {
                @Override
                public void onResponse(Call<Login> call, Response<Login> response) {
                    Login user = response.body();
                    if (response.isSuccessful()) {
                        switch (response.code()) {
                            case 200:
                                // La petición fue exitosa
                                if (user != null || !email.equals("admin@mail.com") || !password.equals("admin")){
                                    // Las credenciales son válidas
                                    Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                }
                                else if(user != null || email.equals("admin@mail.com") && password.equals("admin")) {
                                    Toast.makeText(MainActivity.this, "Se ha accedido con éxito", Toast.LENGTH_SHORT).show();
                                    Intent intent2 = new Intent(MainActivity.this, HomeActivityAdmin.class);
                                    startActivity(intent2);
                                } else {
                                    // Las credenciales son inválidas
                                    Toast.makeText(MainActivity.this, "Usuario o contraseña no válido", Toast.LENGTH_SHORT).show();
                                }
                                break;

                            case 401:
                                if (user == null || email.isEmpty() || password.isEmpty()) {
                                    Toast.makeText(MainActivity.this, "Usuario o contraseña no válido", Toast.LENGTH_SHORT).show();
                                }
                                break;

                            default:
                                Toast.makeText(MainActivity.this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Ups estamos revisando el problema", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<Login> call, Throwable t) {

                }
            });
        });

    }

    @Override
    public void onClick(View view, int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}