package com.tfg.adoptaunamascota.models.animals;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Animal implements Serializable {
    @SerializedName("id")
    long id;
    @SerializedName("name")
    String name;
    @SerializedName("species")
    String species;
    @SerializedName("age")
    int age;
    @SerializedName("categoria")
    String categoria;
    @SerializedName("type")
    String type;
    @SerializedName("descripcion")
    String descripcion;
    @SerializedName("raza")
    String raza;
    @SerializedName("image")
    String imageBase64;
    @SerializedName("gender")
    String gender;


    public Animal(String name, String category, String raza, int i, String description, String animalType, String imageBase64) {
        this.name = name;
        this.categoria = category;
        this.raza = raza;
        this.age = i;
        this.descripcion = description;
        this.type = animalType;
        this.imageBase64 = imageBase64;
    }

    public Animal(String name, String species, String raza, int age, String imageBase64) {
        this.name = name;
        this.species = species;
        this.raza = raza;
        this.age = age;
        this.imageBase64 = imageBase64;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEdad() {
        return age;
    }

    public void setEdad(int age) {
        this.age = age;
    }

    public String getDescription() {
        return descripcion;
    }

    public void setDescription(String description) {
        this.descripcion = description;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getEdadEnMeses() {
        return age * 12;
    }

    public int getAge() {
        return age;
    }

    public void setImageString(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public String getImageString() {
        return imageBase64;
    }
}

