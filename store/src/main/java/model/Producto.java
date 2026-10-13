package model;

public class Producto {
    private int id;
    private String nombre;
    private double precio;

    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public String toString() {
        return id + "," + nombre + "," + precio;
    }

    public static Producto fromString(String data) {
        String[] parts = data.split(",");
        return new Producto(
                Integer.parseInt(parts[0]),
                parts[1],
                Double.parseDouble(parts[2])
        );
    }
}