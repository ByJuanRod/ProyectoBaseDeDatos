package proyecto.com.proyectobasesdedatos.utilidades.alertas;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertaPregunta implements Alerta{
    @Override
    public Alert crearAlerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("Confirmación");
        alert.setContentText(texto);
        alert.setResizable(false);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.OK);
        return alert;
    }

    @Override
    public Alert crearAlerta(String titulo, String texto) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(titulo);
        alert.setContentText(texto);
        alert.setResizable(false);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ButtonType.CANCEL);
        alert.getButtonTypes().add(ButtonType.OK);
        return alert;
    }
}
