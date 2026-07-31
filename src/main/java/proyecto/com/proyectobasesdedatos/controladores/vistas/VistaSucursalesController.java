package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.modelos.Sucursal;
import proyecto.com.proyectobasesdedatos.servicios.ServicioSucursales;
import proyecto.com.proyectobasesdedatos.utilidades.*;

public class VistaSucursalesController implements Vista<Sucursal>, Controlador {
    private final ServicioSucursales servicio = ServicioSucursales.getInstance();

    @FXML
    public TableView<Sucursal> tblSucursales;

    @FXML
    public TableColumn<Sucursal, Integer> colCodigo;

    @FXML
    public TableColumn<Sucursal, String> colNombre, colCiudad, colTelefono;

    @FXML
    public TextField txtBuscar;

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    private FilteredList<Sucursal> datosFiltrados;

    @FXML
    public void initialize() {
        Inicializador.inicializar(this, tblSucursales, txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder() {
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.SUCURSALES, "No se han encontrado sucursales.");
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

        datosFiltrados.setPredicate(sucursal -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(sucursal.getCodigo()).contains(textoBusqueda);
            boolean coincideNombre = sucursal.getNombre() != null &&
                    sucursal.getNombre().toLowerCase().contains(textoBusqueda);
            boolean coincideTelefono = sucursal.getTelefono() != null &&
                    sucursal.getTelefono().contains(textoBusqueda);

            boolean coincideCiudad = false;
            if (sucursal.getSector() != null &&
                    sucursal.getSector().getMunicipio() != null &&
                    sucursal.getSector().getMunicipio().getCiudad() != null &&
                    sucursal.getSector().getMunicipio().getCiudad().getNombre() != null) {
                coincideCiudad = sucursal.getSector().getMunicipio().getCiudad().getNombre()
                        .toLowerCase().contains(textoBusqueda);
            }

            return coincideCodigo || coincideNombre || coincideTelefono || coincideCiudad;
        });
    }

    @Override
    public void cargar() {
        ObservableList<Sucursal> datosOriginales = servicio.consultar();
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);
        tblSucursales.setItems(datosFiltrados);
        filtrar();
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        colCiudad.setCellValueFactory(cellData -> {
            if (cellData.getValue().getSector() != null &&
                    cellData.getValue().getSector().getMunicipio() != null &&
                    cellData.getValue().getSector().getMunicipio().getCiudad() != null) {
                return new SimpleStringProperty(
                        cellData.getValue().getSector().getMunicipio().getCiudad().getNombre()
                );
            } else {
                return new SimpleStringProperty("");
            }
        });

        FormatearTabla.ajustarAnchoColumnas(tblSucursales);
    }
}