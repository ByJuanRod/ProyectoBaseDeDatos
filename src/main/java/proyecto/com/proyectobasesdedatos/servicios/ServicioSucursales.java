package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.modelos.Sucursal;

public class ServicioSucursales implements Servicio<Sucursal>{
    @Override
    public ObservableList<Sucursal> consultar() {
        return null;
    }

    @Override
    public boolean crear(Sucursal entidad) {
        return false;
    }

    @Override
    public boolean actualizar(Sucursal entidad) {
        return false;
    }

    @Override
    public boolean eliminar(Sucursal entidad) {
        return false;
    }

    @Override
    public Sucursal buscar(int codigo) {
        return null;
    }

    @Override
    public void cargar() {

    }
}
