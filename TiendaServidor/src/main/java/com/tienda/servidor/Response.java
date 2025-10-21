package com.tienda.servidor;

// Esta clase es la plantilla para cualquier respuesta que el servidor envie.
public class Response {
    private String status; // "OK", "ERROR"
    private Object data;   // Los datos de la respuesta, ej: la lista de productos o un mensaje de error.

    // Getters y Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}