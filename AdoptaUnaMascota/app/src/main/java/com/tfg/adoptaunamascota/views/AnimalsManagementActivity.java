package com.tfg.adoptaunamascota.views;

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
        animalRepository = new AnimalRepository(this);

        // Aquí puedes agregar botones y funciones para realizar operaciones CRUD
        // en usuarios y animales utilizando los métodos proporcionados por
        // AnimalRepository.
    }
}
