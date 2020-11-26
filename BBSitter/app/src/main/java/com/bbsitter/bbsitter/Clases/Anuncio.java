package com.bbsitter.bbsitter.Clases;

import java.io.Serializable;

public class Anuncio implements Serializable {

    private String titulo;
    private String descripcion;
    private String fechaPublicacion;
    private String tiempo;
    private String casa;
    private String img;
    private String nombre;
    private String direccion;
    private String uid;


    public Anuncio() {}

    public Anuncio(String titulo, String descripcion, String fechaPublicacion, String tiempo, String casa, String img, String nombre, String direccion,String uid) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.tiempo = tiempo;
        this.casa = casa;
        this.img = img;
        this.nombre = nombre;
        this.direccion = direccion;
        this.uid = uid;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getCasa() {
        return casa;
    }

    public void setCasa(String casa) {
        this.casa = casa;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

