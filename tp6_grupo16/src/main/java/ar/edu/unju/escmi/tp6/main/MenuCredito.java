
package ar.edu.unju.escmi.tp6.main;

import java.util.Scanner;

import ar.edu.unju.escmi.tp6.collections.CollectionCliente;
import ar.edu.unju.escmi.tp6.collections.CollectionCredito;
import ar.edu.unju.escmi.tp6.dominio.Cliente;
import ar.edu.unju.escmi.tp6.dominio.Credito;

public class MenuCredito {

    private Scanner scanner;

    public MenuCredito(Scanner sc) {
        this.scanner = sc;
    }

    public void revisarCreditosCliente() {

        System.out.println("\n====== Revisar Creditos ======");
        System.out.println("Ingrese el DNI del cliente: ");

        long dni = scanner.nextLong();

        Cliente cliente = CollectionCliente.buscarCliente(dni);

        if (cliente == null) {
            System.out.println("No existe un cliente registrado con ese DNI.");
            return;
        }

        boolean encontrado = false;

        for (Credito credito : CollectionCredito.creditos) {

            if (credito.getFactura().getCliente().getDni() == cliente.getDni()) {

                System.out.println("\nCredito del cliente: " + cliente.getNombre());
                credito.mostrarCredito();

                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("El cliente no tiene creditos registrados.");
        }
    }
}
