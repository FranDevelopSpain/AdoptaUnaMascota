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
import com.tfg.adoptaunamascota.models.Register;
import com.tfg.adoptaunamascota.services.RegisterService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    EditText nombreET;
    EditText apellidosET;
    EditText emailET;
    EditText passwordET;
    EditText passwords2ET;
    Button registrarseBTN;
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

        registrarseBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String nombre = nombreET.getText().toString().trim();
                String apellido = apellidosET.getText().toString().trim();
                String email = emailET.getText().toString().trim();
                String password = passwordET.getText().toString().trim();
                String password2 = passwords2ET.getText().toString().trim();
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(logging);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://adoptaunamascota.com/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RegisterService registerService = retrofit.create(RegisterService.class);
                Call<Register> call = registerService.USER_CALL_REGISTER(nombre, apellido, email, password, password2);

                call.enqueue(new Callback<Register>() {
                    @Override
                    public void onResponse(Call<Register> call, Response<Register> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            nombreET.getText().clear();
                            apellidosET.getText().clear();
                            emailET.getText().clear();
                            passwordET.getText().clear();
                            passwords2ET.getText().clear();
                            Toast.makeText(RegisterActivity.this, "Se ha registrado con éxito", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Cumplimente los campos vacios", Toast.LENGTH_SHORT).show();
                            Toast.makeText(RegisterActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<Register> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
