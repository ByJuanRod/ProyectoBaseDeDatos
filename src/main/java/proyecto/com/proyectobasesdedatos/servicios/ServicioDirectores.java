package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.modelos.Director;

public class ServicioDirectores implements Servicio<Director> {
    @Override
    public ObservableList<Director> consultar() {
        return null;
    }

    @Override
    public boolean crear(Director entidad) {
        return false;
    }

    @Override
    public boolean actualizar(Director entidad) {
        return false;
    }

    @Override
    public boolean eliminar(Director entidad) {
        return false;
    }

    @Override
    public Director buscar(int codigo) {
        return null;
    }

    @Override
    public void cargar() {

    }
}
