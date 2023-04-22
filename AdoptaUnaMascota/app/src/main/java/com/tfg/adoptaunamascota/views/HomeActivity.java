package com.tfg.adoptaunamascota.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.adapters.CustomExpandableListAdapter;
import com.tfg.adoptaunamascota.fragments.AnimalListFragment;
import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.models.animals.Cats;
import com.tfg.adoptaunamascota.models.animals.Dogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private ExpandableListView expandableListView;
    private LinearLayout animalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        expandableListView = findViewById(R.id.expandableListView);
        animalList = findViewById(R.id.animal_list);

        setupExpandableListView();
        AnimalListFragment animalListFragment = new AnimalListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.animal_list, animalListFragment)
                .commit();

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
            return false;
        });
    }

    private void filterAnimalList(String filter) {
        List<Animal> animals = getFilteredAnimals(filter);

        animalList.removeAllViews();

        for (Animal animal : animals) {
            View animalView = createAnimalView(animal);
            animalList.addView(animalView);
        }
    }

    private List<Animal> getFilteredAnimals(String filter) {
        List<Animal> animals = new ArrayList<>();

        if (filter.equals("Perros pequeños")) {
            animals.add(new Dogs("1", "Dog", "Macho"));
        } else if (filter.equals("Perros medianos")) {
            animals.add(new Dogs("2", "Dog", "Macho"));
        } else if (filter.equals("Perros grandes")) {
            animals.add(new Dogs("3", "Dog", "Macho"));
        } else if (filter.equals("Menos de 6 meses")) {
            animals.add(new Cats("4", "Cat", "Hembra"));
        } else if (filter.equals("Más de 6 meses")) {
            animals.add(new Cats("5", "Cat", "Hembra"));
        }


        return animals;
    }
    private View createAnimalView(Animal animal) {
        View animalView = getLayoutInflater().inflate(R.layout.animal_item, null);

        TextView animalName = animalView.findViewById(R.id.animal_name);
        TextView animalDescription = animalView.findViewById(R.id.animal_description);

        // Obtener el titulo y descripcion de 'animal'
        String titulo = String.valueOf(animal.getName());
        String descripcion = animal.getDescription();

        // Establecer el texto en los TextView correspondientes
        animalName.setText(titulo);
        animalDescription.setText(descripcion);

        animalView.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AnimalDetailActivity.class);
            intent.putExtra("animal", animal);
            startActivity(intent);
        });

        return animalView;
    }
}