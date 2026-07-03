package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import proyecto.com.proyectobasesdedatos.controladores.Vista;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.servicios.ServicioClientes;
import proyecto.com.proyectobasesdedatos.utilidades.FormatearTabla;

public class VistaClientesController implements Vista {
    private final ServicioClientes servicio = new ServicioClientes();

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    @FXML
    public TableView<Cliente> tblClientes;

    @FXML
    public TextField txtBuscar;

    private FilteredList<Cliente> datosFiltrados;

    @FXML
    public void initialize() {
        configurarColumnas();
        cargar();
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> filtrar());
        tblClientes.widthProperty().addListener((obs, oldWidth, newWidth) -> FormatearTabla.ajustarAnchoColumnas(tblClientes));
    }

    public void btnEliminarClick(){

    }

    public void btnActualizarClick(){

    }

    public void btnRegistrarClick(){

    }

    @Override
    public void filtrar() {

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

    }
}
