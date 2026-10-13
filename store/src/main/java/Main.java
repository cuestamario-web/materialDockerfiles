import com.google.gson.Gson;
import model.Producto;
import model.Usuario;
import service.ProductoService;
import service.UsuarioService;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        port(getPort());

        Gson gson = new Gson();

        UsuarioService usuarioService = new UsuarioService();
        ProductoService productoService = new ProductoService();

        before((req, res) -> {
            res.type("application/json");
        });

        exception(Exception.class, (e, req, res) -> {

            System.out.println("ERROR DETECTADO:");

            e.printStackTrace();

            res.status(500);

            res.body(gson.toJson("Error interno"));
        });

        get("/health", (req, res) -> {
            return gson.toJson("API funcionando");
        });
        // =========================
        // USUARIOS
        // =========================

        get("/usuarios", (req, res) -> {
            return gson.toJson(usuarioService.listar());
        });

        post("/usuarios", (req, res) -> {

            Usuario usuario = gson.fromJson(req.body(), Usuario.class);

            usuarioService.crearUsuario(usuario);

            res.status(201);

            return gson.toJson(usuario);
        });

        put("/usuarios/:id", (req, res) -> {

            int id = Integer.parseInt(req.params(":id"));

            Usuario usuario = gson.fromJson(req.body(), Usuario.class);

            usuarioService.actualizar(
                    id,
                    usuario.getNombre(),
                    usuario.getEmail()
            );

            return gson.toJson("Usuario actualizado");
        });

        delete("/usuarios/:id", (req, res) -> {

            int id = Integer.parseInt(req.params(":id"));

            usuarioService.eliminar(id);

            return gson.toJson("Usuario eliminado");
        });

        // =========================
        // PRODUCTOS
        // =========================

        get("/productos", (req, res) -> {
            return gson.toJson(productoService.listar());
        });

        post("/productos", (req, res) -> {

            Producto producto = gson.fromJson(req.body(), Producto.class);

            productoService.crear(producto);

            res.status(201);

            return gson.toJson(producto);
        });

        put("/productos/:id", (req, res) -> {

            int id = Integer.parseInt(req.params(":id"));

            Producto producto = gson.fromJson(req.body(), Producto.class);

            productoService.actualizar(
                    id,
                    producto.getNombre(),
                    producto.getPrecio()
            );

            return gson.toJson("Producto actualizado");
        });

        delete("/productos/:id", (req, res) -> {

            int id = Integer.parseInt(req.params(":id"));

            productoService.eliminar(id);

            return gson.toJson("Producto eliminado");
        });

    }

    private static int getPort() {

        String port = System.getenv("PORT");

        if (port != null) {
            return Integer.parseInt(port);
        }

        return 4567;
    }
}