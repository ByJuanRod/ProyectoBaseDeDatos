package proyecto.com.proyectobasesdedatos.controladores.formularios;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.selectores.SelectorCiudadController;
import proyecto.com.proyectobasesdedatos.modelos.Ciudad;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.servicios.ServicioClientes;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

import java.awt.*;

public class FormularioClienteController implements Formulario, Controlador {
    ServicioClientes serv = new  ServicioClientes();

    @FXML
    public Button btnRegistrar;

    @FXML
    public ImageView imgICono;

    @FXML
    public TextField txtNombres, txtApellidos, txtTelefono, txtCorreo, txtCiudad;

    @FXML
    public DatePicker dpFechaNacimiento;

    @FXML
    public ComboBox<Sexo> cbxSexo;

    private Stage stage;

    private Cliente cliente;

    private Modalidad modalidad;

    @FXML
    public void initialize(){
        cbxSexo.setItems(FXCollections.observableArrayList(Sexo.values()));
        cbxSexo.getSelectionModel().selectFirst();
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

        if(txtNombres.getText().trim().isEmpty()){
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("El campo de nombres es obligatorio.").show();
            return false;
        }

        if(txtApellidos.getText().trim().isEmpty()){
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("El campo de apellidos es obligatorio.").show();
            return false;
        }

        if(txtTelefono.getText().trim().replace("-","").length() != 10) {
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("El campo de teléfono es obligatorio y requiere almenos 11 caracteres.").show();
            return false;
        }

        if(txtCorreo.getText().trim().isEmpty()){
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("El campo de correo es obligatorio.").show();
            return false;
        }

        if(!txtCorreo.getText().trim().matches("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$")){
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("El correo ingresado no tiene un formato válido.").show();
            return false;
        }

        if(dpFechaNacimiento.getValue() == null){
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("Debe seleccionar la fecha de nacimiento.").show();
            return false;
        }

        if(cbxSexo.getValue() == null){
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("Debe seleccionar el sexo.").show();
            return false;
        }

        if(cliente.getCiudad() == null){
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("Debe seleccionar una ciudad.").show();
            return false;
        }

        return true;
    }

    public void setCliente(Cliente clt){

        if(clt != null){
            cliente = clt;
            cargarCliente();
        }
        else{
            cliente = new Cliente();
        }
    }

    @Override
    public void limpiar() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtCiudad.setText("");
        dpFechaNacimiento.setValue(null);
        cbxSexo.setValue(null);

        if(cliente != null){
            cliente.setCiudad(null);
        }
    }

    @Override
    public void cerrar() {
        stage.close();
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void cargarCliente(){
        txtNombres.setText(cliente.getNombres());
        txtApellidos.setText(cliente.getApellidos());
        txtTelefono.setText(cliente.getTelefono());
        txtCorreo.setText(cliente.getCorreo());
        dpFechaNacimiento.setValue(cliente.getFechaNacimiento());
        cbxSexo.setValue(Sexo.getSexo(cliente.getSexo()));

        if(cliente.getCiudad() != null){
            txtCiudad.setText(cliente.getCiudad().getCiudadFormateada());
        }
    }

    public void btnRegistrarClick(){
        if(validar()){
            asignar();
            if(modalidad.equals(Modalidad.INSERTAR)){
                serv.crear(cliente);
                AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("Registro Agregado Exitosamente.").show();
            }
            else if(modalidad.equals(Modalidad.ACTUALIZAR)){
                serv.actualizar(cliente);
                AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("Registro Actualizado Exitosamente.").show();
            }
            else if(modalidad.equals(Modalidad.OPERACION_EXTERNA)){
                serv.crear(cliente);
                AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("Registro Agregado Exitosamente.").show();
            }
        }

    }

    public void btnSeleccionarClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido(Selectores.CIUDADES.getArchivo())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(Selectores.CIUDADES.getTitulo())
                .setSize(Selectores.CIUDADES.getSize())
                .construirPantalla();

        SelectorCiudadController controlador = (SelectorCiudadController) pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setFormularioCliente(this);
        pnt.pantalla().show();
    }

    public void setCiudadSeleccionada(Ciudad ciudad){
        if(ciudad != null){
            if(cliente == null){
                cliente = new Cliente();
            }

            cliente.setCiudad(ciudad);
            txtCiudad.setText(ciudad.getCiudadFormateada());
        }
    }

    public void btnLimpiarClick(){
        limpiar();
    }

    public void btnCerrarClick(){
        cerrar();
    }

    public void asignar(){
        if(cliente == null){
            cliente = new Cliente();
        }
        cliente.setNombres(txtNombres.getText());
        cliente.setApellidos(txtApellidos.getText());
        cliente.setTelefono(txtTelefono.getText());
        cliente.setCorreo(txtCorreo.getText());
        cliente.setFechaNacimiento(dpFechaNacimiento.getValue());
        cliente.setSexo(cbxSexo.getValue().toString().charAt(0));
    }
}