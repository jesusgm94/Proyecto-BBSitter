package com.bbsitter.bbsitter.Clases;

import java.io.Serializable;

public class Hijos implements Serializable {

    private String imgHijos;
    private String nombreHijos;
    private String edadHijos;
    private String otrosDatos;

    public Hijos() {
    }

    public Hijos(String imgHijos, String nombreHijos, String edadHijos, String otrosDatos) {
        this.imgHijos = imgHijos;
        this.nombreHijos = nombreHijos;
        this.edadHijos = edadHijos;
        this.otrosDatos = otrosDatos;
    }

    public String getImgHijos() {
        return imgHijos;
    }

    public void setImgHijos(String imgHijos) {
        this.imgHijos = imgHijos;
    }

    public String getNombreHijos() {
        return nombreHijos;
    }

    public void setNombreHijos(String nombreHijos) {
        this.nombreHijos = nombreHijos;
    }

    public String getEdadHijos() {
        return edadHijos;
    }

    public void setEdadHijos(String edadHijos) {
        this.edadHijos = edadHijos;
    }

    public String getOtrosDatos() {
        return otrosDatos;
    }

    public void setOtrosDatos(String otrosDatos) {
        this.otrosDatos = otrosDatos;
    }
}
