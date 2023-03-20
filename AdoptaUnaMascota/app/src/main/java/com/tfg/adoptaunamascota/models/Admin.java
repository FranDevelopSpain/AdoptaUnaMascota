package com.tfg.adoptaunamascota.models;

public class Admin {

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

    public Admin(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }
}
