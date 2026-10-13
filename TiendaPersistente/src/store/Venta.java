package store;

import java.util.ArrayList;

public class Venta {
    private String codigo;
    private Cliente cliente;
    private String fecha;
    private ArrayList<DetalleVenta> detalles;

    public Venta(String codigo, Cliente cliente, String fecha) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.fecha = fecha;
        this.detalles = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public ArrayList<DetalleVenta> getDetalles() {
        return detalles;
    }

    /**
     * Agrega un producto a la venta y descuenta el stock.
     * Devuelve false si no hay unidades suficientes.
     */
    public boolean agregarProducto(Producto producto, int cantidad) {
        if (cantidad <= 0 || producto.getCantidad() < cantidad) {
            return false;
        }
        producto.reducirStock(cantidad);
        detalles.add(new DetalleVenta(
                producto.getCodigo(),
                producto.getNombre(),
                producto.getPrecio(),
                cantidad
        ));
        return true;
    }

    public double getTotal() {
        double total = 0;
        for (DetalleVenta d : detalles) {
            total += d.getSubtotal();
        }
        return total;
    }

    public void mostrarDetalle() {
        System.out.println("Venta: " + codigo + "   Fecha: " + fecha);
        System.out.println("Cliente: " + cliente.getNombre() + " (" + cliente.getId() + ")");
        System.out.println("Productos comprados:");
        for (DetalleVenta d : detalles) {
            System.out.println("- " + d.getNombreProducto()
                    + " x" + d.getCantidad()
                    + "  ($" + d.getPrecioUnitario() + " c/u) = $" + d.getSubtotal());
        }
        System.out.println("Total: $" + getTotal());
    }

    // ===== Persistencia =====
    // Cada venta se guarda como una línea del archivo ventas.txt:
    // V001;C01;2026-10-13;P01,Arroz,2500.0,2|P02,Leche,3000.0,1
    // Del cliente solo se guarda el id: al cargar se busca en la lista de clientes.

    public String aLinea() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < detalles.size(); i++) {
            if (i > 0) {
                sb.append("|");
            }
            sb.append(detalles.get(i).aTexto());
        }
        return codigo + ";" + cliente.getId() + ";" + fecha + ";" + sb;
    }

    public static Venta desdeLinea(String linea, Tienda tienda) {
        String[] partes = linea.split(";");
        Cliente cliente = tienda.buscarCliente(partes[1]);
        if (cliente == null) {
            // El cliente fue borrado a mano del txt: se conserva la venta igual
            cliente = new Cliente(partes[1], "(cliente no encontrado)", "-");
        }
        Venta venta = new Venta(partes[0], cliente, partes[2]);
        for (String texto : partes[3].split("\\|")) {
            venta.detalles.add(DetalleVenta.desdeTexto(texto));
        }
        return venta;
    }
}
