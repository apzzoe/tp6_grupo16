package ar.edu.unju.escmi.tp6.main;

import java.util.Scanner;

public class MenuVenta {
	
	private Scanner scVenta;
	
	public MenuVenta(Scanner sc) {
		this.scVenta = sc;
	}
	 
	public void mostrarMenuVenta() {
		
	}
	
	public void realizarVenta() {
		int opcionVenta;
		
		do {
			mostrarMenuVenta();
			opcionVenta = scVenta.nextInt();
			
		}while(opcionVenta != 6);
		
		
	}
	
	
	
	
	//metodos privados para pedir al usuario los datos de la factra como fecha, producto
	//cliente, etc, los que haya que especificar para hacer los controles q pide el tp
	
	// mostrar menu solo muestra el menu dea
	
	// en realizar venta estaria el switch recibiendo los datos de los metodos que armemos supongo
			
	
}
