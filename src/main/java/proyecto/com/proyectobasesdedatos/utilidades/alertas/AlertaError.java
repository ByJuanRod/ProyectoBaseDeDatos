package proyecto.com.proyectobasesdedatos.utilidades.alertas;

import javafx.scene.control.Alert;

public class AlertaError implements Alerta{
    @Override
    public Alert crearAlerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(texto);
        alert.setResizable(false);
        return alert;
    }

    @Override
    public Alert crearAlerta(String titulo, String texto) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(titulo);
        alert.setContentText(texto);
        alert.setResizable(false);
        return alert;
    }
}
