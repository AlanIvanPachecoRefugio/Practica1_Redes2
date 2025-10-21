package com.tienda.servidor;

// Esta clase es la plantilla para cualquier peticion que el cliente envie.
public class Request {
    private String action; // Ej: "listar_productos", "agregar_carrito"
    private Object payload; // Datos adicionales, ej: el ID de un producto

    // Getters y Setters para poder acceder y modificar los atributos.
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