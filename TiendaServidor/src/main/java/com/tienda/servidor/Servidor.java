package com.tienda.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*NOTA: Parte del servidor de alta de productos con el format:  Nombre, Marca, Categiría, Preciio y stock*/

public class Servidor {

    public static void main(String[] args) {
        List<Producto> catalogo = Collections.synchronizedList(new ArrayList<>());
        catalogo.add(new Producto(1, "Laptop Gamer Alienware", "Dell", "Electronica", 25000.0, 15));
        catalogo.add(new Producto(2, "Teclado Mecanico RGB", "Corsair", "Accesorios", 3500.0, 30));
        catalogo.add(new Producto(3, "Monitor Curvo 27\"", "Samsung", "Monitores", 7200.0, 20));
        catalogo.add(new Producto(4, "Mouse Inalambrico", "Logitech", "Accesorios", 1200.0, 50));
        catalogo.add(new Producto(5, "SSD 1TB NVMe", "Kingston", "Almacenamiento", 1800.0, 40));

        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            System.out.println("Servidor de la tienda iniciado en el puerto 9999. Esperando clientes...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("¡Cliente conectado desde " + clientSocket.getInetAddress());

                // ManejadorC, implementacion
                ManejadorC manejadorCliente = new ManejadorC(clientSocket, catalogo);

                new Thread(manejadorCliente).start();
            }
        } catch (IOException e) {
            System.err.println(" Error en el servidor: " + e.getMessage());
        }
    }
}