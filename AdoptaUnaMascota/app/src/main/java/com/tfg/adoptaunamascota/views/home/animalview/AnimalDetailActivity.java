package com.tfg.adoptaunamascota.views.home.animalview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.models.animals.Animal;

public class AnimalDetailActivity extends AppCompatActivity {
    private ImageView animalImage;
    private TextView animalName;
    private TextView animalSpecies;
    private TextView animalAge;
    private TextView animalCategory;
    private TextView animalType;
    private TextView animalDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_detail);

        animalImage = findViewById(R.id.animal_image);
        animalName = findViewById(R.id.animal_name);
        animalSpecies = findViewById(R.id.animal_species);
        animalAge = findViewById(R.id.animal_age);
        animalCategory = findViewById(R.id.animal_category);
        animalType = findViewById(R.id.animal_type);
        animalDescription = findViewById(R.id.animal_description);

        Animal selectedAnimal = (Animal) getIntent().getSerializableExtra("selected_animal");

        // Aquí es donde se establecen los datos del animal en las vistas
        byte[] decodedImage = Base64.decode(selectedAnimal.getImageBase64(), Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        animalImage.setImageBitmap(decodedBitmap);
    }
}