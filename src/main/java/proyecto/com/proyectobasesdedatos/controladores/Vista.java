package proyecto.com.proyectobasesdedatos.controladores;

import javafx.scene.layout.AnchorPane;
import proyecto.com.proyectobasesdedatos.utilidades.Modalidad;

public interface Vista<T> {
    void filtrar();
    void cargar();
    void configurarColumnas();
    void crearPantalla(Modalidad modalidad, T objeto);
    AnchorPane setPlaceholder();
}
