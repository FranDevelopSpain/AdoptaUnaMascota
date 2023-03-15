package com.tfg.adoptaunamascota.model;

public class Login {
    private String mail;
    private String password;

    public Login() {
    }

    public Login(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public boolean setMail(String mail) {
        this.mail = mail;
        return false;
    }

    public String getPassword() {
        return password;
    }

    public boolean setPassword(String password) {
        this.password = password;
        return false;
    }
}
