package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;

public interface Servicio<T> {
    ObservableList<T> consultar();
}
