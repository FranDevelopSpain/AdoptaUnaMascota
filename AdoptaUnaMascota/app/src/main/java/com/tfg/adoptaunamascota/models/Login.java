package com.tfg.adoptaunamascota.models;

public class Login {
    private String mail;
    private String password;

    public Login(String email, String password) {
        this.mail = email;
        this.password = password;
    }
    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }
}