package proyecto.com.proyectobasesdedatos.controladores.formularios;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.selectores.SelectorPaisController;
import proyecto.com.proyectobasesdedatos.modelos.Pais;
import proyecto.com.proyectobasesdedatos.servicios.ServicioPaises;
import proyecto.com.proyectobasesdedatos.utilidades.Modalidad;
import proyecto.com.proyectobasesdedatos.utilidades.RecursosVisuales;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

public class FormularioPaisController implements Formulario, Controlador {
    ServicioPaises serv = new ServicioPaises();

    @FXML
    public Button btnRegistrar;

    @FXML
    public ImageView imgICono;

    @FXML
    public TextField txtNombre;

    private Stage stage;

    private Pais pais;

    private Modalidad modalidad;

    private SelectorPaisController controlador;

    @Override
    public void setModalidad(Modalidad mod){
        modalidad = mod;
        if(modalidad.equals(Modalidad.ACTUALIZAR)){
            btnRegistrar.setText("Modificar");
            imgICono.setImage(RecursosVisuales.cargarImagen("modificar.png"));
        }
    }

    public void setControlador(SelectorPaisController controlador){
        this.controlador = controlador;
    }

    @Override
    public boolean validar() {
        if(txtNombre.getText().trim().isEmpty()){
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("El campo de nombre es obligatorio.").show();
            return false;
        }

        return true;
    }

    public void setPais(Pais pa){
        if(pa != null){
            pais = pa;
            cargarPais();
        }
        else{
            pais = new Pais();
        }
    }

    @Override
    public void limpiar() {
        txtNombre.setText("");
    }

    @Override
    public void cerrar() {
        stage.close();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void cargarPais(){
        txtNombre.setText(pais.getNombre());
    }

    public void btnRegistrarClick(){
        if(validar()){
            asignar();
            if(modalidad.equals(Modalidad.INSERTAR)){
                serv.crear(pais);
                AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("Registro Agregado Exitosamente.").show();
            }
            else if(modalidad.equals(Modalidad.ACTUALIZAR)){
                serv.actualizar(pais);
                AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("Registro Actualizado Exitosamente.").show();
            }
            else if(modalidad.equals(Modalidad.OPERACION_EXTERNA)){
                serv.crear(pais);
                AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("Registro Agregado Exitosamente.").show();

                if(controlador != null){
                    controlador.asignarPaisCreado(pais);
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
        if(pais == null){
            pais = new Pais();
        }
        pais.setNombre(txtNombre.getText());
    }
}