package com.tfg.adoptaunamascota.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.model.Login;
import com.tfg.adoptaunamascota.viewmodels.LoginViewModel;

public class MainActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    EditText mail;
    EditText password;
    TextView register;
    TextView login;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mail = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        register = findViewById(R.id.Register);
        login = findViewById(R.id.passwordForget);
        loginButton = findViewById(R.id.BtnRegister);
        loginViewModel = new LoginViewModel(new Login(mail.getText().toString(), password.getText().toString()));
        mail = loginViewModel.setUserEmail(mail.getText().toString());
        password = loginViewModel.setUserPassword(password.getText().toString());
        loginButton.setOnClickListener(v -> {
            runMe(v, loginViewModel.getToastMessage());
        });
    }
    @BindingAdapter({"mail"})
    public static void runMe(View view, String message) {
        if (message!= null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}