package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.modelos.Ciudad;

public class ServicioCiudades implements Servicio<Ciudad>{
    @Override
    public ObservableList<Ciudad> consultar() {
        return null;
    }

    @Override
    public boolean crear(Ciudad entidad) {
        return false;
    }

    @Override
    public boolean actualizar(Ciudad entidad) {
        return false;
    }

    @Override
    public boolean eliminar(Ciudad entidad) {
        return false;
    }

    @Override
    public Ciudad buscar(int codigo) {
        return null;
    }

    @Override
    public void cargar() {

    }
}
