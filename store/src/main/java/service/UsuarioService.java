package service;

import model.Usuario;
import util.FileUtil;
import java.util.*;

public class UsuarioService {

    private final String FILE = "data/usuarios.txt";

    public void crearUsuario(Usuario u) {
        List<String> data = FileUtil.readFile(FILE);
        data.add(u.toString());
        FileUtil.writeFile(FILE, data);
    }

    public List<Usuario> listar() {
        List<Usuario> usuarios = new ArrayList<>();
        for (String line : FileUtil.readFile(FILE)) {
            usuarios.add(Usuario.fromString(line));
        }
        return usuarios;
    }

    public void eliminar(int id) {
        List<Usuario> usuarios = new ArrayList<>();

        for (String line : FileUtil.readFile(FILE)) {
            usuarios.add(Usuario.fromString(line));
        }

        usuarios.removeIf(u -> u.getId() == id);

        List<String> data = new ArrayList<>();
        for (Usuario u : usuarios) {
            data.add(u.toString());
        }

        FileUtil.writeFile(FILE, data);
    }

    public void actualizar(int id, String nombre, String email) {
        List<String> data = FileUtil.readFile(FILE);
        List<String> updated = new ArrayList<>();

        for (String line : data) {
            Usuario u = Usuario.fromString(line);
            if (u.getId() == id) {
                u.setNombre(nombre);
                u.setEmail(email);
            }
            updated.add(u.toString());
        }

        FileUtil.writeFile(FILE, updated);
    }
}