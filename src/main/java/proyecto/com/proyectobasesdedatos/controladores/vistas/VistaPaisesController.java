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
import proyecto.com.proyectobasesdedatos.modelos.Pais;
import proyecto.com.proyectobasesdedatos.servicios.ServicioPaises;
import proyecto.com.proyectobasesdedatos.utilidades.*;

public class VistaPaisesController implements Vista<Pais>, Controlador {
    private final ServicioPaises servicio = ServicioPaises.getInstance();
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    @FXML
    public TableView<Pais> tblPaises;

    @FXML
    public TextField txtBuscar;

    @FXML
    public TableColumn<Pais, Integer> colCodigo;

    @FXML
    public TableColumn<Pais, String> colNombre;

    private FilteredList<Pais> datosFiltrados;

    @FXML
    public void initialize() {
        Inicializador.inicializar(this, tblPaises, txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder() {
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.PAISES, "No se han encontrado países.");
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

        datosFiltrados.setPredicate(pais -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(pais.getCodigo()).contains(textoBusqueda);
            boolean coincideNombre = pais.getNombre() != null &&
                    pais.getNombre().toLowerCase().contains(textoBusqueda);

            return coincideCodigo || coincideNombre;
        });
    }

    @Override
    public void cargar() {
        ObservableList<Pais> datosOriginales = servicio.consultar();
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);
        tblPaises.setItems(datosFiltrados);
        filtrar();
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        FormatearTabla.ajustarAnchoColumnas(tblPaises);
    }
}