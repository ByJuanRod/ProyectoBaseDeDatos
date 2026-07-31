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
import proyecto.com.proyectobasesdedatos.modelos.Genero;
import proyecto.com.proyectobasesdedatos.servicios.ServicioGeneros;
import proyecto.com.proyectobasesdedatos.utilidades.*;

public class VistaGenerosController implements Vista<Genero>, Controlador {
    private final ServicioGeneros servicio = ServicioGeneros.getInstance();
    private Stage stage;

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    @FXML
    public TableView<Genero> tblGeneros;

    @FXML
    public TextField txtBuscar;

    @FXML
    public TableColumn<Genero, Integer> colCodigo;

    @FXML
    public TableColumn<Genero, String> colNombre;

    private FilteredList<Genero> datosFiltrados;

    public void setStage(Stage stg) {
        stage = stg;
    }

    @FXML
    public void initialize() {
        Inicializador.inicializar(this, tblGeneros, txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder() {
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.GENEROS, "No se han encontrado géneros.");
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
        String textoBusqueda = txtBuscar.getText().trim().toLowerCase();

        datosFiltrados.setPredicate(genero -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(genero.getCodigo()).contains(textoBusqueda);
            boolean coincideNombre = genero.getNombre() != null &&
                    genero.getNombre().toLowerCase().contains(textoBusqueda);

            return coincideCodigo || coincideNombre;
        });
    }

    @Override
    public void cargar() {
        ObservableList<Genero> datosOriginales = servicio.consultar();
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);
        tblGeneros.setItems(datosFiltrados);
        filtrar();
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        FormatearTabla.ajustarAnchoColumnas(tblGeneros);
    }
}