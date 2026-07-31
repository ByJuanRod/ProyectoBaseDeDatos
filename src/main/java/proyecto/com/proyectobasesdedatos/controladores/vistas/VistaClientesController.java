package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.servicios.ServicioClientes;
import proyecto.com.proyectobasesdedatos.utilidades.*;

public class VistaClientesController implements Vista<Cliente>, Controlador {
    private final ServicioClientes servicio = ServicioClientes.getInstance();

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    @FXML
    public TableView<Cliente> tblClientes;

    @FXML
    public TextField txtBuscar;

    @FXML
    public TableColumn<Cliente, Integer> colCodigo, colEntradas;

    @FXML
    public TableColumn<Cliente, String> colNombres, colApellidos, colTelefono;

    private FilteredList<Cliente> datosFiltrados;

    @FXML
    public void initialize() {
        Inicializador.inicializar(this, tblClientes, txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder() {
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.CLIENTES, "No se han encontrado clientes.");
        return comp.visual();
    }

    @FXML
    public void btnEliminarClick() {
    }

    @FXML
    public void btnActualizarClick() {
    }

    @FXML
    public void btnRegistrarClick() {

    }

    @FXML
    public void txtBuscarKeyReleased() {
        filtrar();
    }

    @Override
    public void filtrar() {
        if (datosFiltrados == null) {
            return;
        }

        String textoBusqueda = txtBuscar.getText().trim().toLowerCase();

        datosFiltrados.setPredicate(cliente -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(cliente.getCodigo()).contains(textoBusqueda);
            boolean coincideNombres = cliente.getNombres() != null &&
                    cliente.getNombres().toLowerCase().contains(textoBusqueda);
            boolean coincideApellidos = cliente.getApellidos() != null &&
                    cliente.getApellidos().toLowerCase().contains(textoBusqueda);
            boolean coincideTelefono = cliente.getTelefono() != null &&
                    cliente.getTelefono().contains(textoBusqueda);
            boolean coincideEntradas = String.valueOf(cliente.getCantidadEntradas()).contains(textoBusqueda);

            return coincideCodigo || coincideNombres || coincideApellidos ||
                    coincideTelefono || coincideEntradas;
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
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEntradas.setCellValueFactory(new PropertyValueFactory<>("cantidadEntradas"));
        FormatearTabla.ajustarAnchoColumnas(tblClientes);
    }
}