package com.tienda.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*NOTA: Parte del servidor de alta de productos con el format:  Nombre, Marca, Categiría, Preciio y stock*/


//Lista de catalogo
public class Servidor {

    public static void main(String[] args) {
        List<Producto> catalogo = Collections.synchronizedList(new ArrayList<>());
// --- ELECTRONICA
        catalogo.add(new Producto(1, "Laptop", "Dell", "Electronica", 25000.00, 15));
        catalogo.add(new Producto(2, "Smartphone", "Samsung", "Electronica", 12000.00, 20));
        catalogo.add(new Producto(3, "Monitor", "LG", "Electronica", 4500.00, 10));
        catalogo.add(new Producto(4, "Tablet", "Apple", "Electronica", 15000.00, 15));
        catalogo.add(new Producto(5, "Consola", "Sony", "Electronica", 11500.00, 8));
        catalogo.add(new Producto(6, "Smartwatch", "Redmi", "Electronica", 3200.00, 25));
        catalogo.add(new Producto(7, "Drone", "Funko", "Electronica", 12500.00, 5));
// --- COMPONENTES
        catalogo.add(new Producto(8, "SSD", "Kingston", "Componentes", 1800.00, 50));
        catalogo.add(new Producto(9, "RAM", "Corsair", "Componentes", 2200.00, 40));
        catalogo.add(new Producto(10, "Procesador", "AMD", "Componentes", 5500.00, 20));
        catalogo.add(new Producto(11, "Fuente", "CoolMaster", "Componentes", 2800.00, 15));
        catalogo.add(new Producto(12, "Gabinete", "BalamRush", "Componentes", 1500.00, 10)); // Nuevo
// --- MUEBLES
        catalogo.add(new Producto(13, "Silla", "Troncoso", "Muebles", 8500.00, 8));
        catalogo.add(new Producto(14, "Escritorio", "IKEA", "Muebles", 4200.00, 5));
        catalogo.add(new Producto(15, "Estante", "HomeDepot", "Muebles", 1200.00, 15)); // Nuevo
        catalogo.add(new Producto(16, "Lampara", "Xiaomi", "Muebles", 800.00, 30));    // Nuevo
        catalogo.add(new Producto(17, "Soporte", "Temu", "Muebles", 2500.00, 12)); // Nuevo
// --- PERIFERICOS
        catalogo.add(new Producto(18, "Audifonos", "HyperX", "Perifericos", 1500.00, 25));
        catalogo.add(new Producto(19, "Camara", "Logitech", "Perifericos", 3200.00, 12));
        catalogo.add(new Producto(20, "Teclado", "Razer", "Perifericos", 1800.00, 20));   // Nuevo
        catalogo.add(new Producto(21, "Mouse", "Logitech", "Perifericos", 900.00, 45));   // Nuevo
        catalogo.add(new Producto(22, "Microfono", "Blue", "Perifericos", 2500.00, 10));  // Nuevo

        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            System.out.println("Servidor de la tienda iniciado en el puerto 9999. Esperando clientes...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress());

                // ManejadorC, implementacion
                ManejadorC manejadorCliente = new ManejadorC(clientSocket, catalogo);

                new Thread(manejadorCliente).start();
            }
        } catch (IOException e) {
            System.err.println(" Error en el servidor: " + e.getMessage());
        }
    }
}