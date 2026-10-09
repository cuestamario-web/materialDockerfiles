package store;
import java.util.ArrayList;

public class Venta {
    private Cliente cliente;
    private ArrayList<Producto> productosVendidos;
    private double total;

    public Venta(Cliente cliente) {
        this.cliente = cliente;
        this.productosVendidos = new ArrayList<>();
        this.total = 0;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ArrayList<Producto> getProductosVendidos() {
        return productosVendidos;
    }

    public void setProductosVendidos(ArrayList<Producto> productosVendidos) {
        this.productosVendidos = productosVendidos;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        if (producto.getCantidad() >= cantidad) {
            producto.reducirStock(cantidad);
            productosVendidos.add(producto);
            total += producto.getPrecio() * cantidad;
        } else {
            System.out.println("Stock insuficiente.");
        }
    }

    public double getTotal() {
        return total;
    }

    public void mostrarDetalle() {
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Productos comprados:");
        for (Producto p : productosVendidos) {
            System.out.println("- " + p.getNombre());
        }
        System.out.println("Total: $" + total);
    }
}
