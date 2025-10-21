package com.tienda.servidor;

/**
 * Esta clase es el "molde" para cada producto de nuestra tienda.
 * Contiene toda la informacion que un producto necesita.
 */
public class Producto {

    // Atributos (las caracteristicas de un producto)
    private int id;
    private String nombre;
    private String marca;
    private String tipo;
    private double precio;
    private int stock;

    // Constructor: Se usa para crear nuevos objetos "Producto" con sus datos.
    public Producto(int id, String nombre, String marca, String tipo, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.tipo = tipo;
        this.precio = precio;
        this.stock = stock;
    }

    // --- Metodos Getters y Setters ---
    // Los "Getters" nos permiten LEER el valor de un atributo desde fuera de la clase.
    // Los "Setters" nos permiten CAMBIAR el valor de un atributo desde fuera.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    
    // Este metodo es util para imprimir la informacion del producto facilmente.
    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", stock=" + stock + '}';
    }
}