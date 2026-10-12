package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Credito;
import ar.edu.unju.escmi.tp6.dominio.Cuota;
import ar.edu.unju.escmi.tp6.dominio.Factura;

class CuotaTest {

	@Test
	void testListaCuotasNoNull() {

		Credito credito = new Credito();
		credito.setFactura(new Factura());
		credito.generarCuotas();

		List<Cuota> cuotasObtenidas = credito.getCuotas();


		assertNotNull(cuotasObtenidas, "La lista de cuotas no debería ser null");
	}

	@Test
	void testListaCuotasTiene20Cuotas() {
		
		Credito credito = new Credito();
		credito.setFactura(new Factura());
		credito.generarCuotas();


		int cantidadEsperada = 20;

	
		int cantidadObtenida = credito.getCuotas().size();

		assertEquals(cantidadEsperada, cantidadObtenida, "La lista debería tener 20 cuotas");
	}

	@Test
	void testCantidadCuotas() {
		
		Credito credito = new Credito();
		credito.setFactura(new Factura());
		credito.generarCuotas();

	
		int cantidadPermitida = 20;


		int cantidadObtenida = credito.getCuotas().size();

		
		assertTrue(cantidadObtenida <= cantidadPermitida, "No se pueden generar más de 20 cuotas");
	}
}