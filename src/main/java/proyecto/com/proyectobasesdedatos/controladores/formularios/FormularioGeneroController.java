package proyecto.com.proyectobasesdedatos.controladores.formularios;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.Formulario;
import proyecto.com.proyectobasesdedatos.modelos.Genero;
import proyecto.com.proyectobasesdedatos.servicios.ServicioGeneros;
import proyecto.com.proyectobasesdedatos.utilidades.Modalidad;
import proyecto.com.proyectobasesdedatos.utilidades.RecursosVisuales;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

public class FormularioGeneroController implements Formulario, Controlador {
    ServicioGeneros serv = new ServicioGeneros();

    @FXML
    public Button btnRegistrar;

    @FXML
    public ImageView imgICono;

    @FXML
    public TextField txtNombre;

    private Stage stage;

    private Genero genero;

    private Modalidad modalidad;

    @Override
    public void setModalidad(Modalidad mod){
        modalidad = mod;
        if(modalidad.equals(Modalidad.ACTUALIZAR)){
            btnRegistrar.setText("Modificar");
            imgICono.setImage(RecursosVisuales.cargarImagen("modificar.png"));
        }
    }

    @Override
    public boolean validar() {
        if(txtNombre.getText().trim().isEmpty()){
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("El campo de nombre es obligatorio.").show();
            return false;
        }

        return true;
    }

    public void setGenero(Genero gen){
        if(gen != null){
            genero = gen;
            cargarGenero();
        }
        else{
            genero = new Genero();
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

    private void cargarGenero(){
        txtNombre.setText(genero.getNombre());
    }

    public void btnRegistrarClick(){
        if(validar()){
            asignar();
            if(modalidad.equals(Modalidad.INSERTAR)){
                serv.crear(genero);
                AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("Registro Agregado Exitosamente.").show();
            }
            else if(modalidad.equals(Modalidad.ACTUALIZAR)){
                serv.actualizar(genero);
                AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("Registro Actualizado Exitosamente.").show();
            }
            else if(modalidad.equals(Modalidad.OPERACION_EXTERNA)){
                serv.crear(genero);
                AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("Registro Agregado Exitosamente.").show();
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
        genero.setNombre(txtNombre.getText());
    }
}
