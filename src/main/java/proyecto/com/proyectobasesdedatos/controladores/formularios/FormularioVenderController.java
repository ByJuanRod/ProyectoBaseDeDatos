package proyecto.com.proyectobasesdedatos.controladores.formularios;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormularioVenderController {
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public TextField txtCliente, txtMonto;

    @FXML
    public Label lblMonto,lblDescuento;

    public void btnSeleccionarClick(){

    }

    public void btnSeleccionarBoletoClick(){

    }

    public void btnFacturarClick(){

    }

    public void btnCerrarClick(){
        stage.close();
    }

}
