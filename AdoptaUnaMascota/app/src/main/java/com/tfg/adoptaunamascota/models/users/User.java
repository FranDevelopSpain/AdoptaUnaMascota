package com.tfg.adoptaunamascota.models.users;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("id")
    private Long id;
    @SerializedName("name")
    private String name;
    @SerializedName("surname")
    private String surname;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("isAdmin")
    private boolean isAdmin;

    public User(String name, String surname, String mail, String password) {
        this.id=id;
        this.email = mail;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.isAdmin = false;
    }

    public User(String name, String surname, String mail) {
        this.name = name;
        this.surname = surname;
        this.email = mail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String setSurname(String surname){
        this.surname=surname;
        return surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}