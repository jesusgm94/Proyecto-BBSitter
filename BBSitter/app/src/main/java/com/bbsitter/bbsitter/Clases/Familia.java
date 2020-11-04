package com.bbsitter.bbsitter.Clases;

import java.io.Serializable;

public class Familia implements Serializable {

    private String nombre;
    private String descripcion;
    private String direccion;
    private String uid;

    public Familia(){}

    public Familia(String nombre, String descripcion, String direccion, String uid) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
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

    public String getUid() {return uid;}

    public void setUid(String uid) {this.uid = uid;}
}
