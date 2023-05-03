package com.tfg.adoptaunamascota.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.views.home.crudAdmin.AnimalsManagementActivity;
import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private List<Animal> animalList;
    private Context context;
    private AnimalsManagementActivity animalsManagementActivity;
    private int selectedPosition = -1;

    public AnimalAdapter(List<Animal> animalList, Context context, AnimalsManagementActivity animalsManagementActivity) {
        this.animalList = animalList;
        this.context = context;
        this.animalsManagementActivity = animalsManagementActivity;
    }

    public void setAnimalList(List<Animal> animalList) {
        this.animalList = animalList;
    }

    public Animal getSelectedAnimal() {
        if (selectedPosition >= 0 && selectedPosition < animalList.size()) {
            return animalList.get(selectedPosition);
        }
        return null;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.animal_item, parent, false);
        return new AnimalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animalList.get(position);
        holder.animalName.setText(animal.getName());
        holder.animalSpecies.setText(animal.getSpecies());
        holder.animalAge.setText(String.valueOf(animal.getEdadEnMeses()) + " meses");
        holder.animalImage.setImageResource(animal.getImageResource());

        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(Color.parseColor("#EEEEEE"));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    class AnimalViewHolder extends RecyclerView.ViewHolder {
        private ImageView animalImage;
        private TextView animalName;
        private TextView animalSpecies;
        private TextView animalAge;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            animalImage = itemView.findViewById(R.id.animal_image);
            animalName = itemView.findViewById(R.id.animal_name);
            animalSpecies = itemView.findViewById(R.id.animal_species);
            animalAge = itemView.findViewById(R.id.animal_age);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    setSelectedPosition(position);
                    animalsManagementActivity.setSelectedAnimal(animalList.get(position));
                }
            });
        }
    }
}
