package com.tfg.adoptaunamascota.models.animals;

public class Animal {
    String id;
    String type;
    String gender;
    String name;

    public Animal(String id, String type, String gender, String name) {
        this.id = id;
        this.type = type;
        this.gender = gender;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
