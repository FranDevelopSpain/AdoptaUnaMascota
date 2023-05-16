package com.tfg.adoptaunamascota.views.home.animalview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private EditText nombreET, apellidosET, edadET, sexoET, movilET, domicilioET, emailET, comentariosET;
    private Button btnRegresar, registrarseBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_detail);

        // Obtener el Animal del Intent
        Animal selectedAnimal = (Animal) getIntent().getSerializableExtra("selected_animal");
        animalImage = findViewById(R.id.animal_image);
        animalName = findViewById(R.id.animal_name);
        animalSpecies = findViewById(R.id.animal_species);
        animalAge = findViewById(R.id.animal_age);
        animalCategory = findViewById(R.id.animal_category);
        animalType = findViewById(R.id.animal_type);
        animalDescription = findViewById(R.id.animal_description);
        animalName.setText("Nombre: " + selectedAnimal.getName());
        animalSpecies.setText("Especie: " + selectedAnimal.getSpecies());
        animalAge.setText("Edad: " + String.valueOf(selectedAnimal.getAge()));
        animalCategory.setText("Categoría: " + selectedAnimal.getCategoria());
        animalType.setText("Tipo: " + selectedAnimal.getType());
        animalDescription.setText("Descripción: " + selectedAnimal.getDescription());
        //Campos del formulario de registro
        nombreET = findViewById(R.id.nombreET);
        apellidosET = findViewById(R.id.apellidosET);
        edadET = findViewById(R.id.edadET);
        sexoET = findViewById(R.id.sexoET);
        movilET = findViewById(R.id.movilET);
        domicilioET = findViewById(R.id.domicilioET);
        emailET = findViewById(R.id.emailET);
        comentariosET = findViewById(R.id.comentariosET);
        btnRegresar = findViewById(R.id.regresarBTN);
        registrarseBTN = findViewById(R.id.registrarseBTN);

        byte[] decodedImage = Base64.decode(selectedAnimal.getImageBase64(), Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        animalImage.setImageBitmap(decodedBitmap);

        // Establecer el OnClickListener para el botón regresar
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                // Este método finaliza la actividad actual y vuelve a la anterior
            }
        });

        // Aquí puedes agregar el OnClickListener para el botón registrarse si lo necesitas
        // Por ejemplo, podrías querer recoger la información de los EditText y enviarla a algún lado
        registrarseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Recoge la información de los EditText y haz algo con ella
            }
        });
    }
}
