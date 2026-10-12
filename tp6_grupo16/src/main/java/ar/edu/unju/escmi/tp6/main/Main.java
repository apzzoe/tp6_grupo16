package ar.edu.unju.escmi.tp6.main;

import java.util.Scanner;

import ar.edu.unju.escmi.tp6.collections.CollectionCliente;
import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.collections.CollectionTarjetaCredito;
import ar.edu.unju.escmi.tp6.collections.CollectionFactura;
import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Factura;

public class Main {
	
	static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		
        CollectionCliente.precargarClientes();
        CollectionTarjetaCredito.precargarTarjetas();
        CollectionProducto.precargarProductos();
        CollectionStock.precargarStocks();
        
        MenuStock menuStock = new MenuStock(scanner);
        MenuCredito menuCredito = new MenuCredito(scanner);
        
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
            	revisarCompras();
            break;
            
            case 3:
            	CollectionProducto.mostrarProductos();
            	break;
            
            case 4:
            menuStock.consultarStock();
            break;
            
            case 5:
            	menuCredito.revisarCreditosCliente();
            	break;
            	
            case 6:
                System.out.println("Saliendo del sistema...");
                break;
                
            default:
                System.out.println("Opción inválida. Intente de nuevo.");
            }
        }while(opcion != 6);
        
        scanner.close();
	}
	
	private static void revisarCompras() {
		System.out.println("Ingresar el DNI del cliente: ");
		long dni=0;
		try {
		dni=scanner.nextLong();
		}catch(Exception e) {
			scanner.next();
		}
		
		Cliente cliente=CollectionCliente.buscarCliente(dni);
		if(cliente == null) {
			System.out.println("Cliente no registrado");
			}
		
		boolean hayCompras = false;
		for (Factura factura : CollectionFactura.facturas) {
			if (factura.getCliente().getDni() == dni) {
				System.out.println(factura);
				System.out.println("Total de la compra: " + factura.calcularTotal());
				hayCompras = true;
			}
		}

		if (!hayCompras) {
			if(cliente != null)
			System.out.println("El cliente " + cliente.getNombre() + " no tiene compras registradas.");
		}
		
		
	}
}
