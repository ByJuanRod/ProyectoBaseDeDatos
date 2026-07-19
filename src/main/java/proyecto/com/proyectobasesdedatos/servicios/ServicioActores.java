package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.modelos.Actor;

public class ServicioActores implements Servicio<Actor>{
    @Override
    public ObservableList<Actor> consultar() {
        return null;
    }

    @Override
    public boolean crear(Actor entidad) {
        return false;
    }

    @Override
    public boolean actualizar(Actor entidad) {
        return false;
    }

    @Override
    public boolean eliminar(Actor entidad) {
        return false;
    }

    @Override
    public Actor buscar(int codigo) {
        return null;
    }

    @Override
    public void cargar() {

    }
}
