package proyecto.com.proyectobasesdedatos.controladores.formularios;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.componentes.SelectorFuncionController;
import proyecto.com.proyectobasesdedatos.modelos.wrappers.BoletoWrapper;
import proyecto.com.proyectobasesdedatos.servicios.ServicioBoletosTemporal;
import proyecto.com.proyectobasesdedatos.utilidades.*;

import java.awt.Dimension;
import java.util.ArrayList;

public class FormularioVenderController implements Controlador {
    private Stage stage;
    private final ObservableList<BoletoWrapper> boletosObservable = FXCollections.observableArrayList();
    private final ServicioBoletosTemporal servicioBoletosTemp = ServicioBoletosTemporal.getInstance();

    @FXML
    public Label lblMonto;

    @FXML
    public TableView<BoletoWrapper> tblBoletos;

    @FXML
    public TableColumn<BoletoWrapper, String> colAsiento, colPelicula;

    @FXML
    public TableColumn<BoletoWrapper, Float> colPrecio;

    @FXML
    public TableColumn<BoletoWrapper, Integer> colFuncion;

    @FXML
    public AnchorPane rootPane;

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarBoletosExistentes();
        configurarPlaceholder();
        actualizarUI();

        tblBoletos.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            FormatearTabla.ajustarAnchoColumnas(tblBoletos);
        });

        javafx.application.Platform.runLater(() -> {
            FormatearTabla.ajustarAnchoColumnas(tblBoletos);
        });
    }

    private void configurarColumnas() {
        colAsiento.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAsientoInfo()));
        colPrecio.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getPrecio()).asObject());
        colFuncion.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getFuncionCodigo()).asObject());
        colPelicula.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPeliculaNombre()));

        colPrecio.setCellFactory(column -> new TableCell<BoletoWrapper, Float>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : String.format("$%.2f", item));
            }
        });
    }

    private void cargarBoletosExistentes() {
        boletosObservable.clear();
        boletosObservable.addAll(servicioBoletosTemp.getBoletosSeleccionados());
    }

    private void configurarPlaceholder() {
        try {
            CargadorFXML cargadorFXML = new CargadorFXML();
            Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");

            if (comp != null && comp.controlador() != null) {
                PlaceholderController cont = (PlaceholderController) comp.controlador();
                cont.setContenido(Vistas.VENTAS, "Agrega boletos usando el botón 'Agregar Boleto'");
                tblBoletos.setPlaceholder(comp.visual());
            }
        } catch (Exception e) {
            Label label = new Label("No hay boletos agregados");
            label.setStyle("-fx-text-fill: #888888; -fx-font-size: 14px;");
            tblBoletos.setPlaceholder(label);
        }
    }

    @FXML
    public void btnAgregarClick() {
        try {
            StageBuilder builder = new StageBuilder()
                    .setContenido("componentes/selector-funcion.fxml")
                    .setModalidad(Modality.APPLICATION_MODAL)
                    .setTitulo("Selector de Funciones")
                    .setSize(new Dimension(950, 700));

            Pantalla pantalla = builder.construirPantalla();

            if (pantalla != null && pantalla.componte() != null) {
                Stage selectorStage = pantalla.pantalla();
                SelectorFuncionController controller = (SelectorFuncionController) pantalla.componte().controlador();

                controller.setStage(selectorStage);
                controller.setFormularioVender(this);

                selectorStage.showAndWait();
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo abrir el selector de funciones: " + e.getMessage());
        }
    }

    @FXML
    public void btnEliminarClick() {
        BoletoWrapper seleccionado = tblBoletos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            servicioBoletosTemp.eliminarBoleto(seleccionado);
            boletosObservable.remove(seleccionado);
            actualizarUI();
        } else {
            mostrarAlerta("Error", "Selecciona un boleto para eliminar");
        }
    }

    @FXML
    public void btnFacturarClick() {
        if (servicioBoletosTemp.getBoletosSeleccionados().isEmpty()) {
            mostrarAlerta("Error", "El carrito está vacío");
            return;
        }

        // 1. Instanciamos la nueva Venta y asignamos datos generales
        proyecto.com.proyectobasesdedatos.modelos.Venta nuevaVenta = new proyecto.com.proyectobasesdedatos.modelos.Venta();

        long tiempoActual = System.currentTimeMillis();
        nuevaVenta.setFecha(new java.sql.Date(tiempoActual));
        nuevaVenta.setHora(new java.sql.Time(tiempoActual));
        nuevaVenta.setPrecioTotal(servicioBoletosTemp.getTotal());

        // Asignación estática temporal para evitar errores de clave foránea
        proyecto.com.proyectobasesdedatos.modelos.Cliente c = new proyecto.com.proyectobasesdedatos.modelos.Cliente();
        c.setCodigo(1);
        nuevaVenta.setCliente(c);

        proyecto.com.proyectobasesdedatos.modelos.Empleado e = new proyecto.com.proyectobasesdedatos.modelos.Empleado();
        e.setCodigo(21);
        nuevaVenta.setEmpleado(e);

        proyecto.com.proyectobasesdedatos.modelos.Sucursal s = new proyecto.com.proyectobasesdedatos.modelos.Sucursal();
        s.setCodigo(1);
        nuevaVenta.setSucursal(s);

        // 2. Extraemos los wrappers del servicio temporal y construimos los Boletos reales
        java.util.List<proyecto.com.proyectobasesdedatos.modelos.Boleto> listaBoletos = new java.util.ArrayList<>();

        for (BoletoWrapper bw : servicioBoletosTemp.getBoletosSeleccionados()) {
            proyecto.com.proyectobasesdedatos.modelos.Boleto boleto = new proyecto.com.proyectobasesdedatos.modelos.Boleto();
            boleto.setPrecioAplicado(bw.getPrecio());
            boleto.setFuncion(bw.getFuncion());
            boleto.setAsiento(bw.getAsiento());

            listaBoletos.add(boleto);
        }

        nuevaVenta.setBoletos(listaBoletos);

        // 3. Ejecutamos la inserción mediante el servicio
        proyecto.com.proyectobasesdedatos.servicios.ServicioVentas servicioVentas =
                proyecto.com.proyectobasesdedatos.servicios.ServicioVentas.getInstance();

        boolean exito = servicioVentas.guardar(nuevaVenta);

        if (exito) {
            mostrarAlerta("Éxito", "Factura generada y guardada exitosamente");
            // Vaciamos el servicio temporal para que la pantalla anterior sepa que ya se vendió
            servicioBoletosTemp.limpiar();
            cerrar();
        } else {
            mostrarAlerta("Error", "Ocurrió un problema de transacción al guardar en la base de datos");
        }
    }

    @FXML
    public void btnCerrarClick() {
        cerrar();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void cerrar() {
        if (stage != null) stage.close();
    }

    private void actualizarUI() {
        tblBoletos.setItems(boletosObservable);
        lblMonto.setText(String.format("$%.2f", servicioBoletosTemp.getTotal()));
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void agregarBoletos(java.util.List<BoletoWrapper> nuevosBoletos) {
        if (nuevosBoletos == null || nuevosBoletos.isEmpty()) return;

        ArrayList<BoletoWrapper> boletosAAgregar = new ArrayList<>();
        for (BoletoWrapper nuevoBoleto : nuevosBoletos) {
            boolean existe = boletosObservable.stream().anyMatch(e ->
                    e.getFuncion() != null && nuevoBoleto.getFuncion() != null &&
                            e.getFuncion().getCodigo() == nuevoBoleto.getFuncion().getCodigo() &&
                            e.getAsiento() != null && nuevoBoleto.getAsiento() != null &&
                            e.getAsiento().getCodigo() == nuevoBoleto.getAsiento().getCodigo()
            );
            if (!existe) boletosAAgregar.add(nuevoBoleto);
        }

        if (boletosAAgregar.isEmpty()) {
            mostrarAlerta("Información", "Los boletos seleccionados ya están agregados");
            return;
        }

        int agregados = servicioBoletosTemp.agregarBoletos(boletosAAgregar);
        if (agregados > 0) {
            boletosObservable.addAll(boletosAAgregar);
            actualizarUI();
        }
    }

    public void vaciarCarrito() {
        servicioBoletosTemp.limpiar();
        boletosObservable.clear();
        actualizarUI();
    }
}