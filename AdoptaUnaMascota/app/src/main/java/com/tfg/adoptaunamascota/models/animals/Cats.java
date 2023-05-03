package com.tfg.adoptaunamascota.models.animals;

public class Cats extends Animal {

    public Cats(Long id, String gender, String name, int age, String species, String category, String subcategory, String breed, String image, String description) {
        super(id, "Cat", gender, name, age, description, image, species, category, subcategory, breed);
    }

    public Cats(String name, String species, int age) {
        super(name, species, age);
        setType("Cat");
        setDescription("This is a cat description");
    }

    public Cats(String name, String category, String breed, int age, String description) {
        super(name, category, breed, age, description);
        setType("Cat");
    }

    public Cats() {
        super();
        setType("Cat");
        setDescription("This is a cat description");
    }

    public String getSize() {
        return "";
    }

    @Override
    public int getEdadEnMeses() {
        return getEdad() * 12;
    }
}