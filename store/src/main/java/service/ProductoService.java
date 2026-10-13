package service;

import model.Producto;
import util.FileUtil;
import java.util.*;

public class ProductoService {

    private final String FILE = "data/productos.txt";

    public void crear(Producto p) {
        List<String> data = FileUtil.readFile(FILE);
        data.add(p.toString());
        FileUtil.writeFile(FILE, data);
    }

    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        for (String line : FileUtil.readFile(FILE)) {
            productos.add(Producto.fromString(line));
        }
        return productos;
    }

    public void eliminar(int id) {
        List<String> data = FileUtil.readFile(FILE);
        data.removeIf(line -> line.startsWith(id + ","));
        FileUtil.writeFile(FILE, data);
    }

    public void actualizar(int id, String nombre, double precio) {
        List<String> data = FileUtil.readFile(FILE);
        List<String> updated = new ArrayList<>();

        for (String line : data) {
            Producto p = Producto.fromString(line);
            if (p.getId() == id) {
                p.setNombre(nombre);
                p.setPrecio(precio);
            }
            updated.add(p.toString());
        }

        FileUtil.writeFile(FILE, updated);
    }
}