package proyecto.com.proyectobasesdedatos.controladores.componentes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioFacturaController;
import proyecto.com.proyectobasesdedatos.modelos.Asiento;
import proyecto.com.proyectobasesdedatos.modelos.Funcion;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;
import proyecto.com.proyectobasesdedatos.modelos.Sala;
import proyecto.com.proyectobasesdedatos.modelos.wrappers.BoletoWrapper;
import proyecto.com.proyectobasesdedatos.servicios.ServicioBoletosTemporal;
import proyecto.com.proyectobasesdedatos.servicios.ServicioFunciones;
import proyecto.com.proyectobasesdedatos.utilidades.Pantalla;
import proyecto.com.proyectobasesdedatos.utilidades.StageBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FuncionCompController {

    @FXML
    private Label lblCodigoFun;

    @FXML
    private Label lblNombre;

    @FXML
    private Label lblFecha;

    @FXML
    private Label lblDuracion;

    @FXML
    private Label lblCapacidad;

    @FXML
    private Label lblClasificacion;

    @FXML
    private Button btnSeleccionar;

    private Funcion funcionActual;
    private Consumer<Funcion> onSeleccionarCallback;
    private ServicioFunciones servicioFunciones;
    private ServicioBoletosTemporal servicioBoletosTemp;
    private Stage stage;
    private SelectorFuncionController selectorParent;
    private FormularioFacturaController formularioFactura;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm a");

    @FXML
    public void initialize() {
        servicioFunciones = ServicioFunciones.getInstance();
        servicioBoletosTemp = ServicioBoletosTemporal.getInstance();
    }

    public void setFuncion(Funcion funcion) {
        this.funcionActual = funcion;
        actualizarDatos();
    }

    private void actualizarDatos() {
        if (funcionActual == null) return;

        Pelicula pelicula = funcionActual.getPelicula();
        Sala sala = funcionActual.getSala();

        lblCodigoFun.setText(String.format("FUN-%04d", funcionActual.getCodigo()));

        if (pelicula != null) {
            lblNombre.setText(pelicula.getNombre());
            String clasificacion = pelicula.getClasificacion();
            if (clasificacion != null && !clasificacion.trim().isEmpty()) {
                lblClasificacion.setText(clasificacion);
            } else {
                lblClasificacion.setText("N/A");
            }
        }

        if (funcionActual.getFecha() != null) {
            lblFecha.setText(DATE_FORMAT.format(funcionActual.getFecha()));
        }

        if (funcionActual.getHoraInicio() != null && funcionActual.getHoraFin() != null) {
            String duracionStr = TIME_FORMAT.format(funcionActual.getHoraInicio()) +
                    " hasta " +
                    TIME_FORMAT.format(funcionActual.getHoraFin());
            lblDuracion.setText(duracionStr);
        }

        if (sala != null) {
            int[] capacidadData = servicioFunciones.getCapacidadRestanteConTotal(funcionActual.getCodigo());
            int capacidadRestante = capacidadData[0];
            int capacidadTotal = capacidadData[1];

            lblCapacidad.setText(capacidadRestante + " / " + capacidadTotal + " Personas");

            if (capacidadRestante <= 0) {
                lblCapacidad.setStyle("-fx-text-fill: #D62828;");
                btnSeleccionar.setDisable(true);
                btnSeleccionar.setText("Completo");
                btnSeleccionar.setStyle("-fx-background-color: #666666; -fx-background-radius: 10px;");
            } else if (capacidadRestante <= 5) {
                lblCapacidad.setStyle("-fx-text-fill: #FF6B6B;");
                btnSeleccionar.setDisable(false);
                btnSeleccionar.setText("¡Últimos asientos!");
                btnSeleccionar.setStyle("-fx-background-color: #D62828; -fx-background-radius: 10px;");
            } else {
                lblCapacidad.setStyle("-fx-text-fill: #ffc400;");
                btnSeleccionar.setDisable(false);
                btnSeleccionar.setText("Seleccionar");
                btnSeleccionar.setStyle("-fx-background-color: #D62828; -fx-background-radius: 10px;");
            }
        }
    }

    public void setOnSeleccionar(Consumer<Funcion> callback) {
        this.onSeleccionarCallback = callback;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setSelectorParent(SelectorFuncionController parent) {
        this.selectorParent = parent;
    }

    public void setFormularioFactura(FormularioFacturaController formularioFactura) {
        this.formularioFactura = formularioFactura;
    }

    @FXML
    private void btnSeleccionarClick() {
        if (funcionActual == null) {
            System.out.println("Error: Función actual es null");
            return;
        }

        int capacidadRestante = servicioFunciones.getCapacidadRestante(funcionActual.getCodigo());
        if (capacidadRestante <= 0) {
            mostrarAlerta("Información", "No hay capacidad disponible para esta función");
            return;
        }

        // Verificar cuántos asientos ya tiene seleccionados el usuario para esta función
        List<Integer> asientosYaSeleccionados = servicioBoletosTemp.getAsientosSeleccionadosParaFuncion(funcionActual.getCodigo());
        int capacidadDisponibleParaUsuario = capacidadRestante - asientosYaSeleccionados.size();

        if (capacidadDisponibleParaUsuario <= 0) {
            mostrarAlerta("Información", "Ya has seleccionado todos los asientos disponibles para esta función");
            return;
        }

        try {
            // Usar StageBuilder para crear la ventana de selección de asientos
            StageBuilder stageBuilder = new StageBuilder()
                    .setTitulo("Selección de Asientos")
                    .setModalidad(Modality.APPLICATION_MODAL)
                    .setContenido("componentes/seleccion-asientos-view.fxml");

            // Construir la pantalla para obtener el controlador
            Pantalla pantalla = stageBuilder.construirPantalla();

            if (pantalla == null || pantalla.componte() == null) {
                mostrarAlerta("Error", "No se pudo cargar la vista de selección de asientos");
                return;
            }

            Stage asientosStage = pantalla.pantalla();

            // Obtener el controlador de la pantalla
            Object controllerObj = pantalla.componte().controlador();
            if (!(controllerObj instanceof SeleccionAsientosController)) {
                mostrarAlerta("Error", "Error al cargar el controlador de selección de asientos");
                return;
            }

            SeleccionAsientosController controller = (SeleccionAsientosController) controllerObj;

            // PRIMERO: Pasar la función
            controller.setFuncion(funcionActual);
            // SEGUNDO: Pasar los asientos del usuario
            controller.setAsientosDelUsuario(asientosYaSeleccionados);
            // TERCERO: Pasar el formulario
            controller.setFormularioFactura(formularioFactura);
            // CUARTO: Establecer el stage
            controller.setStage(asientosStage);
            // QUINTO: Cargar los asientos (después de tener todos los datos)
            controller.cargarAsientos();

            // Configurar el callback para cuando se confirmen los asientos
            controller.setOnConfirmar(asientosSeleccionados -> {
                System.out.println("Asientos seleccionados: " + asientosSeleccionados.size());

                if (asientosSeleccionados != null && !asientosSeleccionados.isEmpty()) {
                    // Crear boletos a partir de los asientos seleccionados
                    List<BoletoWrapper> boletosWrapper = new ArrayList<>();

                    // Obtener el precio de la función
                    float precio = (float) funcionActual.getPrecioEntrada();

                    for (Asiento asiento : asientosSeleccionados) {
                        BoletoWrapper wrapper = new BoletoWrapper(funcionActual, asiento, precio);
                        boletosWrapper.add(wrapper);
                    }

                    // Agregar los boletos al formulario de factura
                    if (formularioFactura != null && !boletosWrapper.isEmpty()) {
                        formularioFactura.agregarBoletos(boletosWrapper);
                    }

                    // Cerrar el selector de funciones
                    if (selectorParent != null) {
                        selectorParent.cerrarSelector();
                    }

                    // Notificar al callback
                    if (onSeleccionarCallback != null) {
                        onSeleccionarCallback.accept(funcionActual);
                    }
                }

                asientosStage.close();
            });

            asientosStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista de selección de asientos: " + e.getMessage());
            mostrarAlerta("Error", "No se pudo abrir la selección de asientos: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public Funcion getFuncionActual() {
        return funcionActual;
    }

    public int getCapacidadRestante() {
        if (funcionActual == null) {
            return 0;
        }
        return servicioFunciones.getCapacidadRestante(funcionActual.getCodigo());
    }
}