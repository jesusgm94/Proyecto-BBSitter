package com.bbsitter.bbsitter.Clases;

import java.io.Serializable;

public class Familia implements Serializable {

    private String nombre;
    private String descripcion;
    private String direccion;
    private double longitud;
    private double latitud;
    private String uid;
    private String email;

    public Familia(){}

    public Familia(String nombre, String descripcion, String direccion, double longitud, double latitud, String uid,String email) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.longitud = longitud;
        this.latitud = latitud;
        this.uid = uid;
        this.email = email;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLongitud() {return longitud;}

    public void setLongitud(double longitud) {this.longitud = longitud;}

    public double getLatitud() {return latitud;}

    public void setLatitud(double latitud) {this.latitud = latitud;}

    public String getUid() {return uid;}

    public void setUid(String uid) {this.uid = uid;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
