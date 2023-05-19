package com.tfg.adoptaunamascota.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.views.home.crudAdmin.AnimalsManagementActivity;

import java.util.ArrayList;
import java.util.List;


/*Este código es un adaptador personalizado para un RecyclerView en Android */
public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> implements Filterable {

    private List<Animal> animalList;
    private Context context;
    private AnimalsManagementActivity animalsManagementActivity;
    private int selectedPosition = -1;
    private OnAnimalClick onAnimalClickListener;
    private List<Animal> animalListFiltered;
    private String lastFilter = "";




    // Recibe la lista de animales, el contexto y la actividad que maneja los animales.
    public AnimalAdapter(List<Animal> animalList, Context context, OnAnimalClick onAnimalClickListener) {
        this.animalList = animalList;
        this.context = context;
        this.animalListFiltered = animalList;
        this.onAnimalClickListener = onAnimalClickListener;
    }

    //Este método se utiliza para actualizar la lista de animales en el adaptador
    // y notificar al RecyclerView que los datos han cambiado
    public void setAnimalList(List<Animal> animalList) {
        this.animalList = animalList;
        this.animalListFiltered = animalList;
        notifyDataSetChanged();
    }
    //Este método devuelve el animal seleccionado actualmente en la lista, si hay alguno.
    public Animal getSelectedAnimal() {
        if (selectedPosition >= 0 && selectedPosition < animalListFiltered.size()) {
            return animalListFiltered.get(selectedPosition);
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
        Animal animal = animalListFiltered.get(position);
        if (animal.getImageBase64() != null) {
            byte[] decodedImage = Base64.decode(animal.getImageBase64(), Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
            holder.animalImage.setImageBitmap(decodedBitmap);
        } else {
            // Aquí puedes poner una imagen por defecto si quieres, en lugar de dejarla en blanco.
            holder.animalImage.setImageResource(R.drawable.error_image);
        }
        holder.animalNombre.setText("Nombre: " + animal.getNombre());
        holder.animalCategoria.setText("Categoria: " + animal.getCategoria());
        holder.animalSubCategoria.setText("Edad: " + animal.getSubcategoria());
        holder.animalRaza.setText("Raza: " + animal.getRaza());
        holder.animalSexo.setText("Sexo: " + animal.getSexo());
        holder.animalTamano.setText("Tamaño: " + animal.getTamaño());
        holder.animalEdad.setText("Edad: " + animal.getEdad());
        holder.animalDescription.setText("Descripcion: " + animal.getDescripcion());

        // Agrega OnClickListener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSelectedPosition(position);
                Animal selectedAnimal = animalListFiltered.get(position);
                onAnimalClickListener.onAnimalClick(position, selectedAnimal);
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
        return animalListFiltered.size();
    }
    /*
    Este método se utiliza para establecer la posición del elemento seleccionado en la lista
    y notificar al RecyclerView que los datos han cambiado
     */
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<Animal> filteredList = new ArrayList<>();

                String filterPattern = constraint == null ? "" : constraint.toString().toLowerCase().trim();

                if (filterPattern.equals(lastFilter)) {
                    // Si el filtro actual es el mismo que el último, "desactiva" el filtro
                    results.values = animalList;
                    results.count = animalList.size();
                    lastFilter = "";  // Restablece el último filtro
                } else {
                    lastFilter = filterPattern;  // Actualiza el último filtro

                    for (Animal animal : animalList) {
                        if (animal.getCategoria().trim().equalsIgnoreCase("perro")) {
                            if ((filterPattern.equals("perros pequeno") && animal.getTamaño().trim().equalsIgnoreCase("pequeno")) ||
                                    (filterPattern.equals("perros medianos") && animal.getTamaño().trim().equalsIgnoreCase("mediano")) ||
                                    (filterPattern.equals("perros grandes") && animal.getTamaño().trim().equalsIgnoreCase("grande"))) {
                                filteredList.add(animal);
                            }
                        } else if (animal.getCategoria().trim().equalsIgnoreCase("gato")) {
                            if ((filterPattern.equals("menos de 6 meses") && animal.getEdad() < 6) ||
                                    (filterPattern.equals("más de 6 meses") && animal.getEdad() >= 6)) {
                                filteredList.add(animal);
                            }
                        }
                    }

                    results.values = filteredList;
                    results.count = filteredList.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                animalListFiltered = (ArrayList<Animal>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    class AnimalViewHolder extends RecyclerView.ViewHolder {
        private ImageView animalImage;
        private TextView animalNombre;
        private TextView animalCategoria;
        private TextView animalSubCategoria;
        private TextView animalSexo;
        private TextView animalRaza;
        private TextView animalTamano;
        private TextView animalEdad;
        private TextView animalDescription;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            animalImage = itemView.findViewById(R.id.animal_image);
            animalNombre = itemView.findViewById(R.id.animal_name);
            animalCategoria = itemView.findViewById(R.id.animal_category);
            animalSubCategoria = itemView.findViewById(R.id.animal_subCategory);
            animalSexo = itemView.findViewById(R.id.animal_sex);
            animalRaza = itemView.findViewById(R.id.animal_raza);
            animalTamano = itemView.findViewById(R.id.animal_tamano);
            animalEdad = itemView.findViewById(R.id.animal_edad);
            animalDescription = itemView.findViewById(R.id.animal_description);
        }
    }
    public void updateAnimals(List<Animal> newAnimals) {
        setAnimalList(newAnimals);
        animalListFiltered = newAnimals;
    }
}
