package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;
import proyecto.com.proyectobasesdedatos.servicios.ServicioPeliculas;
import proyecto.com.proyectobasesdedatos.utilidades.*;

public class VistaPeliculasController implements Vista<Pelicula>, Controlador {
    private final ServicioPeliculas servicio = ServicioPeliculas.getInstance();

    @FXML
    public TableView<Pelicula> tblPeliculas;

    @FXML
    public TableColumn<Pelicula, Integer> colCodigo;

    @FXML
    public TableColumn<Pelicula, String> colNombre, colDirector, colDuracion, colClasificacion;

    @FXML
    public TextField txtBuscar;

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    private FilteredList<Pelicula> datosFiltrados;

    @FXML
    public void initialize() {
        Inicializador.inicializar(this, tblPeliculas, txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder() {
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.PELICULAS, "No se han encontrado películas.");
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

        datosFiltrados.setPredicate(pelicula -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(pelicula.getCodigo()).contains(textoBusqueda);
            boolean coincideNombre = pelicula.getNombre() != null &&
                    pelicula.getNombre().toLowerCase().contains(textoBusqueda);

            boolean coincideDirector = false;
            if (pelicula.getDirector() != null) {
                String nombres = pelicula.getDirector().getNombres() != null ?
                        pelicula.getDirector().getNombres().toLowerCase() : "";
                String apellidos = pelicula.getDirector().getApellidos() != null ?
                        pelicula.getDirector().getApellidos().toLowerCase() : "";
                coincideDirector = nombres.contains(textoBusqueda) || apellidos.contains(textoBusqueda);
            }

            boolean coincideDuracion = String.valueOf(pelicula.getDuracionMinutos()).contains(textoBusqueda);
            boolean coincideClasificacion = pelicula.getClasificacion() != null &&
                    pelicula.getClasificacion().toLowerCase().contains(textoBusqueda);

            return coincideCodigo || coincideNombre || coincideDirector ||
                    coincideDuracion || coincideClasificacion;
        });
    }

    @Override
    public void cargar() {
        ObservableList<Pelicula> datosOriginales = servicio.consultar();
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);
        tblPeliculas.setItems(datosFiltrados);
        filtrar();
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDirector.setCellValueFactory(new PropertyValueFactory<>("nombreDirector"));
        colDuracion.setCellValueFactory(new PropertyValueFactory<>("duracionMinutos"));
        colClasificacion.setCellValueFactory(new PropertyValueFactory<>("clasificacion"));
        FormatearTabla.ajustarAnchoColumnas(tblPeliculas);
    }
}