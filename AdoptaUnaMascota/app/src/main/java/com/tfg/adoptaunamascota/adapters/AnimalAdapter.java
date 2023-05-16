package com.tfg.adoptaunamascota.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.views.home.animalview.AnimalDetailActivity;
import com.tfg.adoptaunamascota.views.home.crudAdmin.AnimalsManagementActivity;

import java.util.List;


/*Este código es un adaptador personalizado para un RecyclerView en Android */
public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private List<Animal> animalList;
    private Context context;
    private AnimalsManagementActivity animalsManagementActivity;
    private int selectedPosition = -1;

    // Recibe la lista de animales, el contexto y la actividad que maneja los animales.
    public AnimalAdapter(List<Animal> animalList, Context context, AnimalsManagementActivity animalsManagementActivity) {
        this.animalList = animalList;
        this.context = context;
        this.animalsManagementActivity = animalsManagementActivity;
    }

    //Este método se utiliza para actualizar la lista de animales en el adaptador
    // y notificar al RecyclerView que los datos han cambiado
    public void setAnimalList(List<Animal> animalList) {
        this.animalList = animalList;
        Log.d("Prueba", "Entramos¿: ");
        notifyDataSetChanged();
    }
    //Este método devuelve el animal seleccionado actualmente en la lista, si hay alguno.
    public Animal getSelectedAnimal() {
        if (selectedPosition >= 0 && selectedPosition < animalList.size()) {
            return animalList.get(selectedPosition);
        }
        return null;
    }

    public List<Animal> getAnimals() {
        return animalList;
    }

    //Este método se encarga de crear las vistas para los elementos del RecyclerView.
    // Aquí se infla el diseño de cada elemento de la lista.
    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.animal_item, parent, false);
        return new AnimalViewHolder(itemView);
    }

    /*Este método se encarga de rellenar las vistas con los datos de cada animal.
    Aquí es donde se establecen los textos e imágenes para cada elemento de la lista.
    También cambia el color de fondo del elemento si este es el seleccionado.
    */
    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animalList.get(position);
        byte[] decodedImage = Base64.decode(animal.getImageBase64(), Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        holder.animalImage.setImageBitmap(decodedBitmap);
        holder.animalName.setText("Nombre: " + animal.getName());
        holder.animalSpecies.setText("Especie: " + animal.getSpecies());
        holder.animalAge.setText("Edad: " + String.valueOf(animal.getAge()) + " meses");
        holder.animalCategory.setText("Categoria: " + animal.getCategoria());
        holder.animalType.setText("Tipo: " + animal.getType());
        holder.animalDescription.setText("Descripcion: " + animal.getDescription());

        // Agrega OnClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedPosition(position);
                Animal selectedAnimal = animalList.get(position);
                animalsManagementActivity.setSelectedAnimal(selectedAnimal);

                // Iniciar la nueva actividad
                Intent intent = new Intent(context, AnimalDetailActivity.class);
                // Pasar el objeto Animal seleccionado
                intent.putExtra("selected_animal", selectedAnimal);
                context.startActivity(intent);
            }
        });

        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(Color.parseColor("#EEEEEE"));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    //Este método devuelve la cantidad de elementos en la lista,
    // que RecyclerView utiliza para determinar cuántos elementos necesita mostrar.
    @Override
    public int getItemCount() {
        return animalList.size();
    }
    /*
    Este método se utiliza para establecer la posición del elemento seleccionado en la lista
    y notificar al RecyclerView que los datos han cambiado
     */
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    /*
    Esta es una subclase de ViewHolder.
    Un ViewHolder representa un único elemento de la lista en la pantalla.
    Aquí se mantiene una referencia a todas las vistas dentro de un elemento de la lista
    y también se establece un OnClickListener para manejar cuando se selecciona un elemento.
     */

    class AnimalViewHolder extends RecyclerView.ViewHolder {
        private ImageView animalImage;
        private TextView animalName;
        private TextView animalSpecies;
        private TextView animalAge;
        private TextView animalCategory;
        private TextView animalType;
        private TextView animalDescription;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            animalImage = itemView.findViewById(R.id.animal_image);
            animalName = itemView.findViewById(R.id.animal_name);
            animalSpecies = itemView.findViewById(R.id.animal_species);
            animalAge = itemView.findViewById(R.id.animal_age);
            animalCategory = itemView.findViewById(R.id.animal_category);
            animalType = itemView.findViewById(R.id.animal_type);
            animalDescription = itemView.findViewById(R.id.animal_description);
        }
    }
}
