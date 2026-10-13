package store;

import java.util.ArrayList;

public class Inventario {
    private ArrayList<Producto> productos;

    public Inventario() {
        productos = new ArrayList<>();
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public Producto buscarProducto(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo)) {
                return p;
            }
        }
        return null;
    }

    public void mostrarProductos() {
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
            return;
        }
        for (Producto p : productos) {
            p.mostrarInfo();
            System.out.println("----------------------");
        }
    }

    public void aplicarAumentoGeneral(double porcentaje) {
        if (porcentaje < 0) {
            throw new IllegalArgumentException("Porcentaje invalido");
        }
        for (Producto producto : productos) {
            double nuevoPrecio = Math.round(producto.getPrecio() * (1 + porcentaje / 100));
            producto.setPrecio(nuevoPrecio);
        }
    }
}
