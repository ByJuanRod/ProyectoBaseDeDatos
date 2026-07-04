package proyecto.com.proyectobasesdedatos.utilidades.alertas;

import javafx.scene.control.Alert;

public class AlertaAdvertencia implements Alerta{
    @Override
    public Alert crearAlerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText("Advertencia");
        alert.setContentText(texto);
        alert.setResizable(false);
        return alert;
    }

    @Override
    public Alert crearAlerta(String titulo, String texto) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(titulo);
        alert.setContentText(texto);
        alert.setResizable(false);
        return alert;
    }
}
