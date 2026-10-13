package store;

/**
 * Una línea de una venta: qué producto se vendió, a qué precio y cuántas unidades.
 * Se guarda una copia del nombre y del precio para que, si el producto
 * cambia de precio después, la venta conserve el valor con el que se hizo.
 */
public class DetalleVenta {
    private String codigoProducto;
    private String nombreProducto;
    private double precioUnitario;
    private int cantidad;

    public DetalleVenta(String codigoProducto, String nombreProducto, double precioUnitario, int cantidad) {
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return precioUnitario * cantidad;
    }

    // ===== Persistencia =====
    // Formato: P01,Arroz,2500.0,2

    public String aTexto() {
        return codigoProducto + "," + nombreProducto + "," + precioUnitario + "," + cantidad;
    }

    public static DetalleVenta desdeTexto(String texto) {
        String[] partes = texto.split(",");
        return new DetalleVenta(
                partes[0],
                partes[1],
                Double.parseDouble(partes[2]),
                Integer.parseInt(partes[3])
        );
    }
}
