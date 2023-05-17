package com.tfg.adoptaunamascota.models.animals;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Animal implements Serializable {
    @SerializedName("id")
    long id;
    @SerializedName("nombre")
    String nombre;
    @SerializedName("categoria")
    String categoria;
    @SerializedName("subcategoria")
    String subcategoria; //tamaño/meses
    @SerializedName("raza")
    String raza;
    @SerializedName("sexo")
    private String sexo;
    @SerializedName("descripcion")
    String descripcion;
    @SerializedName("image")
    String imageBase64;
    @SerializedName("tamaño")
    private String tamaño;  // Para perros
    @SerializedName("edad")
    private Integer edad;  // Para gatos

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Animal(String nombre, String categoria, String subcategoria, String raza, String sexo, String descripcion, String imageBase64) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.sexo = sexo;
        this.descripcion = descripcion;
        this.raza = raza;
        this.imageBase64 = imageBase64;
    }
    public Animal(String nombre, String categoria, String subcategoria, String raza, String sexo, String descripcion, String imageBase64,String tamaño, Integer edad) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.sexo = sexo;
        this.descripcion = descripcion;
        this.raza = raza;
        this.imageBase64 = imageBase64;
        this.tamaño = tamaño;
        this.edad = edad;
    }

    public Animal() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(String subcategoria) {
        this.subcategoria = subcategoria;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}

