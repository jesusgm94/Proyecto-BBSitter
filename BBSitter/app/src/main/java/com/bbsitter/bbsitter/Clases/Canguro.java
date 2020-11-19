package com.bbsitter.bbsitter.Clases;

import java.io.Serializable;
import java.util.Map;

public class Canguro implements Serializable {

    //Atributos
    private String img;

    // Datos personales
    private String nombre;
    private String apellidos;
    private String fechaNacimiento;
    private int edad;  //Calculada a traves del String
    private String sexo;

    // Direccion y fecha nacimiento
    private String direccion; // Obtenidad de autocompletar Direccion
    private double longitudLoc;
    private double latitudLoc;

    // Demás atributos
    private double precioHora;    //Obtenida de slider precio/Hora
    private String experiencia;
    private String descripcion;
    Map<String, Boolean> mapPluses;     // Pluses
    Map<String, Boolean> mapPrefenciaEdades;    // PreferenciaEdades
    Map<String, Boolean> mapIdiomas;    // PreferenciaEdades

    // Fecha creacion
    String fechaCreacionPerfil;


    // Constructores

    public Canguro(){}

    public Canguro(String urlFoto, String nombre, String apellidos, String fechaNacimiento, int edad, String sexo, String direccion, double longitudLoc, double latitudLoc, double precioHora, String experiencia, String descripcion, Map<String, Boolean> mapPluses, Map<String, Boolean> mapPrefenciaEdades, Map<String, Boolean> mapIdiomas, String fechaCreacionPerfil) {
        this.img = urlFoto;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.sexo = sexo;
        this.direccion = direccion;
        this.longitudLoc = longitudLoc;
        this.latitudLoc = latitudLoc;
        this.precioHora = precioHora;
        this.experiencia = experiencia;
        this.descripcion = descripcion;
        this.mapPluses = mapPluses;
        this.mapPrefenciaEdades = mapPrefenciaEdades;
        this.mapIdiomas = mapIdiomas;
        this.fechaCreacionPerfil = fechaCreacionPerfil;
    }

    // Getters and setters

    public String getImg() {
        return img;
    }

    public void setImg(String urlFoto) {
        this.img = urlFoto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getLongitudLoc() {
        return longitudLoc;
    }

    public void setLongitudLoc(double longitudLoc) {
        this.longitudLoc = longitudLoc;
    }

    public double getLatitudLoc() {
        return latitudLoc;
    }

    public void setLatitudLoc(double latitudLoc) {
        this.latitudLoc = latitudLoc;
    }

    public double getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(double precioHora) {
        this.precioHora = precioHora;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Map<String, Boolean> getMapPluses() {
        return mapPluses;
    }

    public void setMapPluses(Map<String, Boolean> mapPluses) {
        this.mapPluses = mapPluses;
    }

    public Map<String, Boolean> getMapPrefenciaEdades() {
        return mapPrefenciaEdades;
    }

    public void setMapPrefenciaEdades(Map<String, Boolean> mapPrefenciaEdades) {
        this.mapPrefenciaEdades = mapPrefenciaEdades;
    }

    public Map<String, Boolean> getMapIdiomas() {
        return mapIdiomas;
    }

    public void setMapIdiomas(Map<String, Boolean> mapIdiomas) {
        this.mapIdiomas = mapIdiomas;
    }

    public String getFechaCreacionPerfil() {
        return fechaCreacionPerfil;
    }

    public void setFechaCreacionPerfil(String fechaCreacionPerfil) {
        this.fechaCreacionPerfil = fechaCreacionPerfil;
    }
}
