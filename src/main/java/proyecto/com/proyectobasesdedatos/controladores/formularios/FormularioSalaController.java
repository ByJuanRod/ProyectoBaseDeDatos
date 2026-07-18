package proyecto.com.proyectobasesdedatos.controladores.formularios;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.Formulario;
import proyecto.com.proyectobasesdedatos.modelos.Sala;
import proyecto.com.proyectobasesdedatos.servicios.ServicioSalas;
import proyecto.com.proyectobasesdedatos.utilidades.ConfiguradorSpinners;
import proyecto.com.proyectobasesdedatos.utilidades.Modalidad;

public class FormularioSalaController implements Formulario {
    ServicioSalas  ss = new ServicioSalas();

    @FXML
    public TextField txtNombre;

    @FXML
    public Spinner<Integer> spnCapacidad;

    private Stage stage;

    private Sala sala;

    private Modalidad modalidad;

    public void setSala(Sala sala){
        this.sala=sala;

        if(sala!=null){
            cargar();
        }
    }

    private void cargar(){
        txtNombre.setText(sala.getNombre());
        spnCapacidad.getValueFactory().setValue(sala.getCapacidad());
    }

    @FXML
    public void initialize(){
        ConfiguradorSpinners.configurarSpinnerNumerico(spnCapacidad,0,200,0);
    }

    @Override
    public void limpiar() {
        txtNombre.setText("");
        spnCapacidad.getValueFactory().setValue(0);
    }

    @Override
    public void cerrar() {
        stage.close();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    @Override
    public boolean validar() {
        if(txtNombre.getText().trim().isEmpty()){
            return false;
        }
        return true;
    }

    @Override
    public void asignar() {

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
