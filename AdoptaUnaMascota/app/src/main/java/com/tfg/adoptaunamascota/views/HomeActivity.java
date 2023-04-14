package com.tfg.adoptaunamascota.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tfg.adoptaunamascota.R;
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
    private LinearLayout animalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        expandableListView = findViewById(R.id.expandableListView);
        animalList = findViewById(R.id.animal_list);

        setupExpandableListView();
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
            animals.add(new Dogs("Bobby", "Macho", "Perro pequeño y juguetón"));
        } else if (filter.equals("Perros medianos")) {
            animals.add(new Dogs("Rex", "Macho", "Perro mediano y amigable"));
        } else if (filter.equals("Perros grandes")) {
            animals.add(new Dogs("Max", "Macho", "Perro grande y protector"));
        } else if (filter.equals("Menos de 6 meses")) {
            animals.add(new Cats());
        } else if (filter.equals("Más de 6 meses")) {
            animals.add(new Cats("Luna", "Hembra", "Gato adulto de 1 año"));
        }

        return animals;
    }
    private View createAnimalView(Animal animal) {
        View animalView = getLayoutInflater().inflate(R.layout.animal_item, null);

        ImageView animalImage = animalView.findViewById(R.id.animal_image);
        TextView animalName = animalView.findViewById(R.id.animal_name);
        TextView animalGender = animalView.findViewById(R.id.animal_gender);
        TextView animalDescription = animalView.findViewById(R.id.animal_description);

        animalImage.setImageResource(animal.getImageResource());
        //animalName.setText(animal.getName());
        //animalGender.setText(animal.getGender());
        animalDescription.setText(animal.getDescription());

        animalView.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AnimalDetailActivity.class);
            intent.putExtra("animal", animal);
            startActivity(intent);
        });

        return animalView;
    }
}