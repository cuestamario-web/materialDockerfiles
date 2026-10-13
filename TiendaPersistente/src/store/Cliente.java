package store;

public class Cliente {
    private String id;
    private String nombre;
    private String telefono;

    public Cliente(String id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void mostrarInfo() {
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Teléfono: " + telefono);
    }

    // ===== Persistencia =====
    // Cada cliente se guarda como una línea del archivo clientes.txt:
    // C01;Juan Pérez;3001234567

    public String aLinea() {
        return id + ";" + nombre + ";" + telefono;
    }

    public static Cliente desdeLinea(String linea) {
        String[] partes = linea.split(";");
        return new Cliente(partes[0], partes[1], partes[2]);
    }
}
