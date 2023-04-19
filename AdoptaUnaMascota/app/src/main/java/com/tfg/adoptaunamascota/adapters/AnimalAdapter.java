package com.tfg.adoptaunamascota.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tfg.adoptaunamascota.R;
import com.tfg.adoptaunamascota.models.animals.Animal;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {

    private List<Animal> animalList;

    public AnimalAdapter(List<Animal> animalList) {
        this.animalList = animalList;
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
        holder.animalGender.setText(animal.getGender());
        holder.animalDescription.setText(animal.getDescription());
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    public static class AnimalViewHolder extends RecyclerView.ViewHolder {
        private ImageView animalImage;
        private TextView animalName;
        private TextView animalGender;
        private TextView animalDescription;

        public AnimalViewHolder(@NonNull View itemView) {
            super(itemView);
            animalImage = itemView.findViewById(R.id.animal_image);
            animalName = itemView.findViewById(R.id.animal_name);
            animalGender = itemView.findViewById(R.id.animal_gender);
            animalDescription = itemView.findViewById(R.id.animal_description);
        }
    }
}
