package com.tfg.adoptaunamascota.models.animals;

public class Cats extends Animal {

    public Cats(Long id, String name, String gender, int age, int image) {
        super(id, "Cat", gender, name, age, "This is a cat description", image, "");
    }

    public Cats() {
        super();
    }

    @Override
    public String getSize() {
        return ""; // Las clases de gatos no tienen un atributo de tamaño, por lo que simplemente devolvemos una cadena vacía
    }
}
