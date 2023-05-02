package com.tfg.adoptaunamascota.views.home.crudAdmin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.adapters.AnimalAdapter;
import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.repository.AnimalRepository;

import java.util.List;

public class AnimalsManagementActivity extends AppCompatActivity {


    private AnimalRepository animalRepository;
    private List<Animal> animalList;
    private RecyclerView userRecyclerView;
    private AnimalAdapter animalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals_management);
        animalRepository = new AnimalRepository(this, "http://192.168.43.1:300/");

    }
}
