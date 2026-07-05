package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import proyecto.com.proyectobasesdedatos.controladores.Vista;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioClienteController;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.servicios.ServicioClientes;
import proyecto.com.proyectobasesdedatos.utilidades.FormatearTabla;
import proyecto.com.proyectobasesdedatos.utilidades.Modalidad;
import proyecto.com.proyectobasesdedatos.utilidades.Pantalla;
import proyecto.com.proyectobasesdedatos.utilidades.StageBuilder;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

import java.awt.*;

public class VistaClientesController implements Vista<Cliente> {
    private final ServicioClientes servicio = new ServicioClientes();

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    @FXML
    public TableView<Cliente> tblClientes;

    @FXML
    public TextField txtBuscar;

    @FXML
    public TableColumn<Cliente,Integer> colCodigo, colEntradas;

    @FXML
    public TableColumn<Cliente,String> colNombres, colApellidos, colTelefono;

    private FilteredList<Cliente> datosFiltrados;

    @FXML
    public void initialize() {
        try{
            configurarColumnas();
            cargar();
            txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> filtrar());
            tblClientes.widthProperty().addListener((obs, oldWidth, newWidth) -> FormatearTabla.ajustarAnchoColumnas(tblClientes));
        }
        catch(Exception e){
            AlertFactory.obtenerAlerta(TipoAlerta.ERROR).crearAlerta("No se ha podido iniciar el apartado").show();
        }
    }

    public void btnEliminarClick(){
        try{
            Cliente cliente = tblClientes.getSelectionModel().getSelectedItem();
            if(servicio.eliminar(cliente)){
                AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("El cliente ha sido eliminado.").show();
            }
            else{
                AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("No se puede eliminar este cliente.").show();
            }
        }
        catch (Exception e){
            AlertFactory.obtenerAlerta(TipoAlerta.ERROR).crearAlerta("No se ha logrado eliminar el registro.").show();
        }
    }

    public void btnActualizarClick(){
        Cliente cli =  tblClientes.getSelectionModel().getSelectedItem();

        if(cli != null){
            crearPantalla(Modalidad.ACTUALIZAR,cli);
        }
    }

    public void btnRegistrarClick(){
        crearPantalla(Modalidad.INSERTAR,null);
    }

    public void txtBuscarKeyReleased(){
        filtrar();
    }

    @Override
    public void filtrar() {
        String textoBusqueda = txtBuscar.getText().trim().toLowerCase();

        datosFiltrados.setPredicate(cliente -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(cliente.getCodigo()).contains(textoBusqueda);
            boolean coincideNombre = cliente.getNombres() != null &&
                    cliente.getNombres().toLowerCase().contains(textoBusqueda);

            boolean coincideApellidos = cliente.getApellidos() != null &&
                    cliente.getApellidos().toLowerCase().contains(textoBusqueda);
            boolean coincideTelefono = cliente.getTelefono() != null &&
                    cliente.getTelefono().toLowerCase().contains(textoBusqueda);

            return coincideCodigo || coincideNombre || coincideApellidos || coincideTelefono;
        });
    }

    @Override
    public void cargar() {
        ObservableList<Cliente> datosOriginales = servicio.consultar();
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);

        tblClientes.setItems(datosFiltrados);
        filtrar();
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colEntradas.setCellValueFactory(new PropertyValueFactory<>("entradas"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }

    @Override
    public void crearPantalla(Modalidad modalidad, Cliente clt){
        Pantalla pnt = new StageBuilder()
                .setContenido("formularios/formulario-cliente.fxml")
                .setModalidad(Modality.WINDOW_MODAL)
                .setTitulo(modalidad.equals(Modalidad.INSERTAR) ? "Registrar Cliente" : "Actualizar Cliente")
                .setSize(new Dimension(680,530))
                .construirPantalla();

        FormularioClienteController controlador = (FormularioClienteController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(modalidad);
        controlador.setCliente(clt);

        pnt.pantalla().show();
        pnt.pantalla().setOnHidden(event -> cargar());
    }
}
