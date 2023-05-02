package com.tfg.adoptaunamascota.views.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.adapters.AnimalAdapter;
import com.tfg.adoptaunamascota.adapters.CustomExpandableListAdapter;
import com.tfg.adoptaunamascota.adapters.ExpandableListDataPump;
import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.models.animals.Cats;
import com.tfg.adoptaunamascota.models.animals.Dogs;
import com.tfg.adoptaunamascota.views.home.animalview.AnimalDetailActivity;
import com.tfg.adoptaunamascota.views.home.crudAdmin.AnimalsManagementActivity;
import com.tfg.adoptaunamascota.views.home.crudAdmin.UserManagementActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class HomeActivityAdmin extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private RecyclerView animalList;
    private AnimalAdapter animalAdapter;

    private AnimalsManagementActivity animalsManagementActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        animalList = findViewById(R.id.animal_list);
        animalList.setLayoutManager(new LinearLayoutManager(this));
        List<Animal> animals = new ArrayList<>();
        animalsManagementActivity = new AnimalsManagementActivity();
        animalAdapter = new AnimalAdapter(animals, this, animalsManagementActivity);
        animalList.setAdapter(animalAdapter);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.navigation_view);
        loadInitialAnimals();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                switch (id) {
                    case R.id.nav_animales:
                        // Código para mostrar la opción "Animales"
                        break;
                    case R.id.nav_usuarios:
                        // Ir a la actividad UserManagementActivity
                        intent = new Intent(HomeActivityAdmin.this, UserManagementActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        return true;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView = findViewById(R.id.expandableListView);
        animalList = findViewById(R.id.animal_list);
        setupExpandableListView();
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
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
        animalAdapter.setAnimalList(animals);
        animalAdapter.notifyDataSetChanged();
    }

    private List<Animal> getFilteredAnimals(String filter) {
        List<Animal> allAnimals = getAllAnimals(); // Obtén la lista completa de animales
        List<Animal> filteredAnimals = new ArrayList<>();

        for (Animal animal : allAnimals) {
            if (filter.equals("Perros pequeños") && animal.getType().equals("Dog") && animal.getSize().equals("Pequeño")) {
                filteredAnimals.add(animal);
            } else if (filter.equals("Perros medianos") && animal.getType().equals("Dog") && animal.getSize().equals("Mediano")) {
                filteredAnimals.add(animal);
            } else if (filter.equals("Perros grandes") && animal.getType().equals("Dog") && animal.getSize().equals("Grande")) {
                filteredAnimals.add(animal);
            } else if (filter.equals("Menos de 6 meses") && animal.getType().equals("Cat") && animal.getAge() < 6) {
                filteredAnimals.add(animal);
            } else if (filter.equals("Más de 6 meses") && animal.getType().equals("Cat") && animal.getAge() >= 6) {
                filteredAnimals.add(animal);
            }
        }

        return filteredAnimals;
    }

    private View createAdminAnimalView(Animal animal) {
        View animalView = getLayoutInflater().inflate(R.layout.animal_item, null);

        TextView animalName = animalView.findViewById(R.id.animal_name);
        TextView animalDescription = animalView.findViewById(R.id.animal_description);

        String titulo = String.valueOf(animal.getName());
        String descripcion = animal.getDescription();

        animalName.setText(titulo);
        animalDescription.setText(descripcion);

        animalView.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivityAdmin.this, AnimalDetailActivity.class);
            intent.putExtra("animal", animal);
            startActivity(intent);
        });

        return animalView;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void loadInitialAnimals() {
        List<Animal> animals = getAllAnimals();
        animalAdapter.setAnimalList(animals);
        animalAdapter.notifyDataSetChanged();
    }
    private List<Animal> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();
        animals.add(new Dogs(1L, "Dog1", "Macho", "Pequeño", 24, R.drawable.perro1));
        animals.add(new Dogs(2L, "Dog2", "Macho", "Mediano", 36, R.drawable.perro2));
        animals.add(new Dogs(3L, "Dog3", "Macho", "Grande", 48, R.drawable.perro3));
        animals.add(new Cats(4L, "Cat1", "Hembra", 5, R.drawable.gato1)); // Edad en meses
        animals.add(new Cats(5L, "Cat2", "Hembra", 9, R.drawable.gato2)); // Edad en meses
        // ... agrega todos los animales aquí
        return animals;
    }
}