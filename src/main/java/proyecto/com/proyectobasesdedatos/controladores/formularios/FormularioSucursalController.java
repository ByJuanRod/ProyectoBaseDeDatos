package proyecto.com.proyectobasesdedatos.controladores.formularios;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.selectores.SelectorCiudadController;
import proyecto.com.proyectobasesdedatos.modelos.Ciudad;
import proyecto.com.proyectobasesdedatos.modelos.Sucursal;
import proyecto.com.proyectobasesdedatos.servicios.ServicioSucursales;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

import java.awt.*;

public class FormularioSucursalController implements Formulario, Controlador {
    ServicioSucursales serv = new ServicioSucursales();

    @FXML
    public Button btnRegistrar;

    @FXML
    public ImageView imgICono;

    @FXML
    public TextField txtNombre, txtTelefono, txtCorreo, txtDireccion, txtCiudad;

    private Stage stage;

    private Sucursal sucursal;


    private Modalidad modalidad;

    @FXML
    public void initialize() {
        txtCiudad.setEditable(false);
    }

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

    public void setSucursal(Sucursal suc){
        if(suc != null){
            sucursal = suc;
            cargarSucursal();
        }
        else{
            sucursal = new Sucursal();
        }
    }

    @Override
    public void limpiar() {
        txtNombre.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtCiudad.setText("");
        sucursal.setCiudad(null);
        txtTelefono.setText("");
    }

    @Override
    public void cerrar() {
        stage.close();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void cargarSucursal(){
        txtNombre.setText(sucursal.getNombre());
        txtTelefono.setText(sucursal.getTelefono());
        txtDireccion.setText(sucursal.getDireccion());
        txtCorreo.setText(sucursal.getCorreo());
        txtCiudad.setText(sucursal.getCiudad().getCiudadFormateada());
    }

    public void btnRegistrarClick(){
        if(validar()){
            asignar();
            if(modalidad.equals(Modalidad.INSERTAR)){
                serv.crear(sucursal);
                AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("Registro Agregado Exitosamente.").show();
            }
            else if(modalidad.equals(Modalidad.ACTUALIZAR)){
                serv.actualizar(sucursal);
                AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("Registro Actualizado Exitosamente.").show();
            }
            else if(modalidad.equals(Modalidad.OPERACION_EXTERNA)){
                serv.crear(sucursal);
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

    public void btnSeleccionarClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido(Selectores.CIUDADES.getArchivo())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo("Selector de Ciudad")
                .setSize(new Dimension(780,600))
                .construirPantalla();

        SelectorCiudadController controlador = (SelectorCiudadController) pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setFormularioSucursal(this);
        pnt.pantalla().show();
    }

    public void setCiudadSeleccionada(Ciudad ciudad){
        if(ciudad != null){
            if(sucursal == null){
                sucursal = new Sucursal();
            }

            sucursal.setCiudad(ciudad);
            txtCiudad.setText(ciudad.getCiudadFormateada());
        }
    }

    @Override
    public void asignar(){
        sucursal.setNombre(txtNombre.getText());
        sucursal.setCorreo(txtCorreo.getText());
        sucursal.setTelefono(txtTelefono.getText());
        sucursal.setDireccion(txtDireccion.getText());
    }
}