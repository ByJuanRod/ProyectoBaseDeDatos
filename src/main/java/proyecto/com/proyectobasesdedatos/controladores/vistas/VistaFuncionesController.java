package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.componentes.SeleccionAsientosController;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioFacturaController;
import proyecto.com.proyectobasesdedatos.modelos.Funcion;
import proyecto.com.proyectobasesdedatos.servicios.ServicioFunciones;
import proyecto.com.proyectobasesdedatos.utilidades.*;

public class VistaFuncionesController implements Vista<Funcion>, Controlador {
    private final ServicioFunciones servicio = ServicioFunciones.getInstance();
    private FormularioFacturaController facturarController;


    @FXML
    public TableView<Funcion> tblFunciones;

    @FXML
    public TableColumn<Funcion, Integer> colCodigo;

    @FXML
    public TableColumn<Funcion, String> colPelicula, colFecha, colSala;

    @FXML
    public TableColumn<Funcion, Float> colPrecio;

    @FXML
    public TextField txtBuscar;

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    private FilteredList<Funcion> datosFiltrados;

    @FXML
    public void initialize() {
        Inicializador.inicializar(this, tblFunciones, txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder() {
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.FUNCIONES, "No se han encontrado funciones.");
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

        datosFiltrados.setPredicate(funcion -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(funcion.getCodigo()).contains(textoBusqueda);
            boolean coincidePelicula = funcion.getPelicula() != null &&
                    funcion.getPelicula().getNombre() != null &&
                    funcion.getPelicula().getNombre().toLowerCase().contains(textoBusqueda);
            boolean coincideSala = funcion.getSala() != null &&
                    funcion.getSala().getNombre() != null &&
                    funcion.getSala().getNombre().toLowerCase().contains(textoBusqueda);
            boolean coincideFecha = funcion.getFecha() != null &&
                    funcion.getFecha().toString().contains(textoBusqueda);
            boolean coincidePrecio = String.valueOf(funcion.getPrecioEntrada()).contains(textoBusqueda);

            return coincideCodigo || coincidePelicula || coincideSala ||
                    coincideFecha || coincidePrecio;
        });
    }


    @Override
    public void cargar() {
        ObservableList<Funcion> datosOriginales = servicio.consultar();
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);
        tblFunciones.setItems(datosFiltrados);
        filtrar();
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colPelicula.setCellValueFactory(new PropertyValueFactory<>("nombrePelicula"));
        colSala.setCellValueFactory(new PropertyValueFactory<>("nombreSala"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioEntrada"));
        FormatearTabla.ajustarAnchoColumnas(tblFunciones);
    }

}