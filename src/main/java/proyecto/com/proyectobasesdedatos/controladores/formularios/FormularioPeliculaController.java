package proyecto.com.proyectobasesdedatos.controladores.formularios;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.Formulario;
import proyecto.com.proyectobasesdedatos.modelos.ClasificacionPelicula;
import proyecto.com.proyectobasesdedatos.utilidades.ConfiguradorSpinners;
import proyecto.com.proyectobasesdedatos.utilidades.Modalidad;

public class FormularioPeliculaController implements Formulario {

    private Stage stage;

    private Modalidad modalidad;

    @FXML
    public TextField txtNombre, txtDirector;

    @FXML
    public Spinner<Integer> spnHoras, spnMinutos;

    @FXML
    public ComboBox<ClasificacionPelicula> cbxClasificacion;

    @FXML
    public void initialize(){
        cargarClasificaciones();
        ConfiguradorSpinners.configurarSpinnerNumerico(spnMinutos,0,60,0);
        ConfiguradorSpinners.configurarSpinnerNumerico(spnHoras,0,24, 0);
    }

    private void cargarClasificaciones(){
        for(ClasificacionPelicula cp : ClasificacionPelicula.values())
            cbxClasificacion.getItems().add(cp);

    }

    @Override
    public void limpiar() {
        txtNombre.setText("");
        txtDirector.setText("");
        spnHoras.getValueFactory().setValue(0);
        spnMinutos.getValueFactory().setValue(0);
        cbxClasificacion.getSelectionModel().select(0);
    }

    @Override
    public void cerrar() {
        stage.close();
    }

    @Override
    public void setStage(Stage st){
        stage = st;
    }

    @Override
    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    @Override
    public boolean validar() {
        return false;
    }

    public void btnCerrarClick(){
        cerrar();
    }

    public void btnLimpiarClick(){
        limpiar();
    }

    public void btnRegistrarClick(){

    }
}
