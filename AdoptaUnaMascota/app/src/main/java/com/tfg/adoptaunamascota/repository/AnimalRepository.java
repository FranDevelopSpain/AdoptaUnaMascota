package com.tfg.adoptaunamascota.repository;

import android.content.Context;
import android.content.SharedPreferences;

public class AnimalRepository {
    private static final  String ANIMALS = "animals";
    private SharedPreferences sharedPreferences;

    public AnimalRepository(Context context){
        sharedPreferences = context.getSharedPreferences(ANIMALS, Context.MODE_PRIVATE);
    }

}
