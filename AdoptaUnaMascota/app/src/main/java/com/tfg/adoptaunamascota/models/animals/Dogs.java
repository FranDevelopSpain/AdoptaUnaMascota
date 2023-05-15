package com.tfg.adoptaunamascota.models.animals;

public class Dogs extends Animal {
    public Dogs(String name, String category, String raza, int i, String description, String animalType, String imageBase64, String gender) {
        super(name, category, raza, i, description, animalType, imageBase64,gender);
    }

    public Dogs(String name, String species, String raza, int age, String imageBase64) {
        super(name, species, raza, age, imageBase64);
    }

    @Override
    public int getEdadEnMeses() {
        return getEdad() * 12;
    }
}