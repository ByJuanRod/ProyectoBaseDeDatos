package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.modelos.Venta;
import proyecto.com.proyectobasesdedatos.servicios.ServicioVentas;
import proyecto.com.proyectobasesdedatos.utilidades.*;

public class VistaVentasController implements Vista<Venta>, Controlador {
    private final ServicioVentas servicio = ServicioVentas.getInstance();

    @FXML
    public TableView<Venta> tblVentas;

    @FXML
    public TableColumn<Venta, Integer> colCodigo, colEntradas;

    @FXML
    public TableColumn<Venta, String> colCliente, colFecha;

    @FXML
    public TableColumn<Venta, Float> colTotal;

    @FXML
    public TextField txtBuscar;

    @FXML
    public Button btnRegistrar, btnInforme;

    private FilteredList<Venta> datosFiltrados;

    @FXML
    public void initialize() {
        Inicializador.inicializar(this, tblVentas, txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder() {
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.VENTAS, "No se han encontrado ventas.");
        return comp.visual();
    }

    @FXML
    public void btnRegistrarClick() {
    }

    @FXML
    public void btnInformeClick() {
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

        datosFiltrados.setPredicate(venta -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(venta.getCodigo()).contains(textoBusqueda);

            boolean coincideCliente = false;
            if (venta.getCliente() != null) {
                String nombreCliente = venta.getCliente().getNombres() != null ?
                        venta.getCliente().getNombres().toLowerCase() : "";
                String apellidoCliente = venta.getCliente().getApellidos() != null ?
                        venta.getCliente().getApellidos().toLowerCase() : "";
                coincideCliente = nombreCliente.contains(textoBusqueda) ||
                        apellidoCliente.contains(textoBusqueda);
            }

            boolean coincideFecha = venta.getFecha() != null &&
                    venta.getFecha().toString().contains(textoBusqueda);

            int cantidadBoletos = venta.getBoletos() != null ? venta.getBoletos().size() : 0;
            boolean coincideEntradas = String.valueOf(cantidadBoletos).contains(textoBusqueda);

            boolean coincideTotal = String.valueOf(venta.getPrecioTotal()).contains(textoBusqueda);

            return coincideCodigo || coincideCliente || coincideFecha ||
                    coincideEntradas || coincideTotal;
        });
    }

    @Override
    public void cargar() {
        ObservableList<Venta> datosOriginales = servicio.consultar();
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);
        tblVentas.setItems(datosFiltrados);
        filtrar();
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colEntradas.setCellValueFactory(new PropertyValueFactory<>("cantidadBoletos"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("precioTotal"));
        FormatearTabla.ajustarAnchoColumnas(tblVentas);
    }
}