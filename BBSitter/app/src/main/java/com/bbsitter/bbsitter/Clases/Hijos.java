package com.bbsitter.bbsitter.Clases;

import java.io.Serializable;

public class Hijos implements Serializable {

    private String nombre;
    private String edad;
    private String otrosDatos;


    public Hijos() {
    }

    public Hijos( String nombre, String edad, String otrosDatos) {
        this.nombre = nombre;
        this.edad = edad;
        this.otrosDatos = otrosDatos;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdadH(String edad) {
        this.edad = edad;
    }

    public String getOtrosDatos() {
        return otrosDatos;
    }

    public void setOtrosDatos(String otrosDatos) {
        this.otrosDatos = otrosDatos;
    }
}
