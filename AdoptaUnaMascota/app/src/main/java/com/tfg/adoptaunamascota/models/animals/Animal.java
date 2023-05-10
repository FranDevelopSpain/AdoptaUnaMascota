package com.tfg.adoptaunamascota.models.animals;

import android.util.Base64;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Animal implements Serializable {
    @SerializedName("id")
    long id;
    @SerializedName("categoria")
    String categoria;
    @SerializedName("descripcion")
    String descripcion;
    @SerializedName("name")
    String name;
    @SerializedName("raza")
    String raza;
    @SerializedName("image")
    String image;  // Ahora es String

    @SerializedName("species")
    String species;
    @SerializedName("type")
    String type;
    @SerializedName("age")
    int age;
    @SerializedName("gender")
    String gender;

    // Actualiza los constructores para aceptar la imagen como String
    public Animal(Long id, String type, String name, String s, int age, String descripcion, String species, String categoria, String raza, String imageBase64) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.age = age;
        this.descripcion = descripcion;
        this.species = species;
        this.categoria = categoria;
        this.raza = raza;
        this.image = imageBase64;
    }

    public Animal(String name, String species, String raza, int age, String imageBase64, String animalType, byte[] imageBytes) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.image = imageBase64;
    }

    public Animal(String name, String category, String breed, int age, String descripcion, String animalType, String imageBase64) {
        this.name = name;
        this.categoria = category;
        this.raza = breed;
        this.age = age;
        this.descripcion = descripcion;
        this.image = imageBase64;
    }


    public Animal(Long id, String cat, String gender, String name, int age, String descripcion,byte[] image,
                  String species, String category, String subcategory, String breed) {
    }

    public Animal(String name, String species, int age) {
    }

    public Animal(String name, String category, String breed, int age, String description) {
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

    public byte[] getImage() {
        return Base64.decode(image, Base64.DEFAULT);
    }

    public void setImage(String imageBase64) {
        this.image = imageBase64;
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
    }

    public byte[] getImageString() {
        return Base64.decode(image, Base64.DEFAULT);
    }
}
