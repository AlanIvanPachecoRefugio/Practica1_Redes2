package com.tienda.servidor;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManejadorC implements Runnable {

    private final Socket clientSocket;
    private final List<Producto> catalogo;
    // Por cada cliente se guarda la lista de productos
    private final List<Carrito> carritoDeEsteCliente = new ArrayList<>();

    public ManejadorC(Socket socket, List<Producto> catalogo) {
        this.clientSocket = socket;
        this.catalogo = catalogo;
    }

    @Override
    public void run() {
        try (
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            Gson gson = new Gson();

            // 1. Envio del catalogo al cliente
            System.out.println("Enviando catalogo al cliente...");
            String catalogoJson = gson.toJson(catalogo);
            out.println(catalogoJson);
            // 2. Escucha y procesa al cliente
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Peticion recibida del cliente: " + inputLine);

                Request request = gson.fromJson(inputLine, Request.class);
                Response response = new Response();

                switch (request.getAction()) {
                    
case "finalizar_compra":
    try {
        if (carritoDeEsteCliente.isEmpty()) {
            response.setStatus("ERROR");
            response.setData("Tu carrito esta vacio, no se puede finalizar la compra.");
            break; 
        }

        // Builder para mostrar el ticket
        StringBuilder ticket = new StringBuilder();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime ahora = LocalDateTime.now();
        double totalCompra = 0;

        ticket.append("---- TICKET DE COMPRA ----\n");
        ticket.append("Fecha: ").append(dtf.format(ahora)).append("\n");
        ticket.append("----------------------------------------\n");
        ticket.append(String.format("%-20s | %-5s | %s%n", "PRODUCTO", "CANT", "SUBTOTAL"));
        ticket.append("----------------------------------------\n");

        // Reutilizacion de ver carrito para el recuento y el ticket
        for (Carrito item : carritoDeEsteCliente) {
            for (Producto p : catalogo) {
                if (p.getId() == item.getProductoId()) {
                    double subtotal = p.getPrecio() * item.getCantidad();
                    ticket.append(String.format("%-20.20s | %-5d | $%.2f%n", p.getNombre(), item.getCantidad(), subtotal));
                    totalCompra += subtotal;
                    break;
                }
            }
        }

        ticket.append("----------------------------------------\n");
        ticket.append(String.format("TOTAL: $%.2f%n", totalCompra));
        ticket.append("---- Gracias por tu compra ----\n");

        // Limpiamos el carrito del cliente
        carritoDeEsteCliente.clear();

        response.setStatus("OK");
        response.setData(ticket.toString()); // Se envia el ticket (String)

    } catch (Exception e) {
        response.setStatus("ERROR");
        response.setData("Ocurrio un error al finalizar la compra: " + e.getMessage());
    }
    break;
                    case "ver_carrito":
                        try {
                            // Lista detalle con respuesta
                            List<ItemDetalle> detallesCarrito = new ArrayList<>();
                            
                            // Recorrido del carrito de clientes que contiene ID y stock
                            for (Carrito item : carritoDeEsteCliente) {
                                // Por cada articulo se busca en el catalogo 
                                for (Producto p : catalogo) {
                                    if (p.getId() == item.getProductoId()) {
                                        // Creamos un objeto detallado y se agrega a la lista
                                        detallesCarrito.add(new ItemDetalle(p.getNombre(), item.getCantidad(), p.getPrecio()));
                                        break; // Una vez encontrado el producto se agrega y pasa al siguiente producto
                                    }
                                }
                            }
                            response.setStatus("OK");
                            response.setData(detallesCarrito);

                        } catch (Exception e) {
                            response.setStatus("ERROR");
                            response.setData("No se pudo procesar la solicitud para ver el carrito: " + e.getMessage());
                        }
                        break;
                    
                    case "listar_por_tipo":
                        try {
                            String tipo = (String) request.getPayload();
                            List<Producto> productosFiltrados = new ArrayList<>();
                            for (Producto p : catalogo) {
                                if (p.getTipo().equalsIgnoreCase(tipo)) {
                                    productosFiltrados.add(p);
                                }
                            }
                            response.setStatus("OK");
                            response.setData(productosFiltrados);
                        } catch (Exception e) {
                            response.setStatus("ERROR");
                            response.setData("Datos de pago invalidos.");
                        }
                        break;

                    case "agregar_carrito":
                        try {
                            // Se verifica el payload en forma de mapa para facilidad en tipo especifico 
                            if (!(request.getPayload() instanceof java.util.Map)) {
                                throw new Exception("El pago debe de ser un productoID y cantidad.");
                            }
                            
                            java.util.Map<String, Object> payload = (java.util.Map<String, Object>) request.getPayload();
                            
                            // extraemos valores de Number
                            Object idObj = payload.get("productoId");
                            Object cantObj = payload.get("cantidad");

                            if (idObj == null || cantObj == null) {
                                throw new Exception("Faltan 'productoId' o 'cantidad' en el payload.");
                            }

                            int productoId = ((Number) idObj).intValue();
                            int cantidad = ((Number) cantObj).intValue();

                            synchronized (catalogo) {
                                Producto productoSeleccionado = null;
                                for (Producto p : catalogo) {
                                    if (p.getId() == productoId) {
                                        productoSeleccionado = p;
                                        break;
                                    }
                                }

                                if (productoSeleccionado == null) {
                                    response.setStatus("ERROR");
                                    response.setData("El producto con ese ID no existe.");
                                } else if (productoSeleccionado.getStock() < cantidad) {
                                    response.setStatus("ERROR");
                                    response.setData("Stock insuficiente. Disponibles: " + productoSeleccionado.getStock());
                                } else {
                                    productoSeleccionado.setStock(productoSeleccionado.getStock() - cantidad);
                                    carritoDeEsteCliente.add(new Carrito(productoId, cantidad));
                                    response.setStatus("OK");
                                    response.setData("Producto agregado al carrito exitosamente.");
                                }
                            }
                        } catch (Exception e) {
                            response.setStatus("ERROR");
                            response.setData("Payload incorrecto o invalido: " + e.getMessage());
                        }
                        break;

                    default:
                        response.setStatus("ERROR");
                        response.setData("Accion desconocida.");
                        break;
                }
                
                // 3. Envio de respuesta final al cliente
                out.println(gson.toJson(response));
            }

        } catch (IOException e) {
            // Si el cliente se desconecta de forma anormal, sale este error
            System.err.println("Cliente desconectado inesperadamente: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Conexion con el cliente cerrada.");
            } catch (IOException e) {
                System.err.println("Error al cerrar el socket del cliente: " + e.getMessage());
            }
        }
    }
}