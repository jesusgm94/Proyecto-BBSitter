package com.bbsitter.bbsitter.Clases;

import java.io.Serializable;

public class Familia implements Serializable {

    private String nombre;
    private String descripcion;
    private String direccion;
    private double longitudLoc;
    private double latitudLoc;
    private String uid;

    public Familia(){}

    public Familia(String nombre, String descripcion, String direccion, double longitudLoc, double latitudLoc, String uid) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.longitudLoc = longitudLoc;
        this.latitudLoc = latitudLoc;
        this.uid = uid;
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

    public double getLongitudLoc() {return longitudLoc;}

    public void setLongitudLoc(double longitudLoc) {this.longitudLoc = longitudLoc;}

    public double getLatitudLoc() {return latitudLoc;}

    public void setLatitudLoc(double latitudLoc) {this.latitudLoc = latitudLoc;}

    public String getUid() {return uid;}

    public void setUid(String uid) {this.uid = uid;}
}
