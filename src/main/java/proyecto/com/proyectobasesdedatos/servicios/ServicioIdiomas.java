package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.modelos.Idioma;

public class ServicioIdiomas implements Servicio<Idioma>{
    @Override
    public ObservableList<Idioma> consultar() {
        return null;
    }

    @Override
    public boolean crear(Idioma entidad) {
        return false;
    }

    @Override
    public boolean actualizar(Idioma entidad) {
        return false;
    }

    @Override
    public boolean eliminar(Idioma entidad) {
        return false;
    }

    @Override
    public Idioma buscar(int codigo) {
        return null;
    }

    @Override
    public void cargar() {

    }
}
