package proyecto.com.proyectobasesdedatos;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.utilidades.OpcionMenu;
import proyecto.com.proyectobasesdedatos.utilidades.RecursosVisuales;

public class PlaceholderController implements Controlador {
    @FXML
    public ImageView imgApartado;

    @FXML
    public Label lbltexto;

    public void setContenido(OpcionMenu opcion, String texto){
        lbltexto.setText(texto);
        imgApartado.setImage(RecursosVisuales.getImagenOpciones(opcion));
    }

}
