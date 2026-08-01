package proyecto.com.proyectobasesdedatos.controladores.formularios;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.componentes.SelectorFuncionController;
import proyecto.com.proyectobasesdedatos.modelos.wrappers.BoletoWrapper;
import proyecto.com.proyectobasesdedatos.servicios.ServicioBoletosTemporal;
import proyecto.com.proyectobasesdedatos.utilidades.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormularioFacturaController implements Controlador {
    private Stage stage;
    private final ObservableList<BoletoWrapper> boletosObservable = FXCollections.observableArrayList();
    private final ServicioBoletosTemporal servicioBoletosTemp = ServicioBoletosTemporal.getInstance();

    @FXML
    public Label lblMonto;

    @FXML
    public TableView<BoletoWrapper> tblBoletos;

    @FXML
    public TableColumn<BoletoWrapper, String> colAsiento;

    @FXML
    public TableColumn<BoletoWrapper, Float> colPrecio;

    @FXML
    public TableColumn<BoletoWrapper, Integer> colFuncion;

    @FXML
    public TableColumn<BoletoWrapper, String> colPelicula;

    @FXML
    public AnchorPane rootPane;

    @FXML
    public void initialize() {
        configurarColumnas();
        cargarBoletosExistentes();
        configurarPlaceholder();
        actualizarUI();

        // Listener para ajustar el ancho cuando la tabla cambie de tamaño
        tblBoletos.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            FormatearTabla.ajustarAnchoColumnas(tblBoletos);
        });

        // Ejecutar después de que la UI esté completamente cargada
        javafx.application.Platform.runLater(() -> {
            FormatearTabla.ajustarAnchoColumnas(tblBoletos);
        });
    }

    private void configurarColumnas() {
        // Usar Callback en lugar de PropertyValueFactory para evitar problemas de módulo
        colAsiento.setCellValueFactory(cellData -> {
            BoletoWrapper wrapper = cellData.getValue();
            return new SimpleStringProperty(wrapper.getAsientoInfo());
        });

        colPrecio.setCellValueFactory(cellData -> {
            BoletoWrapper wrapper = cellData.getValue();
            return new SimpleFloatProperty(wrapper.getPrecio()).asObject();
        });

        colFuncion.setCellValueFactory(cellData -> {
            BoletoWrapper wrapper = cellData.getValue();
            return new SimpleIntegerProperty(wrapper.getFuncionCodigo()).asObject();
        });

        colPelicula.setCellValueFactory(cellData -> {
            BoletoWrapper wrapper = cellData.getValue();
            return new SimpleStringProperty(wrapper.getPeliculaNombre());
        });

        // Formatear la columna de precio - usando TableCell<BoletoWrapper, Float> correctamente
        colPrecio.setCellFactory(column -> new TableCell<BoletoWrapper, Float>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", item));
                }
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
                // Usar Vistas.VENTAS en lugar de un String
                cont.setContenido(Vistas.VENTAS, "Agrega boletos usando el botón 'Agregar Boleto'");
                AnchorPane placeholderPane = comp.visual();

                // Configurar el placeholder de la tabla
                tblBoletos.setPlaceholder(placeholderPane);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Si falla, crear un placeholder simple
            Label label = new Label("No hay boletos agregados");
            label.setStyle("-fx-text-fill: #888888; -fx-font-size: 14px;");
            tblBoletos.setPlaceholder(label);
        }
    }

    @FXML
    public void btnCerrarClick() {
        if (stage != null) {
            stage.close();
        }
    }

    @FXML
    public void btnFacturarClick() {
        if (boletosObservable.isEmpty()) {
            mostrarAlerta("Error", "No hay boletos para facturar");
            return;
        }

        try {
            // Llama al formulario de vender donde están los datos de facturación
            StageBuilder builder = new StageBuilder()
                    .setContenido("formularios/formulario-vender.fxml") // Verifica que esta ruta sea correcta
                    .setModalidad(Modality.APPLICATION_MODAL)
                    .setTitulo("Datos de Facturación")
                    .setSize(new Dimension(600, 450));

            Pantalla pantalla = builder.construirPantalla();
            FormularioVenderController controlador = (FormularioVenderController) pantalla.componte().controlador();
            controlador.setStage(pantalla.pantalla());

            if (pantalla != null) {
                pantalla.pantalla().showAndWait();

                // Limpia la tabla visual si la venta se completó en la otra ventana
                if (servicioBoletosTemp.getBoletosSeleccionados().isEmpty()) {
                    boletosObservable.clear();
                    actualizarUI();
                }
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo abrir la ventana de facturación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void btnEliminarClick() {
        BoletoWrapper seleccionado = tblBoletos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            servicioBoletosTemp.eliminarBoleto(seleccionado);
            boletosObservable.remove(seleccionado);
            actualizarUI();
            mostrarAlerta("Información", "Boleto eliminado correctamente");
        } else {
            mostrarAlerta("Error", "Selecciona un boleto para eliminar");
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

            // Construir la pantalla para obtener el controlador
            Pantalla pantalla = builder.construirPantalla();

            if (pantalla == null || pantalla.componte() == null) {
                mostrarAlerta("Error", "No se pudo cargar el selector de funciones");
                return;
            }

            Stage selectorStage = pantalla.pantalla();

            // Obtener el controlador del selector
            Object controllerObj = pantalla.componte().controlador();
            if (!(controllerObj instanceof SelectorFuncionController)) {
                mostrarAlerta("Error", "Error al cargar el selector de funciones");
                return;
            }

            SelectorFuncionController controller = (SelectorFuncionController) controllerObj;
            controller.setStage(selectorStage);
            controller.setFormularioFactura(this);

            selectorStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir el selector de funciones: " + e.getMessage());
        }
    }

    private void actualizarUI() {
        // Actualizar tabla
        tblBoletos.setItems(boletosObservable);

        // Actualizar monto total
        double total = servicioBoletosTemp.getTotal();
        lblMonto.setText(String.format("$%.2f", total));
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void cerrar() {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Método para agregar boletos desde el selector de asientos
     */
    public void agregarBoletos(java.util.List<BoletoWrapper> nuevosBoletos) {
        if (nuevosBoletos == null || nuevosBoletos.isEmpty()) {
            return;
        }

        // Verificar duplicados antes de agregar
        ArrayList<BoletoWrapper> boletosAAgregar = new ArrayList<>();
        for (BoletoWrapper nuevoBoleto : nuevosBoletos) {
            boolean existe = false;
            for (BoletoWrapper existente : boletosObservable) {
                if (existente.getFuncion() != null && nuevoBoleto.getFuncion() != null) {
                    if (existente.getFuncion().getCodigo() == nuevoBoleto.getFuncion().getCodigo()) {
                        if (existente.getAsiento() != null && nuevoBoleto.getAsiento() != null) {
                            if (existente.getAsiento().getCodigo() == nuevoBoleto.getAsiento().getCodigo()) {
                                existe = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (!existe) {
                boletosAAgregar.add(nuevoBoleto);
            }
        }

        if (boletosAAgregar.isEmpty()) {
            mostrarAlerta("Información", "Los boletos seleccionados ya están agregados");
            return;
        }

        // Agregar al servicio temporal
        int agregados = servicioBoletosTemp.agregarBoletos(boletosAAgregar);

        if (agregados > 0) {
            // Actualizar la tabla solo con los que se agregaron realmente
            boletosObservable.addAll(boletosAAgregar);
            actualizarUI();
            mostrarAlerta("Éxito", agregados + " boleto(s) agregado(s) correctamente");
        } else {
            mostrarAlerta("Información", "No se pudieron agregar los boletos");
        }
    }

    /**
     * Elimina de la factura (y del servicio temporal) los boletos que
     * correspondan a una función específica y cuyo asiento esté en la lista
     * de códigos indicada. Se usa cuando el usuario deselecciona asientos en
     * el diálogo de selección de asientos después de haberlos confirmado
     * previamente.
     */
    public void eliminarBoletosPorAsientos(int codigoFuncion, List<Integer> codigosAsientos) {
        if (codigosAsientos == null || codigosAsientos.isEmpty()) {
            return;
        }

        List<BoletoWrapper> aEliminar = new ArrayList<>();
        for (BoletoWrapper wrapper : boletosObservable) {
            if (wrapper.getFuncion() != null && wrapper.getFuncion().getCodigo() == codigoFuncion
                    && wrapper.getAsiento() != null
                    && codigosAsientos.contains(wrapper.getAsiento().getCodigo())) {
                aEliminar.add(wrapper);
            }
        }

        if (aEliminar.isEmpty()) {
            return;
        }

        for (BoletoWrapper wrapper : aEliminar) {
            servicioBoletosTemp.eliminarBoleto(wrapper);
            boletosObservable.remove(wrapper);
        }

        actualizarUI();
    }
}