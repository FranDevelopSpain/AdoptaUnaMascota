package com.tfg.adoptaunamascota.models.animals;

public class Dogs extends Animal {
    private String size;

    public Dogs(Long id, String name, String gender, String size, int age, int image) {
        super(id, "Dog", gender, name, age, "This is a dog description", image);
        this.size = size;
    }

    @Override
    public String getSize() {
        return size;
    }
}