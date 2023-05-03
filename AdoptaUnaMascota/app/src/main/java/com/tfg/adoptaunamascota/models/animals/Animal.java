package com.tfg.adoptaunamascota.models.animals;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Animal implements Serializable {
    @SerializedName("id")
    long id;
    @SerializedName("type")
    String type;
    @SerializedName("gender")
    String gender;
    @SerializedName("name")
    String name;
    @SerializedName("edad")
    int edad;
    @SerializedName("description")
    String description;
    @SerializedName("image")
    String image;

    @SerializedName("species")
    String species;
    @SerializedName("categoria")
    String categoria;
    @SerializedName("subcategoria")
    String subcategoria;
    @SerializedName("raza")
    String raza;

    public Animal(Long id, String type, String gender, String name, int edad, String description, String image, String species, String categoria, String subcategoria, String raza) {
        this.id = id;
        this.type = type;
        this.gender = gender;
        this.name = name;
        this.edad = edad;
        this.description = description;
        this.image = image;
        this.species = species;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.raza = raza;
    }

    public Animal(String name, String species, int edad) {
        this.name = name;
        this.species = species;
        this.edad = edad;
    }

    public Animal(String name, String category, String breed, int age, String description) {
        this.name = name;
        this.categoria = category;
        this.raza = breed;
        this.edad = age;
        this.description = description;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public void setImage(String image) {
        this.image = image;
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

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdadEnMeses() {
        return edad * 12;
    }

    public int getAge() {
        return edad;
    }
}
