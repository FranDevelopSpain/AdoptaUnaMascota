package com.tfg.adoptaunamascota.views.home.crudAdmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.adapters.AnimalAdapter;
import com.tfg.adoptaunamascota.adapters.DrawableImagePickerAdapter;
import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.repository.AnimalRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    private ImageView imageAnimal;
    private AlertDialog drawableImagePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals_management);
        animalRecyclerView = findViewById(R.id.recyclerView);
        animalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Inicializa el adaptador con una lista vacía y pasa el contexto y la instancia de AnimalsManagementActivity
        animalAdapter = new AnimalAdapter(new ArrayList<>(), this, this);
        animalRecyclerView.setAdapter(animalAdapter);
        String baseUrl = "http://10.0.2.2:8080";
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
        final EditText genderEditText = dialogView.findViewById(R.id.dialog_animal_gender);
        final EditText razaEditText = dialogView.findViewById(R.id.dialog_animal_breed_spinner);
        final EditText ageEditText = dialogView.findViewById(R.id.dialog_animal_age);
        final EditText descriptionEditText = dialogView.findViewById(R.id.dialog_animal_description);
        imageAnimal = dialogView.findViewById(R.id.dialog_animal_image);
        final Button selectImageButton = dialogView.findViewById(R.id.dialog_animal_select_image);
        final RadioGroup animalTypeRadioGroup = dialogView.findViewById(R.id.dialog_animal_type_radio_group);
        final RadioButton dogRadioButton = dialogView.findViewById(R.id.dialog_animal_type_dog);
        final RadioButton catRadioButton = dialogView.findViewById(R.id.dialog_animal_type_cat);
        selectImageButton.setOnClickListener(v -> {
            startDrawableImagePickerDialog();
        });
        builder.setTitle("Agregar Animal");
        builder.setPositiveButton("Agregar", (dialog, which) -> {
            String name = nameEditText.getText().toString();
            String category = speciesEditText.getText().toString();
            String gender = genderEditText.getText().toString();
            String raza = razaEditText.getText().toString();
            String age = ageEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            String animalType = dogRadioButton.isChecked() ? "Dog" : "Cat";
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(category) && !TextUtils.isEmpty(raza) && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(description) && imageAnimal.getDrawable() != null) {
                imageAnimal.setDrawingCacheEnabled(true);
                byte[] imageBytes = bitmapToByteArray(imageAnimal.getDrawingCache());
                String imageBase64 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                Animal animal = new Animal(name, category, raza, Integer.parseInt(age), description, animalType, imageBase64,gender);
                addAnimal(animal);
                animalAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(AnimalsManagementActivity.this, "Por favor, complete todos los campos y seleccione una imagen.", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            String scheme = selectedImageUri.getScheme();
            Bitmap bitmap = null;
            try {
                if (scheme.equals("content")) {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                } else if (scheme.equals("file")) {
                    int imageResource = getResources().getIdentifier(selectedImageUri.getPath(), "drawable", getPackageName());
                    bitmap = BitmapFactory.decodeResource(getResources(), imageResource);
                }
                if (bitmap != null) {
                    imageAnimal.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(AnimalsManagementActivity.this, "No se pudo obtener la imagen.", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void showUpdateAnimalDialog(final Animal animal) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_animal, null);
        builder.setView(dialogView);

        final EditText nameEditText = dialogView.findViewById(R.id.dialog_animal_name);
        final EditText speciesEditText = dialogView.findViewById(R.id.dialog_animal_species);
        final EditText genderEditText = dialogView.findViewById(R.id.dialog_animal_gender);
        final EditText breedEditText = dialogView.findViewById(R.id.dialog_animal_breed_spinner);
        final EditText ageEditText = dialogView.findViewById(R.id.dialog_animal_age);
        final RadioGroup animalTypeRadioGroup = dialogView.findViewById(R.id.dialog_animal_type_radio_group);
        final RadioButton dogRadioButton = dialogView.findViewById(R.id.dialog_animal_type_dog);
        final RadioButton catRadioButton = dialogView.findViewById(R.id.dialog_animal_type_cat);
        final EditText descriptionEditText = dialogView.findViewById(R.id.dialog_animal_description);

        nameEditText.setText(animal.getName());
        speciesEditText.setText(animal.getSpecies());
        ageEditText.setText(String.valueOf(animal.getEdad()));
        descriptionEditText.setText(animal.getDescription());

        // Aquí necesitas un método para establecer el tipo de animal en el radio group
        // Esto dependerá de cómo estés almacenando y recuperando esta información en tu clase Animal

        builder.setTitle("Actualizar Animal");
        builder.setPositiveButton("Actualizar", (dialog, which) -> {
            String updatedName = nameEditText.getText().toString();
            String updatedSpecies = speciesEditText.getText().toString();
            String updatedGender = genderEditText.getText().toString();
            String updatedBreed = breedEditText.getText().toString();
            String updatedAgeString = ageEditText.getText().toString();
            String updatedDescription = descriptionEditText.getText().toString();
            // Aquí necesitas un método para obtener el tipo de animal del radio group

            if (TextUtils.isEmpty(updatedName) || TextUtils.isEmpty(updatedSpecies) || TextUtils.isEmpty(updatedGender)
                    || TextUtils.isEmpty(updatedBreed) || TextUtils.isEmpty(updatedAgeString) || TextUtils.isEmpty(updatedDescription)) {
                Toast.makeText(AnimalsManagementActivity.this, "Por favor, ingrese todos los campos.", Toast.LENGTH_LONG).show();
            } else {
                int updatedAge = Integer.parseInt(updatedAgeString);

                animal.setName(updatedName);
                animal.setSpecies(updatedSpecies);
                animal.setEdad(updatedAge);
                animal.setDescription(updatedDescription);

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
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                animalRepository.deleteAnimal(animal.getId(), new Callback<Void>() {
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
                });
            }
        });

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
                    Log.d("AnimalsManagementActivity", "Obtained " + animalList.size() + " animals from the server");
                    animalAdapter.setAnimalList(animalList);
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

    private void addAnimal(Animal animal) {

        animalRepository.createAnimal(animal, new Callback<Animal>() {
            @Override
            public void onResponse(Call<Animal> call, Response<Animal> response) {
                if (response.isSuccessful()) {
                    Animal addedAnimal = response.body();

                    // Decodificar la imagen desde Base64 antes de establecerla en el animal agregado
                    String imageBase64 = addedAnimal.getImageString();
                    if (imageBase64 != null) {
                        byte[] decodedImage = Base64.decode(imageBase64, Base64.DEFAULT);
                        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);

                        // Establecer la imagen decodificada en el ImageView
                        imageAnimal.setImageBitmap(decodedBitmap);
                    }

                    animalList.add(addedAnimal);
                    animalAdapter.setAnimalList(animalList); // Cambia setAnimals a setAnimalList

                } else {
                    String errorMessage = "Error al agregar animal";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(AnimalsManagementActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Animal> call, Throwable t) {
                Toast.makeText(AnimalsManagementActivity.this, "Error al agregar animal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void startDrawableImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_drawable_image_picker, null);
        builder.setView(dialogView);
        GridView gridView = dialogView.findViewById(R.id.drawable_image_picker_gridview);
        List<Integer> imageResourceIds = new ArrayList<>();
        // Agrega los IDs de recursos de las imágenes de la carpeta drawable aquí
        imageResourceIds.add(R.drawable.gato1);
        imageResourceIds.add(R.drawable.gato2);
        imageResourceIds.add(R.drawable.gato3);
        imageResourceIds.add(R.drawable.perro1);
        imageResourceIds.add(R.drawable.perro2);
        imageResourceIds.add(R.drawable.perro3);
        DrawableImagePickerAdapter adapter = new DrawableImagePickerAdapter(this, imageResourceIds);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            int selectedImageResourceId = imageResourceIds.get(position);
            imageAnimal.setImageResource(selectedImageResourceId);
            drawableImagePickerDialog.dismiss();
        });
        builder.setTitle("Seleccione una imagen");
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        drawableImagePickerDialog = builder.create();
        drawableImagePickerDialog.show();
    }
}