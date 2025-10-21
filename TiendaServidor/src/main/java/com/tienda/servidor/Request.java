package com.tienda.servidor;

// Esta clase para cualquiero peticion que el cleinte pida
public class Request {
    private String action; // Accion, ej: agregar_carrito
    private Object payload; // Datos principels como el ID

    // Getters y Setters para modificar los atributos
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