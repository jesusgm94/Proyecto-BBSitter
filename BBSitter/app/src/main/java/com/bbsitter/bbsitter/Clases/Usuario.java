package com.bbsitter.bbsitter.Clases;

public class Usuario  {

    private boolean perfil;
    private String email;
    private String pass;
    private String Uid;

    public Usuario(){}

    public Usuario(boolean perfil, String email, String pass, String uid) {
        this.perfil = perfil;
        this.email = email;
        this.pass = pass;
        Uid = uid;
    }


    public boolean isPerfil() {
        return perfil;
    }

    public void setPerfil(boolean perfil) {
        this.perfil = perfil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
