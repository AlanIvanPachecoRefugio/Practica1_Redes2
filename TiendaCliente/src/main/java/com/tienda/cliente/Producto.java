package com.tienda.cliente;

/**
 * Esta es la copia o "manual de instrucciones" que el cliente necesita
 * para entender los datos de productos que recibe del servidor.
 * Debe coincidir con la estructura de la clase Producto del servidor.
 */
public class Producto {

    // Atributos que deben ser identicos a los de la clase Producto del servidor
    private int id;
    private String nombre;
    private String marca;
    private String tipo;
    private double precio;
    private int stock;

    // El metodo toString() es muy util para imprimir el objeto de forma legible.
    // NetBeans lo usara cuando hagas System.out.println(producto).
    @Override
    public String toString() {
        return String.format("ID: %-3d | Tipo: %-15s | Marca: %-10s | Nombre: %-25s | Precio: $%-8.2f | Stock: %d",
                id, tipo, marca, nombre, precio, stock);
    }
}