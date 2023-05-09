package com.tfg.adoptaunamascota.models.animals;

public class Dogs extends Animal {

    private String size;

    public Dogs(Long id, String gender, String name, String size, int age, String species, String category, String subcategory, String breed, byte[] image, String description) {
        super(id, "Dog", gender, name, age, description, species, category, breed, image);
        setType("Dog");
        this.size = size;
    }

    public Dogs(String name, String species, int age) {
        super(name, species, age);
        setType("Dog");
        setDescription("This is a dog description");
    }

    public Dogs(String name, String category, String breed, int age, String description) {
        super(name, category, breed, age, description);
        setType("Dog");
    }

    public Dogs() {
        super();
        setType("Dog");
        setDescription("This is a dog description");
    }

    public String getSize() {
        return size;
    }

    @Override
    public int getEdadEnMeses() {
        return getEdad() * 12;
    }
}