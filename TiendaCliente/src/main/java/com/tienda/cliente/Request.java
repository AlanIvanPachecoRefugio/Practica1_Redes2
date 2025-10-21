package com.tienda.cliente;

/**
 * Copia por parte del servidor, donde se vera lo que el usuario pida
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