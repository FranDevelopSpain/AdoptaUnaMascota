package com.tfg.adoptaunamascota.views;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.repository.UserRepository;

public class UserManagementActivity extends AppCompatActivity {
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management);
        userRepository = new UserRepository(this, "http://192.168.43.1:300/");

        // Aquí puedes agregar botones y funciones para realizar operaciones CRUD
        // en usuarios utilizando los métodos proporcionados por
        // UserRepository
    }

}
