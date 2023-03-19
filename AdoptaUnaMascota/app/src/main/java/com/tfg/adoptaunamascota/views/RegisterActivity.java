package com.tfg.adoptaunamascota.views;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.models.Register;
import com.tfg.adoptaunamascota.services.LoginService;
import com.tfg.adoptaunamascota.services.RegisterService;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
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
        registrarseBTN = findViewById(R.id.registrarseBTN);
        aceptoCB = findViewById(R.id.aceptoCB);

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
            }
        });
    }
}
