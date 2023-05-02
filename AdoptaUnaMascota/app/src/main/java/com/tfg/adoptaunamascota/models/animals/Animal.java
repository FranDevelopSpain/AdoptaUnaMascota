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
        this.id = this.id;
        this.type = this.type;
        this.gender = this.gender;
        this.name = this.name;
        this.age = this.age;
        this.description = this.description;
        this.image = this.image;
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

    public int getName() {
        return 0;
    }

    public int getGender() {
        return 0;
    }

    public int getImageResource() {
        return 0;
    }
}
