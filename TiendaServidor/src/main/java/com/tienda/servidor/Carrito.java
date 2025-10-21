package com.tienda.servidor;

/**
 * Representa un solo tipo de producto en el carrito, para escoger,stock y dar ID del producto
 */
public class Carrito {

    private int productoId;
    private int cantidad;

    public Carrito(int productoId, int cantidad) {
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}