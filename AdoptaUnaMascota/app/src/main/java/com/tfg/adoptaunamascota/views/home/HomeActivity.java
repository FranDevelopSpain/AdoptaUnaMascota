package com.tfg.adoptaunamascota.views.home;

import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.adapters.AnimalAdapter;
import com.tfg.adoptaunamascota.adapters.CustomExpandableListAdapter;
import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.models.animals.Cats;
import com.tfg.adoptaunamascota.models.animals.Dogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private RecyclerView animalList;
    private AnimalAdapter animalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        expandableListView = findViewById(R.id.expandableListView);
        animalList = findViewById(R.id.animal_list);
        animalList.setLayoutManager(new LinearLayoutManager(this));

        setupExpandableListView();

        List<Animal> animals = new ArrayList<>();
        animalAdapter = new AnimalAdapter(animals);
        animalList.setAdapter(animalAdapter);
        loadInitialAnimals();
    }
    private void setupExpandableListView() {
        HashMap<String, List<String>> expandableListDetail = new LinkedHashMap<>();
        List<String> dogs = new ArrayList<>();
        dogs.add("Perros pequeños");
        dogs.add("Perros medianos");
        dogs.add("Perros grandes");

        List<String> cats = new ArrayList<>();
        cats.add("Menos de 6 meses");
        cats.add("Más de 6 meses");

        expandableListDetail.put("Adoptar un perro", dogs);
        expandableListDetail.put("Adoptar un gato", cats);

        List<String> expandableListTitle = new ArrayList<>(expandableListDetail.keySet());

        ExpandableListAdapter expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            String selectedItem = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
            filterAnimalList(selectedItem);
            return true;
        });
    }

    private void filterAnimalList(String filter) {
        List<Animal> animals = getFilteredAnimals(filter);
        animalAdapter.setAnimalList(animals);
        animalAdapter.notifyDataSetChanged();
    }


    private List<Animal> getFilteredAnimals(String filter) {
        List<Animal> animals = new ArrayList<>();

        if (filter.equals("Perros pequeños")) {
            animals.add(new Dogs("1", "Dog", "Macho", R.drawable.perro1));
        } else if (filter.equals("Perros medianos")) {
            animals.add(new Dogs("2", "Dog", "Macho", R.drawable.perro2));
        } else if (filter.equals("Perros grandes")) {
            animals.add(new Dogs("3", "Dog", "Macho", R.drawable.perro3));
        } else if (filter.equals("Menos de 6 meses")) {
            animals.add(new Cats("4", "Cat", "Hembra", R.drawable.gato1));
        } else if (filter.equals("Más de 6 meses")) {
            animals.add(new Cats("5", "Cat", "Hembra", R.drawable.gato2));
        }

        return animals;
    }
    private void loadInitialAnimals() {
        List<Animal> animals = getAllAnimals();
        animalAdapter.setAnimalList(animals);
        animalAdapter.notifyDataSetChanged();
    }
    private List<Animal> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Dogs("1", "Dog1", "Macho", R.drawable.perro1));
        animals.add(new Dogs("2", "Dog2", "Macho", R.drawable.perro2));
        animals.add(new Dogs("3", "Dog", "Macho", R.drawable.perro3));
        animals.add(new Cats("4", "Cat", "Hembra", R.drawable.gato1));
        animals.add(new Cats("5", "Cat", "Hembra", R.drawable.gato2));
        // ... agrega todos los animales aquí
        return animals;
    }
}