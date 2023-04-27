package com.tfg.adoptaunamascota.views.home.CrudAdmin;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.repository.AnimalRepository;

public class AnimalsManagementActivity extends AppCompatActivity {


    private AnimalRepository animalRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals_management);
        animalRepository = new AnimalRepository(this, "http://192.168.43.1:300/");

    }
}
