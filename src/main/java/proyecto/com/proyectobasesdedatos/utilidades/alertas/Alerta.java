package proyecto.com.proyectobasesdedatos.utilidades.alertas;

import javafx.scene.control.Alert;

public interface Alerta {
    Alert crearAlerta(String titulo, String texto);
    Alert crearAlerta(String texto);
}
