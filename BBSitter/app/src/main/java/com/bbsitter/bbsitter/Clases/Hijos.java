package com.bbsitter.bbsitter.Clases;

import java.io.Serializable;

public class Hijos implements Serializable {

    private String nombre;
    private String edad;
    private String otrosDatos;
    private String uid;
    private String idHijo;


    public Hijos() {
    }

    public Hijos( String nombre, String edad, String otrosDatos,String uid, String idHijo) {
        this.nombre = nombre;
        this.edad = edad;
        this.otrosDatos = otrosDatos;
        this.uid = uid;
        this.idHijo = idHijo;
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

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getOtrosDatos() {
        return otrosDatos;
    }

    public void setOtrosDatos(String otrosDatos) {
        this.otrosDatos = otrosDatos;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIdHijo() {
        return idHijo;
    }

    public void setIdHijo(String idHijo) {
        this.idHijo = idHijo;
    }
}
