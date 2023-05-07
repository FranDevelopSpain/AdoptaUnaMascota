package com.tfg.adoptaunamascota.models.animals;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Animal implements Serializable {
    @SerializedName("id")
    long id;
    @SerializedName("categoria")
    String categoria;
    @SerializedName("description")
    String description;
    @SerializedName("name")
    String name;
    @SerializedName("raza")
    String raza;
    @SerializedName("gender")
    String gender;
    @SerializedName("image")
    String image;
    @SerializedName("image_byte_array")
    byte[] imageByteArray;
    @SerializedName("species")
    String species;
    @SerializedName("type")
    String type;
    @SerializedName("edad")
    int edad;

    public Animal(Long id, String type, String gender, String name, int edad, String description, String species, String categoria, String raza, byte[] imageByteArray) {
        this.id = id;
        this.type = type;
        this.gender = gender;
        this.name = name;
        this.edad = edad;
        this.description = description;
        this.species = species;
        this.categoria = categoria;
        this.raza = raza;
        this.imageByteArray = imageByteArray;
    }

    public Animal(String name, String species, int edad, byte[] imageByteArray) {
        this.name = name;
        this.species = species;
        this.edad = edad;
        this.imageByteArray = imageByteArray;

    }

    public Animal(String name, String category, String breed, int age, String description, String animalType, byte[] imageByteArray) {
        this.name = name;
        this.categoria = category;
        this.raza = breed;
        this.edad = age;
        this.description = description;
        this.imageByteArray = imageByteArray;

    }

    public Animal(Long id, String cat, String gender, String name, int age, String description, String image, String species, String category, String subcategory, String breed) {
    }

    public Animal(String name, String species, int age) {
    }

    public Animal(String name, String category, String breed, int age, String description) {
    }

    public byte[] getImageByteArray() {
        return imageByteArray;
    }

    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }


    public Animal() {
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
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
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
        return edad * 12;
    }

    public int getAge() {
        return edad;
    }
}
