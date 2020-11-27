package com.bbsitter.bbsitter.Clases;

import java.io.Serializable;

public class RoomChat implements Serializable {

    private String Emisor;
    private String Receptor;
    private String Mensaje;

    public RoomChat(){}

    public RoomChat(String emisor, String receptor, String mensaje) {
        Emisor = emisor;
        Receptor = receptor;
        Mensaje = mensaje;
    }

    public String getEmisor() {
        return Emisor;
    }

    public void setEmisor(String emisor) {
        Emisor = emisor;
    }

    public String getReceptor() {
        return Receptor;
    }

    public void setReceptor(String receptor) {
        Receptor = receptor;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }
}
