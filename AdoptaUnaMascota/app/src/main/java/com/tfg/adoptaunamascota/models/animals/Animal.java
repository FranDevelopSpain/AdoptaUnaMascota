package com.tfg.adoptaunamascota.models.animals;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Animal implements Serializable {
    @SerializedName("id")
    String id;
    @SerializedName("type")
    String type;
    @SerializedName("gender")
    String gender;
    @SerializedName("name")
    String name;
    @SerializedName("age")
    String age;
    @SerializedName("description")
    String description;
    @SerializedName("image")
    Integer image;

    public Animal(String id, String type, String gender, String name, String age, String description, int image) {
        this.id = id;
        this.type = type;
        this.gender = gender;
        this.name = name;
        this.age = age;
        this.description = description;
        this.image = image;
    }


    public Animal() {

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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

}
