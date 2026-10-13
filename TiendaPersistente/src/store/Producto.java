package store;

public class Producto {
    private String codigo;
    private String nombre;
    private double precio;
    private int cantidad;

    public Producto(String codigo, String nombre, double precio, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void reducirStock(int cantidadVendida) {
        this.cantidad -= cantidadVendida;
    }

    public void aumentarStock(int cantidadNueva) {
        this.cantidad += cantidadNueva;
    }

    public void mostrarInfo() {
        System.out.println("Código: " + codigo);
        System.out.println("Nombre: " + nombre);
        System.out.println("Precio: $" + precio);
        System.out.println("Cantidad disponible: " + cantidad);
    }

    // ===== Persistencia =====
    // Cada producto se guarda como una línea del archivo productos.txt:
    // P01;Arroz;2500.0;50

    public String aLinea() {
        return codigo + ";" + nombre + ";" + precio + ";" + cantidad;
    }

    public static Producto desdeLinea(String linea) {
        String[] partes = linea.split(";");
        return new Producto(
                partes[0],
                partes[1],
                Double.parseDouble(partes[2]),
                Integer.parseInt(partes[3])
        );
    }
}
