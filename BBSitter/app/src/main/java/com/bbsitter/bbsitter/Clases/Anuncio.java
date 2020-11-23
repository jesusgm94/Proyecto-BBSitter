package com.bbsitter.bbsitter.Clases;

import java.io.Serializable;
import java.util.Date;

public class Anuncio implements Serializable {

    private String titulo;
    private String descripcion;
    private String fechaPublicacion;
    private String tiempo;
    private String casa;


    public Anuncio() {}

    public Anuncio(String titulo, String descripcion, String fechaPublicacion, String tiempo, String casa) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
        this.tiempo = tiempo;
        this.casa = casa;
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
}

