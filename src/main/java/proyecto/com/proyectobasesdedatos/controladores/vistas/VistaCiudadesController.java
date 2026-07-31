package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.modelos.Ciudad;
import proyecto.com.proyectobasesdedatos.servicios.ServicioCiudades;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

public class VistaCiudadesController implements Vista<Ciudad>, Controlador {
    private final ServicioCiudades servicio = ServicioCiudades.getInstance();
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    @FXML
    public TableView<Ciudad> tblCiudades;

    @FXML
    public TextField txtBuscar;

    @FXML
    public TableColumn<Ciudad, Integer> colCodigo;

    @FXML
    public TableColumn<Ciudad, String> colNombre, colCodigoPostal, colPais;

    private FilteredList<Ciudad> datosFiltrados;

    @FXML
    public void initialize() {
        try {
            Inicializador.inicializar(this, tblCiudades, txtBuscar);
        } catch (Exception e) {
            e.printStackTrace();
            AlertFactory.obtenerAlerta(TipoAlerta.ERROR)
                    .crearAlerta("Error al inicializar la vista de ciudades: " + e.getMessage())
                    .show();
        }
    }

    @Override
    public AnchorPane setPlaceholder() {
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.CIUDADES, "No se han encontrado ciudades.");
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
    public void btnCerrarClick() {
        stage.close();
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

        datosFiltrados.setPredicate(ciudad -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(ciudad.getCodigo()).contains(textoBusqueda);
            boolean coincideNombre = ciudad.getNombre() != null &&
                    ciudad.getNombre().toLowerCase().contains(textoBusqueda);

            boolean coincideCodigoPostal = ciudad.getCodigoPostal() != null &&
                    ciudad.getCodigoPostal().contains(textoBusqueda);

            boolean coincidePais = ciudad.getPais() != null &&
                    ciudad.getPais().getNombre() != null &&
                    ciudad.getPais().getNombre().toLowerCase().contains(textoBusqueda);

            return coincideCodigo || coincideNombre || coincideCodigoPostal || coincidePais;
        });
    }

    @Override
    public void cargar() {
        try {
            ObservableList<Ciudad> datosOriginales = servicio.consultar();
            datosFiltrados = new FilteredList<>(datosOriginales, p -> true);
            tblCiudades.setItems(datosFiltrados);
            filtrar();
        } catch (Exception e) {
            e.printStackTrace();
            AlertFactory.obtenerAlerta(TipoAlerta.ERROR)
                    .crearAlerta("Error al cargar los datos de ciudades: " + e.getMessage())
                    .show();
        }
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCodigoPostal.setCellValueFactory(new PropertyValueFactory<>("codigoPostal"));
        colPais.setCellValueFactory(new PropertyValueFactory<>("nombrePais"));
        FormatearTabla.ajustarAnchoColumnas(tblCiudades);
    }
}