package com.tfg.adoptaunamascota.repository;

import android.content.Context;

import com.tfg.adoptaunamascota.models.animals.Animal;
import com.tfg.adoptaunamascota.service.ApiService;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), animal.getName() != null ? animal.getName() : "");
        RequestBody category = RequestBody.create(MediaType.parse("text/plain"), animal.getCategoria() != null ? animal.getCategoria() : "");
        RequestBody raza = RequestBody.create(MediaType.parse("text/plain"), animal.getSpecies() != null ? animal.getSpecies() : "");
        RequestBody age = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(animal.getEdad()));
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), animal.getDescription() != null ? animal.getDescription() : "");
        RequestBody animalType = RequestBody.create(MediaType.parse("text/plain"), animal.getType() != null ? animal.getType() : "");

        // Crear MultipartBody.Part a partir de los bytes de la imagen
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), animal.getImage() != null ? animal.getImage() : new byte[0]);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", "animal_image.png", requestBody);

        Call<Animal> call = apiService.createAnimal(name, category, raza, age, description, animalType, image);
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
