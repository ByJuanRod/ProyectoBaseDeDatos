package proyecto.com.proyectobasesdedatos.controladores.componentes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;

public class VistaBoletosController implements Controlador {

    @FXML
    public Label lblCliente;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public ScrollPane scrp;

    @FXML
    public VBox pnlContenedor;


    public void btnCerrarClick(){
        stage.close();
    }
}
