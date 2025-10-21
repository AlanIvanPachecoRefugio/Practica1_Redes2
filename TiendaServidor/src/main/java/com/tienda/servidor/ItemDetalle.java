package com.tienda.servidor;

public class ItemDetalle {
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    // Constructor vacio para que se ocupe GSON
    public ItemDetalle() {}

    public ItemDetalle(String nombreProducto, int cantidad, double precioUnitario) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    // Getters para que se puedan leer datos por parte de GSon
    public String getNombreProducto() { return nombreProducto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return subtotal; }
}