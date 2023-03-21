package com.example.escobar_silva;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.Serializable;

public class Producto implements Serializable {

    public String Nombre;
    public Double Precio;
    public String Url;
    public Producto(String nombre, Double precio, String url) {
        this.Nombre = nombre;
        this.Precio = precio;
        this.Url = url;
    }
    public Producto(){

    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

}