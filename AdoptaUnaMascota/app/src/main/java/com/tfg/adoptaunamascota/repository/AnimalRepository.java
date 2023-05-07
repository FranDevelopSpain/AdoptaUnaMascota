package com.tfg.adoptaunamascota.repository;

import android.content.Context;

import com.google.gson.Gson;
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
        Gson gson = new Gson();
        String animalJson = gson.toJson(animal);
        RequestBody animalRequestBody = RequestBody.create(animalJson, MediaType.parse("application/json"));

        RequestBody imageRequestBody = RequestBody.create(animal.getImageByteArray(), MediaType.parse("image/jpeg"));
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", "image.jpg", imageRequestBody);

        Call<Animal> call = apiService.createAnimal(animalRequestBody, imagePart);
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
