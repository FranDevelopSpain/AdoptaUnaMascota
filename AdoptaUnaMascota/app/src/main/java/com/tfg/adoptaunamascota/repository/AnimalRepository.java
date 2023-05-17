package com.tfg.adoptaunamascota.repository;

import android.content.Context;

import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimalRepository {
    private ApiService apiService;

    public AnimalRepository(Context context, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public void getAnimals(Callback<List<Animal>> callback) {
        Call<List<Animal>> call = apiService.getAnimals();
        call.enqueue(callback);
    }

    public void createAnimal(Animal animal, Callback<Animal> callback) {
        Call<Animal> call = apiService.createAnimal(animal);
        call.enqueue(callback);
    }

    public void updateAnimal(long id, Animal animal, Callback<Animal> callback) {
        Call<Animal> call = apiService.updateAnimal(id, animal);
        call.enqueue(callback);
    }

    public void deleteAnimal(long id, Callback<Void> callback) {
        Call<Void> call = apiService.deleteAnimal(id);
        call.enqueue(callback);
    }
}
