package proyecto.com.proyectobasesdedatos.controladores.formularios;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.selectores.SelectorCiudadController;
import proyecto.com.proyectobasesdedatos.modelos.Ciudad;
import proyecto.com.proyectobasesdedatos.servicios.ServicioCiudades;
import proyecto.com.proyectobasesdedatos.utilidades.Modalidad;
import proyecto.com.proyectobasesdedatos.utilidades.RecursosVisuales;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

public class FormularioCiudadController implements Formulario, Controlador {
    ServicioCiudades serv = new ServicioCiudades();

    @FXML
    public Button btnRegistrar;

    @FXML
    public ImageView imgICono;

    @FXML
    public TextField txtNombre, txtCodigoPostal;

    private Stage stage;

    private Ciudad ciudad;

    private Modalidad modalidad;

    private SelectorCiudadController controlador;

    @Override
    public void setModalidad(Modalidad mod){
        modalidad = mod;
        if(modalidad.equals(Modalidad.ACTUALIZAR)){
            btnRegistrar.setText("Modificar");
            imgICono.setImage(RecursosVisuales.cargarImagen("modificar.png"));
        }
    }

    public void setControlador(SelectorCiudadController controlador){
        this.controlador = controlador;
    }

    @Override
    public boolean validar() {
        if(txtNombre.getText().trim().isEmpty()){
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("El campo de nombre es obligatorio.").show();
            return false;
        }

        if(txtCodigoPostal.getText().trim().isEmpty()){
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("El campo de código postal es obligatorio.").show();
            return false;
        }

        return true;
    }

    public void setCiudad(Ciudad ciud){
        if(ciud != null){
            ciudad = ciud;
            cargarCiudad();
        }
        else{
            ciudad = new Ciudad();
        }
    }

    @Override
    public void limpiar() {
        txtNombre.setText("");
        txtCodigoPostal.setText("");
    }

    @Override
    public void cerrar() {
        stage.close();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void cargarCiudad(){
        txtNombre.setText(ciudad.getNombre());
        txtCodigoPostal.setText(ciudad.getCodigoPostal());
    }

    public void btnRegistrarClick(){
        if(validar()){
            asignar();
            if(modalidad.equals(Modalidad.INSERTAR)){
                serv.crear(ciudad);
                AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("Registro Agregado Exitosamente.").show();
            }
            else if(modalidad.equals(Modalidad.ACTUALIZAR)){
                serv.actualizar(ciudad);
                AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("Registro Actualizado Exitosamente.").show();
            }
            else if(modalidad.equals(Modalidad.OPERACION_EXTERNA)){
                serv.crear(ciudad);
                AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("Registro Agregado Exitosamente.").show();

                if(controlador != null){
                    controlador.asignarCiudadCreada(ciudad);
                }

                stage.close();
            }
        }
    }

    public void btnLimpiarClick(){
        limpiar();
    }

    public void btnCerrarClick(){
        cerrar();
    }

    @Override
    public void asignar(){
        if(ciudad == null){
            ciudad = new Ciudad();
        }
        ciudad.setNombre(txtNombre.getText());
        ciudad.setCodigoPostal(txtCodigoPostal.getText());
    }
}