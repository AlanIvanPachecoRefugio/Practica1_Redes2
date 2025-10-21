package com.tienda.cliente;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
 * NOTA: Menu principal y vista que tendrá el cliente
pruebaaa12

@ ivano
 */
public class Cliente {

    public static void main(String[] args) {
        final String HOST = "127.0.0.1"; // IP del sever
        final int PORT = 9999;           

        try (
            Socket socket = new Socket(HOST, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Conectado al servidor de la tienda.");
            Gson gson = new Gson();

            // 1. Recibe y muestra el catalogo usando JSON  usamos la dependencia GSON
            String catalogoJson = in.readLine();
            Type tipoListaProductos = new TypeToken<List<Producto>>(){}.getType();
            List<Producto> catalogo = gson.fromJson(catalogoJson, tipoListaProductos);
            
            System.out.println("\n--- CATALOGO DE PRODUCTOS ---");
            catalogo.forEach(p -> System.out.println(p));
            System.out.println("----------------------------\n");

            // Menu principal
            String userInput;
            while (true) {
                System.out.println("\n------ MENU PRINCIPAL -----");
                System.out.println("1. Listar artIculos por tipo");
                System.out.println("2. Agregar articulo al carrito");
                System.out.println("3. Ver mi carrito");
                System.out.println("4. Finalizar Compra");
                System.out.println("5. Salir");
                System.out.print("Elige una opcion: ");

                userInput = scanner.nextLine();

                if ("5".equals(userInput)) {
                    break; // Cierre de conexion
                }

                if ("1".equals(userInput)) {
                    System.out.print("Introduce el tipo de articulo (Ej: Electronica, Accesorios): ");
                    String tipo = scanner.nextLine();

                    Request request = new Request();
                    request.setAction("listar_por_tipo");
                    request.setPayload(tipo);

                    out.println(gson.toJson(request));

                    String serverResponseJson = in.readLine();
                    Response response = gson.fromJson(serverResponseJson, Response.class);

                    if ("OK".equals(response.getStatus())) {
                        String dataJson = gson.toJson(response.getData());
                        List<Producto> productosFiltrados = gson.fromJson(dataJson, tipoListaProductos);
                        
                        System.out.println("\n--- RESULTADOS DE LA BUSQUEDA ---");
                        if(productosFiltrados.isEmpty()){
                            System.out.println("No se encontraron articulos de ese tipo.");
                        } else {
                            productosFiltrados.forEach(p -> System.out.println(p));
                        }
                        System.out.println("---------------------------------");
                    } else {
                        System.err.println("Error del servidor: " + response.getData());
                    }
                } 
                else if ("2".equals(userInput)) {
                    try {
                        System.out.print("Introduce el ID del producto a agregar: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("Introduce la cantidad: ");
                        int cantidad = Integer.parseInt(scanner.nextLine());

                        Map<String, Integer> payload = new HashMap<>();
                        payload.put("productoId", id);
                        payload.put("cantidad", cantidad);

                        Request request = new Request();
                        request.setAction("agregar_carrito");
                        request.setPayload(payload);

                        out.println(gson.toJson(request));
                        String serverResponseJson = in.readLine();
                        Response response = gson.fromJson(serverResponseJson, Response.class);

                        if ("OK".equals(response.getStatus())) {
                            System.out.println("Aprobado " + response.getData());
                        } else {
                            System.err.println("Error: " + response.getData());
                        }

                    } catch (NumberFormatException e) {
                        System.err.println("Error: El ID y la cantidad deben ser numeros.");
                    }
                }
                else if ("3".equals(userInput)) {
                    Request request = new Request();
                    request.setAction("ver_carrito");

                    out.println(gson.toJson(request));

                    String serverResponseJson = in.readLine();
                    Response response = gson.fromJson(serverResponseJson, Response.class);

                    if ("OK".equals(response.getStatus())) {
                        Type tipoListaDetalle = new TypeToken<List<ItemDetalle>>(){}.getType();
                        String dataJson = gson.toJson(response.getData());
                        List<ItemDetalle> detalles = gson.fromJson(dataJson, tipoListaDetalle);
                        
                        System.out.println("\n--- TU CARRITO DE COMPRAS ---");
                        if (detalles.isEmpty()) {
                            System.out.println("Tu carrito esta vacio.");
                        } else {
                            double total = 0;
                            System.out.printf("%-30s | %-10s | %-15s | %-15s%n", "PRODUCTO", "CANTIDAD", "PRECIO UNIT.", "SUBTOTAL");
                            System.out.println(new String(new char[80]).replace("\0", "-"));
                            for (ItemDetalle item : detalles) {
                                System.out.printf("%-30s | %-10d | $%-14.2f | $%-15.2f%n",
                                        item.getNombreProducto(),
                                        item.getCantidad(),
                                        item.getPrecioUnitario(),
                                        item.getSubtotal());
                                total += item.getSubtotal();
                            }
                            System.out.println(new String(new char[80]).replace("\0", "-"));
                            System.out.printf("%-59s | TOTAL: $%-15.2f%n", "", total);
                        }
                        System.out.println("---------------------------------");

                    } else {
                        System.err.println("Error del servidor: " + response.getData());
                    }
                }
                else if ("4".equals(userInput)) {
                    Request request = new Request();
                    request.setAction("finalizar_compra");

                    out.println(gson.toJson(request));

                    String serverResponseJson = in.readLine();
                    Response response = gson.fromJson(serverResponseJson, Response.class);

                    if ("OK".equals(response.getStatus())) {
                        String ticket = (String) response.getData();
                        System.out.println("\n Compra finalizada con exito!");
                        System.out.println(ticket);
                    } else {
                        System.err.println(" Error al finalizar la compra: " + response.getData());
                    }
                }
            }

            System.out.println("Gracias por su visita.");

        } catch (IOException e) {
            System.err.println("Error con la comunicacion al servidor " + e.getMessage());
        }
    }
}