package com.tienda.servidor; // O com.tienda.cliente en el otro proyecto

public class ItemDetalle {
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    // Se necesita un constructor vacío para que Gson funcione correctamente.
    public ItemDetalle() {}

    public ItemDetalle(String nombreProducto, int cantidad, double precioUnitario) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = cantidad * precioUnitario;
    }

    // Getters para que Gson pueda leer los datos
    public String getNombreProducto() { return nombreProducto; }
    public int getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public double getSubtotal() { return subtotal; }
}