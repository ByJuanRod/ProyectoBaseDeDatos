package proyecto.com.proyectobasesdedatos.controladores.formularios;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.Formulario;

public class FormularioClienteController implements Formulario {
    @FXML
    public Button btnRegistrar, btnLimpiar,  btnCerrar;

    @FXML
    public TextField txtNombres, txtApellidos, txtTelefono, txtCorreo;

    private Stage stage;

    @Override
    public void limpiar() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
    }

    @Override
    public void cerrar() {
        stage.close();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void btnRegistrarClick(){


    }

    public void btnLimpiarClick(){
        limpiar();
    }

    public void btnCerrarClick(){
        cerrar();
    }

}
