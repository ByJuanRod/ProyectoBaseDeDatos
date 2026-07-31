package proyecto.com.proyectobasesdedatos.controladores.componentes;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioFacturaController;
import proyecto.com.proyectobasesdedatos.modelos.Funcion;
import proyecto.com.proyectobasesdedatos.servicios.ServicioFunciones;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class SelectorFuncionController implements Controlador {
    private Stage stage;
    private FormularioFacturaController formularioFactura;

    @FXML
    private TextField txtFiltrar;

    @FXML
    private AnchorPane rootPane;

    private List<Funcion> funcionesDisponibles = new ArrayList<>();
    private final List<FuncionCompController> funcionControllers = new ArrayList<>();
    private Consumer<Funcion> onFuncionSeleccionada;

    private VBox contenedorFunciones;
    private ScrollPane scrollPane;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    @FXML
    public void initialize() {
        // Crear el ScrollPane
        scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Crear el contenedor de funciones
        contenedorFunciones = new VBox(10);
        contenedorFunciones.setAlignment(Pos.TOP_CENTER);
        contenedorFunciones.setStyle("-fx-background-color: transparent;");
        contenedorFunciones.setPrefWidth(900);

        scrollPane.setContent(contenedorFunciones);
        scrollPane.setPrefWidth(920);

        // Agregar el ScrollPane al rootPane
        AnchorPane.setLeftAnchor(scrollPane, 10.0);
        AnchorPane.setRightAnchor(scrollPane, 10.0);
        AnchorPane.setTopAnchor(scrollPane, 210.0);
        AnchorPane.setBottomAnchor(scrollPane, 10.0);
        rootPane.getChildren().add(scrollPane);

        // Configurar el filtro
        txtFiltrar.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarFunciones(newValue);
        });

        // Cargar las funciones después de que la UI esté lista
        Platform.runLater(() -> {
            cargarFuncionesDesdeServicio();
        });
    }

    /**
     * Carga las funciones desde el servicio de funciones
     */
    private void cargarFuncionesDesdeServicio() {
        try {
            ServicioFunciones servicio = ServicioFunciones.getInstance();
            servicio.cargar();

            List<Funcion> funciones = servicio.obtenerTodos();

            if (funciones != null && !funciones.isEmpty()) {
                funcionesDisponibles = funciones;
                mostrarFunciones(funcionesDisponibles);
                System.out.println("Funciones cargadas: " + funcionesDisponibles.size());
            } else {
                mostrarMensajeSinFunciones();
                System.out.println("No se encontraron funciones disponibles");
            }

        } catch (Exception e) {
            System.err.println("Error al cargar las funciones: " + e.getMessage());
            e.printStackTrace();
            mostrarMensajeSinFunciones();
        }
    }

    private void mostrarMensajeSinFunciones() {
        contenedorFunciones.getChildren().clear();
        javafx.scene.control.Label mensaje = new javafx.scene.control.Label("No hay funciones disponibles");
        mensaje.setTextFill(javafx.scene.paint.Color.WHITE);
        mensaje.setFont(javafx.scene.text.Font.font("Century Gothic", 18));
        mensaje.setAlignment(Pos.CENTER);
        mensaje.setPrefWidth(900);
        contenedorFunciones.getChildren().add(mensaje);
    }

    private void mostrarFunciones(List<Funcion> funciones) {
        contenedorFunciones.getChildren().clear();
        funcionControllers.clear();

        if (funciones == null || funciones.isEmpty()) {
            mostrarMensajeSinFunciones();
            return;
        }

        ServicioFunciones servicio = ServicioFunciones.getInstance();

        for (Funcion funcion : funciones) {
            try {
                // Verificar si la función tiene capacidad disponible
                int capacidadRestante = servicio.getCapacidadRestante(funcion.getCodigo());

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/proyecto/com/proyectobasesdedatos/componentes/funcion-comp.fxml")
                );
                AnchorPane funcionPane = loader.load();
                FuncionCompController controller = loader.getController();

                controller.setSelectorParent(this);
                controller.setFormularioFactura(formularioFactura);

                controller.setFuncion(funcion);
                controller.setOnSeleccionar(funcionSeleccionada -> {
                    if (onFuncionSeleccionada != null) {
                        onFuncionSeleccionada.accept(funcionSeleccionada);
                    }
                });

                funcionPane.setPrefWidth(900);
                contenedorFunciones.getChildren().add(funcionPane);
                funcionControllers.add(controller);

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error al cargar el componente de función: " + e.getMessage());
            }
        }
    }

    private void filtrarFunciones(String filtro) {
        if (filtro == null || filtro.trim().isEmpty()) {
            mostrarFunciones(funcionesDisponibles);
            return;
        }

        String filtroLower = filtro.toLowerCase().trim();
        List<Funcion> funcionesFiltradas = funcionesDisponibles.stream()
                .filter(funcion -> {
                    // Buscar por código
                    if (String.valueOf(funcion.getCodigo()).contains(filtroLower)) {
                        return true;
                    }

                    // Buscar por nombre de película
                    if (funcion.getPelicula() != null &&
                            funcion.getPelicula().getNombre() != null &&
                            funcion.getPelicula().getNombre().toLowerCase().contains(filtroLower)) {
                        return true;
                    }

                    // Buscar por nombre de sala
                    if (funcion.getSala() != null &&
                            funcion.getSala().getNombre() != null &&
                            funcion.getSala().getNombre().toLowerCase().contains(filtroLower)) {
                        return true;
                    }

                    // Buscar por fecha
                    if (funcion.getFecha() != null) {
                        String fechaStr = DATE_FORMAT.format(funcion.getFecha());
                        if (fechaStr.contains(filtroLower)) {
                            return true;
                        }
                    }

                    // Buscar por hora de inicio
                    if (funcion.getHoraInicio() != null) {
                        String horaStr = TIME_FORMAT.format(funcion.getHoraInicio());
                        if (horaStr.contains(filtroLower)) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());

        mostrarFunciones(funcionesFiltradas);
    }

    public void setFuncionesDisponibles(List<Funcion> funciones) {
        this.funcionesDisponibles = funciones != null ? funciones : new ArrayList<>();
        mostrarFunciones(this.funcionesDisponibles);
    }

    public void setOnFuncionSeleccionada(Consumer<Funcion> callback) {
        this.onFuncionSeleccionada = callback;
    }

    public List<Funcion> getFuncionesDisponibles() {
        return funcionesDisponibles;
    }

    public void refrescarFunciones() {
        cargarFuncionesDesdeServicio();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setFormularioFactura(FormularioFacturaController formularioFactura) {
        this.formularioFactura = formularioFactura;
    }

    public void cerrarSelector() {
        if (stage != null) {
            stage.close();
        }
    }
}