package com.tfg.adoptaunamascota.views.home.animalview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.models.solicitud.Solicitud;
import com.tfg.adoptaunamascota.repository.SolicitudRepository;
import com.tfg.adoptaunamascota.views.home.HomeActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalDetailActivity extends AppCompatActivity {
    private ImageView animalImage;
    private TextView animalNombre;
    private TextView animalCategoria;
    private TextView animalSubCategoria;
    private TextView animalRaza;
    private TextView animalSexo;
    private TextView animalDescripcion;
    private SolicitudRepository solicitudRepository;


    private EditText nombreET, apellidosET, edadET, sexoET, movilET, domicilioET, emailET, comentariosET;
    private Button btnRegresar, registrarseBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_detail);
        String baseUrl = "http://10.0.2.2:8080";
        solicitudRepository = new SolicitudRepository(this, baseUrl);

        // Obtener el Animal del Intent
        Animal selectedAnimal = (Animal) getIntent().getSerializableExtra("selected_animal");
        animalImage = findViewById(R.id.animal_image);
        animalNombre = findViewById(R.id.animal_name);
        animalCategoria = findViewById(R.id.animal_category);
        animalSubCategoria = findViewById(R.id.animal_subCategory);
        animalRaza = findViewById(R.id.animal_raza);
        animalSexo = findViewById(R.id.animal_sex);
        animalDescripcion = findViewById(R.id.animal_description);
        animalNombre.setText("Nombre: " + selectedAnimal.getNombre());
        animalCategoria.setText("Categoría: " + selectedAnimal.getCategoria());
        animalSubCategoria.setText("SubCategoria: " + selectedAnimal.getSubcategoria());
        animalRaza.setText("Raza: " + selectedAnimal.getRaza());
        animalSexo.setText("Sexo: " + selectedAnimal.getSexo());
        animalDescripcion.setText("Descripción: " + selectedAnimal.getDescripcion());
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
        registrarseBTN.setOnClickListener(view -> {
            if (validateFields()) {
                registerSolicitud();
            }
        });
    }

    private boolean validateFields() {
        if (nombreET.getText().toString().isEmpty()
                || apellidosET.getText().toString().isEmpty()
                || edadET.getText().toString().isEmpty()
                || sexoET.getText().toString().isEmpty()
                || movilET.getText().toString().isEmpty()
                || domicilioET.getText().toString().isEmpty()
                || emailET.getText().toString().isEmpty()
                || comentariosET.getText().toString().isEmpty()) {
            Toast.makeText(AnimalDetailActivity.this, "Debe rellenar todos los campos",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        // Aquí puedes agregar más validaciones para cada campo si lo necesitas
        return true;
    }
    private void registerSolicitud() {

        Animal selectedAnimal = (Animal) getIntent().getSerializableExtra("selected_animal");
        Solicitud solicitudAdopcion = new Solicitud();
        solicitudAdopcion.setIdAnimal(selectedAnimal.getId());
        solicitudAdopcion.setNombre(nombreET.getText().toString());
        solicitudAdopcion.setApellidos(apellidosET.getText().toString());
        solicitudAdopcion.setEdad(Integer.parseInt(edadET.getText().toString()));
        solicitudAdopcion.setSexo(sexoET.getText().toString());
        solicitudAdopcion.setTelefono(movilET.getText().toString());
        solicitudAdopcion.setDomicilio(domicilioET.getText().toString());
        solicitudAdopcion.setEmail(emailET.getText().toString());
        solicitudAdopcion.setDetalleSolicitud(comentariosET.getText().toString());

        solicitudRepository.createSolicitud(solicitudAdopcion, new Callback<Solicitud>() {
            @Override
            public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AnimalDetailActivity.this, "Solicitud enviada correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AnimalDetailActivity.this, HomeActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(AnimalDetailActivity.this, "Error al enviar la solicitud",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Solicitud> call, Throwable t) {
                Toast.makeText(AnimalDetailActivity.this, "Error al comunicarse con el servidor",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}

