package store;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Se encarga de leer y escribir los archivos .txt de la carpeta de datos.
 *
 * La carpeta es "data" (relativa a donde se ejecuta el programa), salvo que
 * exista la variable de entorno DATA_DIR. Dentro del contenedor el programa
 * corre en /app/src, así que la carpeta es /app/src/data: el VOLUME del Dockerfile.
 *
 * Los archivos NO se crean al arrancar: cada .txt aparece la primera vez que
 * se guarda algo de ese tipo (primer producto, primer cliente, primera venta).
 */
public class Persistencia {

    private final Path carpeta;
    private final Path archivoProductos;
    private final Path archivoClientes;
    private final Path archivoVentas;

    public Persistencia() {
        String dir = System.getenv("DATA_DIR");
        if (dir == null || dir.isBlank()) {
            dir = "data";
        }
        carpeta = Paths.get(dir).toAbsolutePath();
        archivoProductos = carpeta.resolve("productos.txt");
        archivoClientes = carpeta.resolve("clientes.txt");
        archivoVentas = carpeta.resolve("ventas.txt");
    }

    public Path getCarpeta() {
        return carpeta;
    }

    // ===================== Cargar =====================

    /** Lee todo lo guardado y lo mete en la tienda. Clientes antes que ventas. */
    public void cargar(Tienda tienda) {
        for (String linea : leerLineas(archivoProductos)) {
            tienda.getInventario().agregarProducto(Producto.desdeLinea(linea));
        }
        for (String linea : leerLineas(archivoClientes)) {
            tienda.registrarCliente(Cliente.desdeLinea(linea));
        }
        for (String linea : leerLineas(archivoVentas)) {
            tienda.registrarVenta(Venta.desdeLinea(linea, tienda));
        }
    }

    // ===================== Guardar =====================
    // Se reescribe el archivo completo cada vez: es lo más sencillo y,
    // para pocos registros, no hay problema de rendimiento.

    public void guardarProductos(List<Producto> productos) {
        List<String> lineas = new ArrayList<>();
        for (Producto p : productos) {
            lineas.add(p.aLinea());
        }
        escribirLineas(archivoProductos, lineas);
    }

    public void guardarClientes(List<Cliente> clientes) {
        List<String> lineas = new ArrayList<>();
        for (Cliente c : clientes) {
            lineas.add(c.aLinea());
        }
        escribirLineas(archivoClientes, lineas);
    }

    public void guardarVentas(List<Venta> ventas) {
        List<String> lineas = new ArrayList<>();
        for (Venta v : ventas) {
            lineas.add(v.aLinea());
        }
        escribirLineas(archivoVentas, lineas);
    }

    // ===================== Utilidades =====================

    private List<String> leerLineas(Path archivo) {
        // Si el archivo todavía no existe es porque no se ha registrado nada de ese tipo
        if (Files.notExists(archivo)) {
            return new ArrayList<>();
        }
        try {
            List<String> lineas = new ArrayList<>();
            for (String linea : Files.readAllLines(archivo, StandardCharsets.UTF_8)) {
                if (!linea.isBlank()) {
                    lineas.add(linea.trim());
                }
            }
            return lineas;
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer " + archivo, e);
        }
    }

    private void escribirLineas(Path archivo, List<String> lineas) {
        try {
            // En Docker la carpeta ya existe (VOLUME); esto es por si se ejecuta en el PC sin ella
            Files.createDirectories(carpeta);
            // Files.write crea el archivo si no existe y lo reemplaza si ya existe
            Files.write(archivo, lineas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo escribir " + archivo, e);
        }
    }
}
