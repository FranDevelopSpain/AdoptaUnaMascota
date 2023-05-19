package com.tfg.adoptaunamascota.views.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
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
import com.tfg.adoptaunamascota.adapters.OnAnimalClick;
import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.repository.AnimalRepository;
import com.tfg.adoptaunamascota.views.home.animalview.AnimalDetailActivity;
import com.tfg.adoptaunamascota.views.home.crudAdmin.AnimalsManagementActivity;
import com.tfg.adoptaunamascota.views.home.crudAdmin.UserManagementActivity;
import com.tfg.adoptaunamascota.views.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivityAdmin extends AppCompatActivity implements OnAnimalClick {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;
    private List<Animal> animalList;
    private RecyclerView animalRecyclerView;
    private AnimalAdapter animalAdapter;
    private AnimalRepository animalRepository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        animalRecyclerView = findViewById(R.id.recyclerView);
        animalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Animal> animals = new ArrayList<>();
        animalAdapter = new AnimalAdapter(animals, (Context) this, (OnAnimalClick) this);
        animalRecyclerView.setAdapter(animalAdapter);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.navigation_view);
        String baseUrl = "http://10.0.2.2:8080";
        animalRepository = new AnimalRepository(this, baseUrl);

        getAnimals();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                switch (id) {
                    case R.id.nav_animales:
                        // Ir a la actividad AnimalsManagementActivity
                        intent = new Intent(HomeActivityAdmin.this, AnimalsManagementActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_usuarios:
                        // Ir a la actividad UserManagementActivity
                        intent = new Intent(HomeActivityAdmin.this, UserManagementActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_cerra_sesion:
                        intent = new Intent(HomeActivityAdmin.this, LoginActivity.class);
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
        animalRecyclerView = findViewById(R.id.recyclerView);
        setupExpandableListView();
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });
    }
    private void setupExpandableListView() {
        HashMap<String, List<String>> expandableListDetail = new LinkedHashMap<>();

        List<String> dogs = new ArrayList<>();
        dogs.add("Perros pequeno");
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
            filterAnimalList(selectedItem.toLowerCase());
            return false;
        });

    }
    private void filterAnimalList(String filter) {
        animalAdapter.getFilter().filter(filter);
    }
    @Override
    public void onAnimalClick(int position, Animal animal) {
        Intent intent = new Intent(this, AnimalDetailActivity.class);
        intent.putExtra("selected_animal", animal);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void getAnimals() {
        animalRepository.getAnimals(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful()) {
                    animalList = new ArrayList<>(response.body());
                    Log.d("AnimalsManagementActivity", "Obtained " + animalList.size() + " animals from the server");
                    animalAdapter.setAnimalList(animalList);
                    animalAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(HomeActivityAdmin.this, "Error de respuesta: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                Log.e("AnimalsManagementActivity", "Error al obtener animales", t);
            }
        });
    }
}
