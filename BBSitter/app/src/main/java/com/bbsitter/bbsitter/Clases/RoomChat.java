package com.bbsitter.bbsitter.Clases;

import java.io.Serializable;

public class RoomChat implements Serializable {

    private String emisor;
    private String receptor;
    private String mensaje;
    private String fecha;

    public RoomChat(){}

    public RoomChat(String emisor, String receptor, String mensaje, String fecha) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
