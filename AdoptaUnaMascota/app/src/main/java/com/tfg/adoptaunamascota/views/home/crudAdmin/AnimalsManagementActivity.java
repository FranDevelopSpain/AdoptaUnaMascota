package com.tfg.adoptaunamascota.views.home.crudAdmin;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.adapters.AnimalAdapter;
import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.repository.AnimalRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalsManagementActivity extends AppCompatActivity {

    private AnimalRepository animalRepository;
    private List<Animal> animalList;
    private RecyclerView animalRecyclerView;
    private AnimalAdapter animalAdapter;
    private Animal selectedAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals_management);

        animalRecyclerView = findViewById(R.id.recyclerView);
        animalRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa el adaptador con una lista vacía y pasa el contexto y la instancia de AnimalsManagementActivity
        animalAdapter = new AnimalAdapter(new ArrayList<>(), this, this);
        animalRecyclerView.setAdapter(animalAdapter);

        String baseUrl = "http://192.168.43.1:300";

        animalRepository = new AnimalRepository(this, baseUrl);
        Button addAnimalButton = findViewById(R.id.add_animal_button);
        Button updateAnimalButton = findViewById(R.id.btnUpdate);
        Button deleteAnimalButton = findViewById(R.id.btnDelete);

        getAnimals();

        addAnimalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAnimalDialog();
            }
        });
        updateAnimalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animal selectedAnimal = animalAdapter.getSelectedAnimal();
                if (selectedAnimal != null) {
                    showUpdateAnimalDialog(selectedAnimal);
                } else {
                    Toast.makeText(AnimalsManagementActivity.this, "Por favor, seleccione un animal para actualizar.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        deleteAnimalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animal selectedAnimal = animalAdapter.getSelectedAnimal();
                if (selectedAnimal != null) {
                    deleteAnimal(selectedAnimal);
                } else {
                    Toast.makeText(AnimalsManagementActivity.this, "Por favor, seleccione un animal para eliminar.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setSelectedAnimal(Animal animal) {
        this.selectedAnimal = animal;
    }

    private void showAddAnimalDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_animal, null);
        builder.setView(dialogView);

        final EditText nameEditText = dialogView.findViewById(R.id.dialog_animal_name);
        final EditText speciesEditText = dialogView.findViewById(R.id.dialog_animal_species);
        final EditText ageEditText = dialogView.findViewById(R.id.dialog_animal_age);

        builder.setTitle("Agregar Animal");
        builder.setPositiveButton("Agregar", (dialog, which) -> {
            String name = nameEditText.getText().toString();
            String species = speciesEditText.getText().toString();
            int ageInMonths = Integer.parseInt(ageEditText.getText().toString());
            int age = ageInMonths / 12;
            Animal newAnimal = new Animal(name, species, age);

            animalRepository.createAnimal(newAnimal, new Callback<Animal>() {
                @Override
                public void onResponse(Call<Animal> call, Response<Animal> response) {
                    if (response.isSuccessful()) {
                        getAnimals(); // Actualiza la lista de animales
                    } else {
                        Toast.makeText(AnimalsManagementActivity.this, "Por favor, ingrese todos los campos.", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<Animal> call, Throwable t) {
                    Toast.makeText(AnimalsManagementActivity.this, "Error al crear animal", Toast.LENGTH_LONG).show();
                }
            });
        });
        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showUpdateAnimalDialog(final Animal animal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_animal, null);
        builder.setView(dialogView);
        final EditText nameEditText = dialogView.findViewById(R.id.dialog_animal_name);
        final EditText speciesEditText = dialogView.findViewById(R.id.dialog_animal_species);
        final EditText ageEditText = dialogView.findViewById(R.id.dialog_animal_age);

        nameEditText.setText(animal.getName());
        speciesEditText.setText(animal.getSpecies());
        ageEditText.setText(String.valueOf(animal.getEdad()));

        builder.setTitle("Actualizar Animal");
        builder.setPositiveButton("Actualizar", (dialog, which) -> {
            String updatedName = nameEditText.getText().toString();
            String updatedSpecies = speciesEditText.getText().toString();
            String updatedAgeString = ageEditText.getText().toString();

            if (TextUtils.isEmpty(updatedName) || TextUtils.isEmpty(updatedSpecies) || TextUtils.isEmpty(updatedAgeString)) {
                Toast.makeText(AnimalsManagementActivity.this, "Por favor, ingrese todos los campos.", Toast.LENGTH_LONG).show();
            } else {
                int updatedAgeInMonths = Integer.parseInt(updatedAgeString);
                int updatedAge = updatedAgeInMonths / 12;
                animal.setName(updatedName);
                animal.setSpecies(updatedSpecies);
                animal.setEdad(updatedAge);

                animalRepository.updateAnimal(animal.getId(), animal, new Callback<Animal>() {
                    @Override
                    public void onResponse(Call<Animal> call, Response<Animal> response) {
                        if (response.isSuccessful()) {
                            getAnimals(); // Actualiza la lista de animales
                        } else {
                            Toast.makeText(AnimalsManagementActivity.this, "Error al actualizar animal: " + response.code(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Animal> call, Throwable t) {
                        Toast.makeText(AnimalsManagementActivity.this, "Error al actualizar animal", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteAnimal(final Animal animal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Animal");
        builder.setMessage("¿Estás seguro de que deseas eliminar este animal?");
        builder.setPositiveButton("Eliminar", (dialog, which) -> animalRepository.deleteAnimal(animal.getId(), new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {
                getAnimals(); // Actualiza la lista de animales
            } else {
                Toast.makeText(AnimalsManagementActivity.this, "Error al eliminar animal: " + response.code(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            Toast.makeText(AnimalsManagementActivity.this, "Error al eliminar animal", Toast.LENGTH_LONG).show();
        }
    }));
        builder.setNegativeButton("Cancelar", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void getAnimals() {
        animalRepository.getAnimals(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful()) {
                    animalList = response.body();

                    // Añade los gatos y perros a la lista
                    animalList.add(new Animal("Felipe", "Gato", 7, R.drawable.gato1, "Gato de 7 meses super cute"));
                    animalList.add(new Animal("Michi", "Gato", 3, R.drawable.gato2, "Gatito de 3 meses adorable"));
                    animalList.add(new Animal("Sifu", "Gato", 5, R.drawable.gato3, "Gatita buscando hogar de 5 meses"));
                    animalList.add(new Animal("Max", "Perro", 12, R.drawable.perro1, "Perro pequeño juguetón"));
                    animalList.add(new Animal("Aquiles", "Perro", 24, R.drawable.perro2, "Perro grande y fiel"));

                    // Actualiza el adaptador con la lista de animales
                    animalAdapter.setAnimalList(animalList);

                    // Notifica al RecyclerView que los datos han cambiado
                    animalAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(AnimalsManagementActivity.this, "Error de respuesta: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                Log.e("AnimalsManagementActivity", "Error al obtener animales", t);
            }
        });
    }

}
