package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.modelos.Venta;

public class ServicioVentas implements Servicio<Venta> {
    @Override
    public ObservableList<Venta> consultar() {
        return null;
    }

    @Override
    public boolean crear(Venta entidad) {
        return false;
    }

    @Override
    public boolean actualizar(Venta entidad) {
        return false;
    }

    @Override
    public boolean eliminar(Venta entidad) {
        return false;
    }

    @Override
    public Venta buscar(int codigo) {
        return null;
    }

    @Override
    public void cargar() {

    }
}
