package com.tfg.adoptaunamascota.models;

public class User {
    private String mail;
    private String password;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String mail, String password, String s, String toString) {
        this.mail = mail;
        this.password = password;
    }
}
