package ar.edu.unju.escmi.tp6.main;

import java.util.Scanner;
import java.util.List;

import ar.edu.unju.escmi.tp6.collections.CollectionCliente;
import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.collections.CollectionTarjetaCredito;
import ar.edu.unju.escmi.tp6.collections.CollectionCredito;

import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;

public class Main {
	
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		
		CollectionTarjetaCredito.precargarTarjetas();
        CollectionCliente.precargarClientes();
        CollectionProducto.precargarProductos();
        CollectionStock.precargarStocks();
        int opcion = 0;
        do {
        	System.out.println("\n====== Menu Principal =====");
            System.out.println("1- Realizar una venta");
            System.out.println("2- Revisar compras realizadas por el cliente (debe ingresar el DNI del cliente)");
            System.out.println("3- Mostrar lista de los electrodomésticos");
            System.out.println("4- Consultar stock");
            System.out.println("5- Revisar creditos de un cliente (debe ingresar el DNI del cliente)");
            System.out.println("6- Salir");

            System.out.println("Ingrese su opcion: ");
            opcion = scanner.nextInt();
            
            switch(opcion) {
    			case 1:
    				new MenuVenta(scanner).realizarVenta();
    				break;
    			case 2:
    				mostrarCompras();
    				break;
    			case 3:
    				mostrarElectrodomesticos();
    				break;
    			case 4:
    				mostrarStock();
    				break;
    			case 5:
    				mostrarCreditos();
    				break;
    			case 6:
    				System.out.println("Hasta luego.");
    				break;
    			default:
    				System.out.println("Opcion invalida.");
    			}

    		} while (opcion != 6);

    		scanner.close();
    	}

	private static void mostrarCompras() {
	    System.out.print("Ingrese el DNI del cliente: ");
	    long dni = scanner.nextLong();

	    Cliente cliente = CollectionCliente.buscarCliente(dni);

	    if (cliente == null) {
	        System.out.println("Cliente no encontrado.");
	        return;
	    }

	    List<Factura> compras = cliente.consultarCompras();

	    if (compras == null || compras.isEmpty()) {
	        System.out.println("El cliente no tiene compras.");
	        return;
	    }

	    for (Factura factura : compras) {
	        System.out.println(factura);
	    }
	}
	



private static void mostrarElectrodomesticos() {
    System.out.println("Electrodomesticos incluidos en Ahora 20:");

    for (Producto producto : CollectionProducto.productos) {
        if (producto.verificacionNacional()
                && !producto.tipoProducto().equals("otro")) {
            System.out.println(producto);
        }
    }
}

private static void mostrarStock() {
    System.out.println("Stock de los productos incluidos en Ahora 20:");

    for (Stock stock : CollectionStock.stocks) {
        Producto producto = stock.getProducto();

        if (producto.verificacionNacional()
                && !producto.tipoProducto().equals("otro")) {
            System.out.println(
                producto.getDescripcion()
                + " - Codigo: " + producto.getCodigo()
                + " - Stock: " + stock.getCantidad()
            );
        }
    }
}

private static void mostrarCreditos() {
    System.out.print("Ingrese el DNI del cliente: ");
    long dni = scanner.nextLong();
    scanner.nextLine();

    Cliente cliente = CollectionCliente.buscarCliente(dni);

    if (cliente == null) {
        System.out.println("Cliente no encontrado.");
        return;
    }

    boolean encontrado = false;

    for (Credito credito : CollectionCredito.creditos) {
        if (credito.getTarjetaCredito() != null
                && credito.getTarjetaCredito().getCliente() != null
                && credito.getTarjetaCredito().getCliente().getDni() == dni) {

            credito.mostrarCredito();
            encontrado = true;
        }
    }

    if (!encontrado) {
        System.out.println("El cliente no tiene creditos.");
    }
}}
