package com.tfg.adoptaunamascota.models.animals;

public class Dogs extends Animal {
    public Dogs(String id, String type, String gender, int image) {
        super(id, type, gender, "Dog", "Adult", "This is a dog description", image);
    }
}
