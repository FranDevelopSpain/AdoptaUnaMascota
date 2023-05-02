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
    @SerializedName("age")
    int age;
    @SerializedName("description")
    String description;
    @SerializedName("image")
    int image;
    @SerializedName("species")
    String species;

    public Animal(Long id, String type, String gender, String name, int age, String description, int image) {
        this.id = id;
        this.type = type;
        this.gender = gender;
        this.name = name;
        this.age = age;
        this.description = description;
        this.image = image;
        this.species = "";
    }


    public Animal(String name, String species, int age) {
        this.name = name;
        this.species = species;
        this.age = age;
    }

    public Animal() {

    }

    public Animal(Long id, String cat, String gender, String name, int age, String this_is_a_cat_description, int image, String s) {
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getImageResource() {
        return image;
    }

    public String getSize() {
        return "";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

