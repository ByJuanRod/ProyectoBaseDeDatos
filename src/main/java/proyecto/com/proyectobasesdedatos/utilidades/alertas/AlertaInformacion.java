package proyecto.com.proyectobasesdedatos.utilidades.alertas;

import javafx.scene.control.Alert;

public class AlertaInformacion implements Alerta{
    @Override
    public Alert crearAlerta(String texto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Información");
        alert.setContentText(texto);
        alert.setResizable(false);
        return alert;
    }

    @Override
    public Alert crearAlerta(String titulo, String texto) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(titulo);
        alert.setContentText(texto);
        alert.setResizable(false);
        return alert;
    }
}
