package com.tfg.adoptaunamascota.models.animals;

public class Cats extends Animal {
    public Cats(String id, String type, String gender, int image) {
        super(id, type, gender, "Cat", "Adult", "This is a cat description", image);
    }
    public Cats() {
        super();
    }
}
