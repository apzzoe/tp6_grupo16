package ar.edu.unju.escmi.tp6.main;

import java.util.Scanner;

import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.dominio.Stock;

public class MenuStock {

    private Scanner scanner;

    public MenuStock(Scanner sc) {
        this.scanner = sc;
    }

    public void consultarStock() {

        System.out.println("\n====== Consulta de Stock ======");

        if (CollectionStock.stocks.isEmpty()) {
            System.out.println("No hay stock registrado.");
            return;
        }

        for (Stock stock : CollectionStock.stocks) {
            System.out.println(
                "Codigo: " + stock.getProducto().getCodigo()
                + " | Producto: " + stock.getProducto().getDescripcion()
                + " | Cantidad disponible: " + stock.getCantidad()
            );
        }

        System.out.println("===============================");
    }
}

