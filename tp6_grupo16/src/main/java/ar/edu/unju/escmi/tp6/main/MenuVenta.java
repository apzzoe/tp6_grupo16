package ar.edu.unju.escmi.tp6.main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
import ar.edu.unju.escmi.tp6.collections.CollectionCliente;
import ar.edu.unju.escmi.tp6.collections.CollectionCredito;
import ar.edu.unju.escmi.tp6.collections.CollectionFactura;
import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.collections.CollectionTarjetaCredito;
import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Cuota;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.Stock;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

public class MenuVenta {
	
	private Scanner scanner;
	private DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private Cliente cliente;
	private LocalDate fecha;
	private List<Detalle> detalles = new ArrayList<Detalle>();
	
	public MenuVenta(Scanner sc) {
		this.scanner = sc;
	}
	 
	public void mostrarMenuVenta() {
		
       System.out.println("\n====== Menu de Ventas =====");
       System.out.println("1- Ingresar dni del cliente.");
       System.out.println("2- Fecha de la compra.");
       System.out.println("3- Agregar producto al carrito.");
       System.out.println("4- Ver productos agregados al carrito.");
       System.out.println("5- Confirmar venta.");
       System.out.println("6- Volver..."); 
       System.out.println("\n===========================");
	
	}
	
	public void realizarVenta() {
		int opcionVenta;
		
		do {
			mostrarMenuVenta();
			System.out.println("Ingrese su opción: ");
			opcionVenta = scanner.nextInt();
			
			switch(opcionVenta) {
            case 1: 
            	ingresarCliente();
            	break;
            case 2: 
            	ingresarFecha();
            	break;
            case 3:
            	sumarProducto();
            	break;
            case 4: 
            	verCarrito();
            	break;
            case 5:
            	confirmarVenta();
            	break;
            case 6: 
            	System.out.println("Volviendo al menú principal...");
            	break;
            	
            	default:
            		System.out.println("Ingrese una opción válida");
            	
			}
			
		}while(opcionVenta != 6);
		
		
	}
	

	private void ingresarCliente() {
		long dni = scanner.nextInt();
		Cliente encontrado = CollectionCliente.buscarCliente(dni);
	
		if (encontrado == null) {
			System.out.println("No hay un cliente registrado con ese dni.");
		} else {
			this.cliente = encontrado;
			System.out.println("¡Cliente encontrado! Usted es:" + cliente.getNombre());
		}
	}
	
	private void ingresarFecha() {
		scanner.nextLine();
		System.out.println("Ingrese la fecha de la compra (dd/mm/yyyy): ");
		String texto = scanner.nextLine().trim();
 
		try {
			this.fecha = LocalDate.parse(texto, formato);
			System.out.println("Fecha ingresada: " + fecha.format(formato));
		} catch (DateTimeParseException e) {
			System.out.println("Formato de fecha invalido.");
		}
	}
	
	private void sumarProducto() {
		
		System.out.println("Ingrese el código del producto: ");
		int codigo = scanner.nextInt();
		Producto producto = CollectionProducto.buscarProducto(codigo);
		
 
		if (producto == null) {
			System.out.println("No existe un producto con ese codigo.");
			return;
		}
		if (!producto.verificacionNacional() || producto.tipoProducto().equals("otro")) {
			System.out.println("El producto no está incluido en el programa Ahora 20.");
			return;
		}
		
		System.out.println("Ingrese la cantidad deseada: ");
		int cantidad = scanner.nextInt();
		
		if (cantidad <= 0) {
		    System.out.println("La cantidad debe ser mayor a 0.");
		    return;
		}
		
		int yaEnCarrito = 0;
		Stock stock = CollectionStock.buscarStock(producto);
 
		for (Detalle detalle : detalles) {
		    if (detalle.getProducto().getCodigo() == producto.getCodigo()) {
		        yaEnCarrito += detalle.getCantidad();
		    }
		}

		if (stock == null || !stock.hayStock(yaEnCarrito + cantidad)) {
		    System.out.println("No hay stock suficiente.");
		    return;
		}
 
		detalles.add(new Detalle(cantidad, 0, producto));
		System.out.println("¡Producto agregado al carrito!.");
		
	}
	
	
	private void verCarrito() {
	    if (detalles.isEmpty()) {
	        System.out.println("El carrito está vacío.");
	        return;
	    }

	    double total = 0;
	    for (Detalle detalle : detalles) {
	        System.out.println(detalle.getProducto().getDescripcion()
	                + " | Cantidad: " + detalle.getCantidad()
	                + " | Importe: " + detalle.getImporte());
	        total += detalle.getImporte();
	    }
	    System.out.println("Total parcial: " + total);
	}
	
	
	private void confirmarVenta() {
		boolean ventaRealizada = false;
 
		try {
			if (cliente == null) {
				throw new Exception("Falta ingresar el cliente.");
			}
			if (fecha == null) {
				throw new Exception("Falta ingresar la fecha de la compra.");
			}
			if (detalles.isEmpty()) {
				throw new Exception("No se agrego ningun producto.");
			}
 
	
			TarjetaCredito tarjeta = CollectionTarjetaCredito.buscarTarjetaCredito(cliente);
 
			if (tarjeta.tarjetaVencida(fecha)) {
				throw new Exception("La tarjeta esta vencida.");
			}
 
			
			long nroFactura = CollectionFactura.facturas.size() + 1;
			Factura factura = new Factura(fecha, nroFactura, cliente, detalles);
 
			if (factura.promocionVencida(fecha)) {
				throw new Exception("El programa Ahora 20 ya no está en vigencia.");
			}
			if (factura.montoValido()) {
				throw new Exception("El total supera el límite permitido por el programa.");
			}
			if (!tarjeta.limiteValido(factura.calcularTotal())) {
				throw new Exception("El limite de la tarjeta es insuficiente.");
			}
 
 
			Credito credito = new Credito(tarjeta, factura, new ArrayList<Cuota>());
			CollectionCredito.agregarCredito(credito);
			CollectionFactura.agregarFactura(factura);
 
			for (Detalle detalle : detalles) {
				Stock stock = CollectionStock.buscarStock(detalle.getProducto());
				CollectionStock.reducirStock(stock, detalle.getCantidad());
			}
 
			System.out.println("\nVenta lista.");
			credito.mostrarCredito();
			ventaRealizada = true;
 
		} catch (Exception e) {
			System.out.println("No se pudo realizar la venta: " + e.getMessage());
		} finally {
			if (ventaRealizada) {
				this.cliente = null;
				this.fecha = null;
				this.detalles = new ArrayList<Detalle>();
			}
			System.out.println("Fin del proceso de venta.");
		}
	}

}
