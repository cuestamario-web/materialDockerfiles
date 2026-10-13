package store;

import java.util.ArrayList;

public class Tienda {
    private String nombre;
    private Inventario inventario;
    private ArrayList<Cliente> clientes;
    private ArrayList<Venta> ventas;

    public Tienda(String nombre) {
        this.nombre = nombre;
        this.inventario = new Inventario();
        this.clientes = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public Cliente buscarCliente(String id) {
        for (Cliente c : clientes) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }

    public void registrarVenta(Venta venta) {
        ventas.add(venta);
    }

    /** Genera el siguiente código de venta: V001, V002, ... */
    public String siguienteCodigoVenta() {
        return String.format("V%03d", ventas.size() + 1);
    }

    public void mostrarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        for (Cliente c : clientes) {
            c.mostrarInfo();
            System.out.println("----------------------");
        }
    }

    public void mostrarVentas() {
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
            return;
        }
        for (Venta v : ventas) {
            v.mostrarDetalle();
            System.out.println("=====================");
        }
    }
}
