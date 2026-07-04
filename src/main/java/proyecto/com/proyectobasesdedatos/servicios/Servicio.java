package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;

public interface Servicio<T> {
    ObservableList<T> consultar();
    boolean crear(T entidad);
    boolean actualizar(T entidad);
    boolean eliminar(T entidad);
    T buscar(int codigo);
}
