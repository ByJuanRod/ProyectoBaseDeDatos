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
import proyecto.com.proyectobasesdedatos.modelos.Municipio;
import proyecto.com.proyectobasesdedatos.servicios.ServicioMunicipios;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

public class VistaMunicipiosController implements Vista<Municipio>, Controlador {
    private final ServicioMunicipios servicio = ServicioMunicipios.getInstance();
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    @FXML
    public TableView<Municipio> tblMunicipios;

    @FXML
    public TextField txtBuscar;

    @FXML
    public TableColumn<Municipio, Integer> colCodigo;

    @FXML
    public TableColumn<Municipio, String> colNombre, colCiudad;

    private FilteredList<Municipio> datosFiltrados;

    @FXML
    public void initialize() {
        try {
            Inicializador.inicializar(this, tblMunicipios, txtBuscar);
        } catch (Exception e) {
            e.printStackTrace();
            AlertFactory.obtenerAlerta(TipoAlerta.ERROR)
                    .crearAlerta("Error al inicializar la vista de municipios: " + e.getMessage())
                    .show();
        }
    }

    @Override
    public AnchorPane setPlaceholder() {
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.MUNICIPIOS, "No se han encontrado municipios.");
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

        datosFiltrados.setPredicate(municipio -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(municipio.getIdMunicipio()).contains(textoBusqueda);
            boolean coincideNombre = municipio.getNombreMunicipio() != null &&
                    municipio.getNombreMunicipio().toLowerCase().contains(textoBusqueda);

            boolean coincideCiudad = municipio.getCiudad() != null &&
                    municipio.getCiudad().getNombre() != null &&
                    municipio.getCiudad().getNombre().toLowerCase().contains(textoBusqueda);

            return coincideCodigo || coincideNombre || coincideCiudad;
        });
    }

    @Override
    public void cargar() {
        try {
            ObservableList<Municipio> datosOriginales = servicio.consultar();
            datosFiltrados = new FilteredList<>(datosOriginales, p -> true);
            tblMunicipios.setItems(datosFiltrados);
            filtrar();
        } catch (Exception e) {
            e.printStackTrace();
            AlertFactory.obtenerAlerta(TipoAlerta.ERROR)
                    .crearAlerta("Error al cargar los datos de municipios: " + e.getMessage())
                    .show();
        }
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("idMunicipio"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreMunicipio"));
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("nombreCiudad"));

        FormatearTabla.ajustarAnchoColumnas(tblMunicipios);
    }
}