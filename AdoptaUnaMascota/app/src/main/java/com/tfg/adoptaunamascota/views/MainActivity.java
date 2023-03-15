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
        EditText etEmail = mail;
        etEmail.findViewById(R.id.Email);
        EditText etPassword = password;
        etPassword.findViewById(R.id.Password);
        TextView tvRegister = register;
        tvRegister.findViewById(R.id.Register);
        TextView tvLogin = login;
        tvLogin.findViewById(R.id.passwordForget);
        Button btnLogin = loginButton;
        btnLogin.findViewById(R.id.BtnRegister);
        loginViewModel = new LoginViewModel();
        mail = loginViewModel.setUserEmail(etEmail.getText().toString());
        password = loginViewModel.setUserPassword(etPassword.getText().toString());
        btnLogin.setOnClickListener(v -> {
            runMe(v, loginViewModel.getToastMessage());
            loginViewModel.onLoginClicked();
        });


    }
    @BindingAdapter({"toastMessage"})
    public static void runMe(View view, String message) {
        if (message != null)
            Toast.makeText(view.getContext(), message, Toast.LENGTH_SHORT).show();
    }
}