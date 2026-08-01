package proyecto.com.proyectobasesdedatos.controladores.componentes;

import java.util.function.Consumer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioClienteController;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.servicios.ServicioClientes;
import proyecto.com.proyectobasesdedatos.utilidades.Formularios;
import proyecto.com.proyectobasesdedatos.utilidades.Modalidad;
import proyecto.com.proyectobasesdedatos.utilidades.Pantalla;
import proyecto.com.proyectobasesdedatos.utilidades.StageBuilder;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

public class SelectorClienteController implements Controlador {
    private final ServicioClientes srv = ServicioClientes.getInstance();

    private Stage stage;

    @FXML
    public TextField txtFiltrar;

    @FXML
    public TableView<Cliente> tblClientes;

    @FXML
    public TableColumn<Cliente, String> colNombre, colTelefono, colApellido;

    @FXML
    public TableColumn<Cliente, Integer> colEntradas;

    @FXML
    public TableColumn<Cliente, Integer> colCodigo;

    private FilteredList<Cliente> datosFiltrados;

    private Cliente clienteSeleccionado;

    private Consumer<Cliente> onSeleccionar;

    @FXML
    public void initialize(){
        configurarColumnas();
        cargar();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setOnSeleccionar(Consumer<Cliente> onSeleccionar){
        this.onSeleccionar = onSeleccionar;
    }

    private void configurarColumnas(){
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellido.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEntradas.setCellValueFactory(new PropertyValueFactory<>("cantidadEntradas"));
    }

    private void cargar(){
        ObservableList<Cliente> datosOriginales = FXCollections.observableArrayList(srv.obtenerTodos());
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);

        tblClientes.setItems(datosFiltrados);
        filtrar();
    }

    public void txtFiltrarKeyReleased(){
        filtrar();
    }

    private void filtrar(){
        String textoBusqueda = txtFiltrar.getText().trim().toLowerCase();

        datosFiltrados.setPredicate(cliente -> {
            if(textoBusqueda.isEmpty()){
                return true;
            }

            boolean coincideCodigo = String.valueOf(cliente.getCodigo()).contains(textoBusqueda);

            boolean coincideNombre = cliente.getNombres() != null &&
                    cliente.getNombres().toLowerCase().contains(textoBusqueda);

            boolean coincideApellido = cliente.getApellidos() != null &&
                    cliente.getApellidos().toLowerCase().contains(textoBusqueda);

            boolean coincideTelefono = cliente.getTelefono() != null &&
                    cliente.getTelefono().toLowerCase().contains(textoBusqueda);

            return coincideCodigo || coincideNombre || coincideApellido || coincideTelefono;
        });
    }

    public void btnSeleccionarClick(){
        Cliente cliente = tblClientes.getSelectionModel().getSelectedItem();

        if(cliente != null){
            clienteSeleccionado = cliente;

            if(onSeleccionar != null){
                onSeleccionar.accept(cliente);
            }

            stage.close();
        }
        else{
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("Debe seleccionar un cliente.").show();
        }
    }

    public void btnRegistrarClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido(Formularios.CLIENTE.getArchivo())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo("Registro de Cliente")
                .setSize(Formularios.CLIENTE.getSize())
                .construirPantalla();

        FormularioClienteController controlador = (FormularioClienteController) pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(Modalidad.OPERACION_EXTERNA);
        controlador.setCliente(null);

        controlador.setOnClienteRegistrado(clienteNuevo -> {
            if (onSeleccionar != null) {
                onSeleccionar.accept(clienteNuevo);
            }
            stage.close();
        });

        pnt.pantalla().show();
        pnt.pantalla().setOnHidden(event -> cargar());
    }

    public void btnCerrarClick(){
        stage.close();
    }
}