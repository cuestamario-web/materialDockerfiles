package store;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static Tienda tienda;
    private static Persistencia persistencia;

    public static void main(String[] args) {

        tienda = new Tienda("Tienda Don Mario");
        persistencia = new Persistencia();

        // 1. Al arrancar se cargan los datos que haya en los .txt
        persistencia.cargar(tienda);

        System.out.println("==========================================");
        System.out.println("  " + tienda.getNombre());
        System.out.println("==========================================");
        System.out.println("Carpeta de datos: " + persistencia.getCarpeta());
        System.out.println("Datos cargados -> productos: " + tienda.getInventario().getProductos().size()
                + " | clientes: " + tienda.getClientes().size()
                + " | ventas: " + tienda.getVentas().size());

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opción: ");
            System.out.println();

            switch (opcion) {
                case 1 -> registrarProducto();
                case 2 -> tienda.getInventario().mostrarProductos();
                case 3 -> reabastecerProducto();
                case 4 -> aplicarAumento();
                case 5 -> registrarCliente();
                case 6 -> tienda.mostrarClientes();
                case 7 -> registrarVenta();
                case 8 -> tienda.mostrarVentas();
                case 0 -> System.out.println("¡Hasta luego! Los datos quedaron guardados en " + persistencia.getCarpeta());
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("--------------- MENÚ ---------------");
        System.out.println(" PRODUCTOS");
        System.out.println("  1. Registrar producto");
        System.out.println("  2. Listar productos");
        System.out.println("  3. Reabastecer producto (aumentar stock)");
        System.out.println("  4. Aplicar aumento general de precios");
        System.out.println(" CLIENTES");
        System.out.println("  5. Registrar cliente");
        System.out.println("  6. Listar clientes");
        System.out.println(" VENTAS");
        System.out.println("  7. Registrar venta");
        System.out.println("  8. Listar ventas");
        System.out.println();
        System.out.println("  0. Salir");
        System.out.println("------------------------------------");
    }

    // ====================== PRODUCTOS ======================

    private static void registrarProducto() {
        System.out.println(">> Nuevo producto");
        String codigo = leerTexto("Código (ej. P01): ").toUpperCase();
        if (tienda.getInventario().buscarProducto(codigo) != null) {
            System.out.println("Ya existe un producto con el código " + codigo + ".");
            return;
        }
        String nombre = leerTexto("Nombre: ");
        double precio = leerDecimal("Precio: ");
        int cantidad = leerEntero("Cantidad inicial: ");

        tienda.getInventario().agregarProducto(new Producto(codigo, nombre, precio, cantidad));
        persistencia.guardarProductos(tienda.getInventario().getProductos());
        System.out.println("Producto registrado y guardado en productos.txt");
    }

    private static void reabastecerProducto() {
        String codigo = leerTexto("Código del producto: ");
        Producto p = tienda.getInventario().buscarProducto(codigo);
        if (p == null) {
            System.out.println("No existe el producto " + codigo + ".");
            return;
        }
        int unidades = leerEntero("Unidades que llegan: ");
        if (unidades <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return;
        }
        p.aumentarStock(unidades);
        persistencia.guardarProductos(tienda.getInventario().getProductos());
        System.out.println("Nuevo stock de " + p.getNombre() + ": " + p.getCantidad());
    }

    private static void aplicarAumento() {
        double porcentaje = leerDecimal("Porcentaje de aumento (ej. 10): ");
        try {
            tienda.getInventario().aplicarAumentoGeneral(porcentaje);
            persistencia.guardarProductos(tienda.getInventario().getProductos());
            System.out.println("Precios actualizados.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    // ====================== CLIENTES ======================

    private static void registrarCliente() {
        System.out.println(">> Nuevo cliente");
        String id = leerTexto("Identificación (ej. C01 o cédula): ").toUpperCase();
        if (tienda.buscarCliente(id) != null) {
            System.out.println("Ya existe un cliente con la identificación " + id + ".");
            return;
        }
        String nombre = leerTexto("Nombre: ");
        String telefono = leerTexto("Teléfono: ");

        tienda.registrarCliente(new Cliente(id, nombre, telefono));
        persistencia.guardarClientes(tienda.getClientes());
        System.out.println("Cliente registrado y guardado en clientes.txt");
    }

    // ====================== VENTAS ======================

    private static void registrarVenta() {
        if (tienda.getClientes().isEmpty() || tienda.getInventario().getProductos().isEmpty()) {
            System.out.println("Para vender necesita al menos un cliente y un producto registrados.");
            return;
        }

        String idCliente = leerTexto("Identificación del cliente: ");
        Cliente cliente = tienda.buscarCliente(idCliente);
        if (cliente == null) {
            System.out.println("No existe el cliente " + idCliente + ".");
            return;
        }

        Venta venta = new Venta(tienda.siguienteCodigoVenta(), cliente, LocalDate.now().toString());

        System.out.println("Agregue productos. Deje el código vacío y presione Enter para terminar.");
        while (true) {
            System.out.print("Código del producto: ");
            String codigo = leerLinea().trim();
            if (codigo.isEmpty()) {
                break;
            }
            Producto p = tienda.getInventario().buscarProducto(codigo);
            if (p == null) {
                System.out.println("  No existe ese producto.");
                continue;
            }
            int cantidad = leerEntero("Cantidad (disponibles " + p.getCantidad() + "): ");
            if (venta.agregarProducto(p, cantidad)) {
                System.out.println("  Agregado: " + p.getNombre() + " x" + cantidad);
            } else {
                System.out.println("  Cantidad no válida o stock insuficiente.");
            }
        }

        if (venta.getDetalles().isEmpty()) {
            System.out.println("La venta no tiene productos; no se registró.");
            return;
        }

        tienda.registrarVenta(venta);
        // Una venta cambia dos archivos: las ventas y el stock de los productos
        persistencia.guardarVentas(tienda.getVentas());
        persistencia.guardarProductos(tienda.getInventario().getProductos());

        System.out.println();
        venta.mostrarDetalle();
        System.out.println("Venta guardada en ventas.txt (y stock actualizado en productos.txt)");
    }

    // ====================== LECTURA DE DATOS ======================

    /**
     * Lee una línea del teclado. Si no hay entrada (por ejemplo, el contenedor
     * se ejecutó sin -it), termina el programa con un mensaje claro.
     */
    private static String leerLinea() {
        if (!sc.hasNextLine()) {
            System.out.println();
            System.out.println("No hay entrada por teclado. Si está usando Docker, ejecute el contenedor con: docker run -it ...");
            System.exit(0);
        }
        return sc.nextLine();
    }

    private static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = leerLinea().trim();
            if (texto.isEmpty()) {
                System.out.println("  El valor no puede estar vacío.");
            } else if (texto.contains(";") || texto.contains(",") || texto.contains("|")) {
                // Esos caracteres son los separadores de los archivos .txt
                System.out.println("  No use los caracteres ;  ,  |");
            } else {
                return texto;
            }
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(leerLinea().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Debe escribir un número entero.");
            }
        }
    }

    private static double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(leerLinea().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("  Debe escribir un número (ej. 2500 o 2500.50).");
            }
        }
    }
}
