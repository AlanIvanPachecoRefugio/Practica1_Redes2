package com.tienda.cliente;

// Plantilla para cada respuesta del servidor.
public class Response {
    private String status; // "OK", "ERROR"
    private Object data;

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