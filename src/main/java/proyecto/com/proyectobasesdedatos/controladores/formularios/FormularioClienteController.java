package proyecto.com.proyectobasesdedatos.controladores.formularios;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.Formulario;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.servicios.ServicioClientes;
import proyecto.com.proyectobasesdedatos.utilidades.Modalidad;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

public class FormularioClienteController implements Formulario, Controlador {
    ServicioClientes serv = new  ServicioClientes();

    @FXML
    public Button btnRegistrar, btnLimpiar,  btnCerrar;

    @FXML
    public TextField txtNombres, txtApellidos, txtTelefono;

    private Stage stage;

    private Cliente cliente;

    private Modalidad modalidad;

    @Override
    public void setModalidad(Modalidad mod){
        modalidad = mod;
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

        if(txtTelefono.getText().trim().length() != 11) {
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("El campo de teléfono es obligatorio y requiere almenos 11 caracteres.").show();
            return false;
        }

        return true;
    }

    public void setCliente(Cliente clt){
        cliente = clt;

        if(clt != null){
            cargarCliente();
        }
    }

    @Override
    public void limpiar() {
        txtNombres.setText("");
        txtApellidos.setText("");
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

    private void cargarCliente(){
        txtNombres.setText(cliente.getNombres());
        txtApellidos.setText(cliente.getApellidos());
        txtTelefono.setText(cliente.getTelefono());
    }

    public void btnRegistrarClick(){
        if(validar()){
            asignar();
            if(modalidad.equals(Modalidad.INSERTAR)){
                serv.crear(cliente);
            }
            else if(modalidad.equals(Modalidad.ACTUALIZAR)){
                serv.actualizar(cliente);
            }
            else if(modalidad.equals(Modalidad.OPERACION_EXTERNA)){
                serv.crear(cliente);
            }
        }

    }

    public void btnLimpiarClick(){
        limpiar();
    }

    public void btnCerrarClick(){
        cerrar();
    }

    public void asignar(){
        cliente.setNombres(txtNombres.getText());
        cliente.setApellidos(txtApellidos.getText());
        cliente.setTelefono(txtTelefono.getText());
    }
}
