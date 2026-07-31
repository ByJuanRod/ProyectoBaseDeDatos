package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.modelos.Sala;
import proyecto.com.proyectobasesdedatos.servicios.ServicioSalas;
import proyecto.com.proyectobasesdedatos.utilidades.*;

public class VistaSalasController implements Vista<Sala>, Controlador {
    private final ServicioSalas servicio = ServicioSalas.getInstance();

    @FXML
    public TableView<Sala> tblSalas;

    @FXML
    public TableColumn<Sala, Integer> colCodigo, colCapacidad;

    @FXML
    public TableColumn<Sala, String> colNombre;

    @FXML
    public TextField txtBuscar;

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    private FilteredList<Sala> datosFiltrados;

    @FXML
    public void initialize() {
        Inicializador.inicializar(this, tblSalas, txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder() {
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.SALAS, "No se han encontrado salas.");
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

        datosFiltrados.setPredicate(sala -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(sala.getCodigo()).contains(textoBusqueda);
            boolean coincideNombre = sala.getNombre() != null &&
                    sala.getNombre().toLowerCase().contains(textoBusqueda);
            boolean coincideCapacidad = String.valueOf(sala.getCapacidad()).contains(textoBusqueda);
            boolean coincideSucursal = sala.getSucursal() != null &&
                    sala.getSucursal().getNombre() != null &&
                    sala.getSucursal().getNombre().toLowerCase().contains(textoBusqueda);

            return coincideCodigo || coincideNombre || coincideCapacidad || coincideSucursal;
        });
    }

    @Override
    public void cargar() {
        ObservableList<Sala> datosOriginales = servicio.consultar();
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);
        tblSalas.setItems(datosFiltrados);
        filtrar();
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        FormatearTabla.ajustarAnchoColumnas(tblSalas);
    }
}