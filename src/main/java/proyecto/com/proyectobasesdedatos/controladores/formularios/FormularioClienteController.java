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
import proyecto.com.proyectobasesdedatos.controladores.componentes.SelectorSectorController;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.modelos.Sector;
import proyecto.com.proyectobasesdedatos.servicios.ServicioClientes;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

import java.time.LocalDate;
import java.util.function.Consumer;

public class FormularioClienteController implements Formulario, Controlador {
    ServicioClientes serv = ServicioClientes.getInstance();

    @FXML
    public Button btnRegistrar;

    @FXML
    public ImageView imgICono;

    @FXML
    public TextField txtNombres, txtApellidos, txtTelefono, txtCorreo, txtSector;

    @FXML
    public DatePicker dpFechaNacimiento;


    @FXML
    public ComboBox<Sexo> cbxSexo;

    private Stage stage;

    private Cliente cliente;

    private Modalidad modalidad;

    // Callback usado cuando el formulario se abre en modo OPERACION_EXTERNA
    // (ej. desde el selector de clientes al dar clic en "Registrar"), para
    // notificar hacia arriba con el cliente recién creado.
    private Consumer<Cliente> onClienteRegistrado;

    public void setOnClienteRegistrado(Consumer<Cliente> onClienteRegistrado) {
        this.onClienteRegistrado = onClienteRegistrado;
    }

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
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("El campo de teléfono es obligatorio y requiere al menos 10 dígitos.").show();
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

        LocalDate fechaNacimiento = dpFechaNacimiento.getValue();
        LocalDate fechaMinima = LocalDate.now().minusYears(12);
        if (fechaNacimiento.isAfter(fechaMinima)) {
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA)
                    .crearAlerta("El cliente debe tener al menos 12 años de edad.").show();
            return false;
        }

        if(cliente == null || cliente.getSectorResidencia() == null){
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("Debe seleccionar un sector de residencia.").show();
            return false;
        }

        return true;
    }

    public void setCliente(Cliente clt){
        if(clt != null){
            cliente = clt;
            cargarCliente();
        } else {
            cliente = new Cliente();
        }
    }

    @Override
    public void limpiar() {
        txtNombres.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtSector.setText("");
        dpFechaNacimiento.setValue(null);
        cbxSexo.getSelectionModel().selectFirst();

        if(cliente != null){
            cliente.setSectorResidencia(null);
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

        if(cliente.getSectorResidencia() != null){
            txtSector.setText(cliente.getSectorResidencia().getNombreSector());
        }
    }

    @FXML
    public void btnRegistrarClick(){
        if(validar()){
            asignar();

            boolean exito;

            if(modalidad.equals(Modalidad.INSERTAR)){
                exito = serv.guardar(cliente);
                if(exito) {
                    AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION)
                            .crearAlerta("Cliente registrado exitosamente.\nCódigo: " + cliente.getCodigo())
                            .show();
                    limpiar();
                } else {
                    AlertFactory.obtenerAlerta(TipoAlerta.ERROR)
                            .crearAlerta("Error al registrar el cliente.\nVerifique que los datos sean correctos.")
                            .show();
                }
            } else if(modalidad.equals(Modalidad.OPERACION_EXTERNA)){
                exito = serv.guardar(cliente);
                if(exito) {
                    AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION)
                            .crearAlerta("Cliente registrado exitosamente.\nCódigo: " + cliente.getCodigo())
                            .show();

                    // Notificar hacia arriba (ej. al selector de clientes)
                    // con el cliente recién creado, y cerrar este formulario.
                    if (onClienteRegistrado != null) {
                        onClienteRegistrado.accept(cliente);
                    }
                    cerrar();
                } else {
                    AlertFactory.obtenerAlerta(TipoAlerta.ERROR)
                            .crearAlerta("Error al registrar el cliente.\nVerifique que los datos sean correctos.")
                            .show();
                }
            } else if(modalidad.equals(Modalidad.ACTUALIZAR)){
                exito = serv.guardar(cliente);
                if(exito) {
                    AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION)
                            .crearAlerta("Cliente actualizado exitosamente.")
                            .show();
                    cerrar();
                } else {
                    AlertFactory.obtenerAlerta(TipoAlerta.ERROR)
                            .crearAlerta("Error al actualizar el cliente.")
                            .show();
                }
            }
        }
    }

    @FXML
    public void btnSeleccionarClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido(Selectores.SECTORES.getArchivo())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(Selectores.SECTORES.getTitulo())
                .setSize(Selectores.SECTORES.getSize())
                .construirPantalla();

        SelectorSectorController controlador = (SelectorSectorController) pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setOnSeleccionar(this::setSectorSeleccionada);
        pnt.pantalla().show();
    }

    public void setSectorSeleccionada(Sector sector){
        if(sector != null){
            if(cliente == null){
                cliente = new Cliente();
            }
            cliente.setSectorResidencia(sector);
            txtSector.setText(sector.getNombreSector());
        }
    }

    @FXML
    public void btnLimpiarClick(){
        limpiar();
    }

    @FXML
    public void btnCerrarClick(){
        cerrar();
    }

    public void asignar(){
        if(cliente == null){
            cliente = new Cliente();
        }
        cliente.setNombres(txtNombres.getText().trim());
        cliente.setApellidos(txtApellidos.getText().trim());
        cliente.setTelefono(txtTelefono.getText().trim());
        cliente.setCorreo(txtCorreo.getText().trim());
        cliente.setFechaNacimiento(dpFechaNacimiento.getValue());
        cliente.setSexo(cbxSexo.getValue().toString().charAt(0));
    }
}