package com.bbsitter.bbsitter.Clases;

import java.io.Serializable;

public class Chat implements Serializable {

    String Id;

    public Chat(){}

    public Chat(String id) {
        Id = id;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
