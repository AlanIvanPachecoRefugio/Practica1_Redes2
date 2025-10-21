package com.tienda.cliente;

/**
 * Estrcutrua similar a Producto en Servidor, 
 */
public class Producto {

    // Atributos a usarse igual que en Producto Servidor
    private int id;
    private String nombre;
    private String marca;
    private String tipo;
    private double precio;
    private int stock;

    @Override
    public String toString() {
        return String.format("ID: %-3d | Tipo: %-15s | Marca: %-10s | Nombre: %-25s | Precio: $%-8.2f | Stock: %d",
                id, tipo, marca, nombre, precio, stock);
    }
}