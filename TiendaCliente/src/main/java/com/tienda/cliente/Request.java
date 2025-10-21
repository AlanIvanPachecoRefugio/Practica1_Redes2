package com.tienda.cliente;

/**
 * Esta clase es la plantilla para cualquier peticion que el cliente envie.
 * Es una copia de la que existe en el servidor.
 */
public class Request {
    private String action;
    private Object payload;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}