package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Detalle;
import ar.edu.unju.escmi.tp6.dominio.Factura;
import ar.edu.unju.escmi.tp6.dominio.Producto;
import ar.edu.unju.escmi.tp6.dominio.TarjetaCredito;

class CreditoTest {

	public static final int MONTO_1 = 500000;
	public static final int MONTO_2 = 1500000;
	public static final int MONTO_3 = 1000;

	public static final double MONTO_PERMITIDO = 2500000;

	private Factura facturaConProductos;
	private TarjetaCredito tarjeta;

	@BeforeEach
	void setUp() {
		Producto producto1 = new Producto(1, "Producto 1", 100000, "Argentina");
		Producto producto2 = new Producto(2, "Producto 2", 250000, "Argentina");
		Producto producto3 = new Producto(3, "Producto 3", 80000, "Argentina");

		List<Detalle> detalles = new ArrayList<Detalle>();
		detalles.add(new Detalle(2, 0, producto1)); 
		detalles.add(new Detalle(1, 0, producto2)); 
		detalles.add(new Detalle(3, 0, producto3)); 

		facturaConProductos = new Factura();
		facturaConProductos.setDetalles(detalles);

		Cliente cliente = new Cliente(45111222, "Mario Barca", "Alvear 120", "65454686");
		tarjeta = new TarjetaCredito(232323, LocalDate.of(2030, 10, 10), cliente, 1000000);
	}

	@Test
	void testMontoCreditoValido() {
		double montoObtenido = crearFactura().calcularTotal();
		assertTrue(montoObtenido <= MONTO_PERMITIDO, "El monto total no deberia superar al monto permitido");
	}

	@Test
	void testSumaDetallesIgualTotalFactura() {
		double sumaDetalles = 0;
		for (Detalle detalle : facturaConProductos.getDetalles()) {
			sumaDetalles += detalle.getImporte();
		}

		double montoEsperado = 690000; 
		assertEquals(montoEsperado, sumaDetalles, 0.001);
		assertEquals(sumaDetalles, facturaConProductos.calcularTotal(), 0.001,
				"La suma de los detalles deberia ser igual al total de la factura");
	}

	@Test
	void testCompraNoSuperaMontoPermitidoNiLimiteTarjeta() {
		double totalCompra = facturaConProductos.calcularTotal();

		assertTrue(totalCompra <= MONTO_PERMITIDO, "La compra no deberia superar los 2.500.000");
		assertTrue(totalCompra <= tarjeta.getLimiteCompra(),
				"La compra no deberia superar el limite de la tarjeta");
	}

	private Factura crearFactura() {
		Factura factura = new Factura();
		factura.setDetalles(crearListaDetalles());
		return factura;
	}

	private List<Detalle> crearListaDetalles() {
		List<Detalle> listaDetalles = new ArrayList<Detalle>();
		Detalle detalle1 = new Detalle();
		detalle1.setImporte(MONTO_1);
		Detalle detalle2 = new Detalle();
		detalle2.setImporte(MONTO_2);
		Detalle detalle3 = new Detalle();
		detalle3.setImporte(MONTO_3);
		listaDetalles.add(detalle1);
		listaDetalles.add(detalle2);
		listaDetalles.add(detalle3);
		return listaDetalles;
	}
}
