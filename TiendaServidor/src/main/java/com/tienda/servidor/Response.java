package com.tienda.servidor;

// Esta clase para la rspuesta que de el servidor
public class Response {
    private String status; // Vista de Ok o Error
    private Object data;   // Respuyestas de visualizacion como lista de producots o algun error

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