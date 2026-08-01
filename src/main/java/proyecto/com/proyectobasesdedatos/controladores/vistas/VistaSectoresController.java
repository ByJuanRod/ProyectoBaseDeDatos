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
import proyecto.com.proyectobasesdedatos.modelos.Sector;
import proyecto.com.proyectobasesdedatos.servicios.ServicioSectores;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

public class VistaSectoresController implements Vista<Sector>, Controlador {
    private final ServicioSectores servicio = ServicioSectores.getInstance();
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    @FXML
    public TableView<Sector> tblSectores;

    @FXML
    public TextField txtBuscar;

    @FXML
    public TableColumn<Sector, Integer> colCodigo;

    @FXML
    public TableColumn<Sector, String> colNombre, colMunicipio;

    private FilteredList<Sector> datosFiltrados;

    @FXML
    public void initialize() {
        try {
            Inicializador.inicializar(this, tblSectores, txtBuscar);
        } catch (Exception e) {
            e.printStackTrace();
            AlertFactory.obtenerAlerta(TipoAlerta.ERROR)
                    .crearAlerta("Error al inicializar la vista de sectores: " + e.getMessage())
                    .show();
        }
    }

    @Override
    public AnchorPane setPlaceholder() {
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.SECTORES, "No se han encontrado sectores.");
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

        datosFiltrados.setPredicate(sector -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(sector.getIdSector()).contains(textoBusqueda);
            boolean coincideNombre = sector.getNombreSector() != null &&
                    sector.getNombreSector().toLowerCase().contains(textoBusqueda);

            boolean coincideMunicipio = sector.getMunicipio() != null &&
                    sector.getMunicipio().getNombreMunicipio() != null &&
                    sector.getMunicipio().getNombreMunicipio().toLowerCase().contains(textoBusqueda);

            return coincideCodigo || coincideNombre || coincideMunicipio;
        });
    }

    @Override
    public void cargar() {
        try {
            ObservableList<Sector> datosOriginales = servicio.consultar();
            datosFiltrados = new FilteredList<>(datosOriginales, p -> true);
            tblSectores.setItems(datosFiltrados);
            filtrar();
        } catch (Exception e) {
            e.printStackTrace();
            AlertFactory.obtenerAlerta(TipoAlerta.ERROR)
                    .crearAlerta("Error al cargar los datos de sectores: " + e.getMessage())
                    .show();
        }
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("idSector"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreSector"));
        colMunicipio.setCellValueFactory(new PropertyValueFactory<>("nombreMunicipio"));
        FormatearTabla.ajustarAnchoColumnas(tblSectores);
    }
}