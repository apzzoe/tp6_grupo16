package ar.edu.unju.escmi.tp6.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.unju.escmi.tp6.collections.CollectionProducto;
import ar.edu.unju.escmi.tp6.collections.CollectionStock;
import ar.edu.unju.escmi.tp6.dominio.Stock;

class StockTest {

	@BeforeEach
	void setUp() throws Exception {
		CollectionProducto.precargarProductos();
		CollectionStock.precargarStocks();
	}

	@Test
	void testDecrementoStock() {
		Stock stockAirePhilco = CollectionStock.stocks.get(2);
		
		CollectionStock.reducirStock(stockAirePhilco, 3);
		
		assertEquals(10, stockAirePhilco.getCantidad());
	}
}
