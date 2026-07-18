package proyecto.com.proyectobasesdedatos.controladores;

import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.utilidades.Modalidad;

public interface Formulario {
    void limpiar();
    void cerrar();
    void setStage(Stage stage);
    void setModalidad(Modalidad modalidad);
    boolean validar();
    void asignar();
}
