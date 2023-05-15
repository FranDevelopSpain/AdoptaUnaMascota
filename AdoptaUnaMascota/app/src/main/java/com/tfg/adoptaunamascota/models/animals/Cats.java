package com.tfg.adoptaunamascota.models.animals;

public class Cats extends Animal {
    public Cats(String name, String category, String raza, int i, String description, String animalType, String imageBase64) {
        super(name, category, raza, i, description, animalType, imageBase64);
    }

    public Cats(String name, String species, String raza, int age, String imageBase64) {
        super(name, species, raza, age, imageBase64);
    }

    public String getSize() {
        return "";
    }

    @Override
    public int getEdadEnMeses() {
        return getAge() * 12;
    }
}
