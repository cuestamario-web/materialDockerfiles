package store;

public class Main {

    public static void main(String[] args) {

        Tienda tienda = new Tienda("Tienda Don Mario");

        // Crear productos
        Producto arroz = new Producto("P01", "Arroz", 2500, 50);
        Producto leche = new Producto("P02", "Leche", 3000, 30);

        // Agregar productos al inventario
        tienda.getInventario().agregarProducto(arroz);
        tienda.getInventario().agregarProducto(leche);

        // Registrar cliente
        Cliente cliente1 = new Cliente("C01", "Juan Pérez", "3001234567");
        tienda.registrarCliente(cliente1);

        // Crear venta
        Venta venta1 = new Venta(cliente1);
        venta1.agregarProducto(arroz, 2);
        venta1.agregarProducto(leche, 1);

        tienda.registrarVenta(venta1);

        // Mostrar información
        tienda.getInventario().mostrarProductos();
        tienda.mostrarVentas();
    }
    // Método que luego podrías modificar en otra rama
    public static double calcularIVA(double valor) {
        return valor * 0.19;
    }

    // Método para futuras mejoras
    public static double aplicarDescuento(double valor, double porcentaje) {
        return valor - (valor * porcentaje / 100);
    }
}

