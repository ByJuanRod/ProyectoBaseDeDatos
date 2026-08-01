package proyecto.com.proyectobasesdedatos.controladores.componentes;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioFacturaController;
import proyecto.com.proyectobasesdedatos.modelos.Asiento;
import proyecto.com.proyectobasesdedatos.modelos.Funcion;
import proyecto.com.proyectobasesdedatos.servicios.ServicioAsientos;
import proyecto.com.proyectobasesdedatos.servicios.ServicioBoletos;
import proyecto.com.proyectobasesdedatos.servicios.ServicioFunciones;
import proyecto.com.proyectobasesdedatos.servicios.ServicioBoletosTemporal;

import java.util.*;
import java.util.function.Consumer;

public class SeleccionAsientosController implements Controlador {

    @FXML
    public Label lblSala;

    @FXML
    public Label lblFuncion;

    @FXML
    public Label lblSeleccionados;

    @FXML
    public FlowPane pnlContenedor;

    private Funcion funcionActual;
    private final List<Asiento> asientosDisponibles = new ArrayList<>();
    private final List<Asiento> asientosSeleccionados = new ArrayList<>();
    private List<Integer> asientosOcupadosPorOtros = new ArrayList<>();
    private List<Integer> asientosDelUsuarioActual = new ArrayList<>();
    private Map<String, List<Asiento>> asientosPorFila = new LinkedHashMap<>();
    private Consumer<List<Asiento>> onConfirmarCallback;
    private Stage stage;
    private ServicioAsientos servicioAsientos;
    private ServicioBoletos servicioBoletos;
    private ServicioBoletosTemporal servicioBoletosTemp;
    private FormularioFacturaController formularioFactura;

    private static final int ASIENTOS_POR_FILA = 10;

    // Mapa para almacenar los paneles de los asientos y poder actualizarlos
    private final Map<Integer, BorderPane> asientoPanes = new HashMap<>();
    private boolean asientosCargados = false;

    @FXML
    public void initialize() {
        ServicioFunciones.getInstance();
        servicioAsientos = ServicioAsientos.getInstance();
        servicioBoletos = ServicioBoletos.getInstance();
        servicioBoletosTemp = ServicioBoletosTemporal.getInstance();

        pnlContenedor.setHgap(10);
        pnlContenedor.setVgap(10);
        pnlContenedor.setAlignment(Pos.CENTER);
        pnlContenedor.setPrefWrapLength(600);

        System.out.println("SeleccionAsientosController initialized");
    }

    public void setFuncion(Funcion funcion) {
        this.funcionActual = funcion;

        if (funcion != null && funcion.getSala() != null) {
            lblSala.setText(funcion.getSala().getNombre());
            lblFuncion.setText(String.format("FUN-%04d", funcion.getCodigo()));
            System.out.println("Función establecida: " + funcion.getCodigo() + " - Sala: " + funcion.getSala().getNombre());
        } else {
            System.out.println("Error: Función o sala null");
        }
    }

    public void setFormularioFactura(FormularioFacturaController formularioFactura) {
        this.formularioFactura = formularioFactura;
    }

    public void setAsientosDelUsuario(List<Integer> asientosDelUsuario) {
        if (asientosDelUsuario != null) {
            this.asientosDelUsuarioActual = new ArrayList<>(asientosDelUsuario);
            System.out.println("Asientos del usuario establecidos: " + this.asientosDelUsuarioActual.size());
        } else {
            this.asientosDelUsuarioActual = new ArrayList<>();
        }
    }

    public void cargarAsientos() {
        if (funcionActual == null) {
            System.err.println("Error: No hay función seleccionada");
            return;
        }

        if (funcionActual.getSala() == null) {
            System.err.println("Error: La función no tiene sala asociada");
            return;
        }

        int codigoSala = funcionActual.getSala().getCodigo();
        int codigoFuncion = funcionActual.getCodigo();

        System.out.println("Cargando asientos para sala: " + codigoSala + ", función: " + codigoFuncion);

        List<Asiento> todosAsientos = servicioAsientos.obtenerPorSala(codigoSala);

        if (todosAsientos == null || todosAsientos.isEmpty()) {
            System.err.println("Error: No se encontraron asientos para la sala " + codigoSala);
            mostrarMensajeSinAsientos();
            return;
        }

        System.out.println("Asientos encontrados: " + todosAsientos.size());

        // Obtener asientos ocupados por otros (no del usuario actual)
        asientosOcupadosPorOtros = servicioBoletos.obtenerCodigosAsientosOcupadosPorFuncion(codigoFuncion);

        // Remover los asientos del usuario actual de los ocupados por otros
        asientosOcupadosPorOtros.removeAll(asientosDelUsuarioActual);

        System.out.println("Asientos ocupados por otros: " + asientosOcupadosPorOtros.size());
        System.out.println("Asientos del usuario actual: " + asientosDelUsuarioActual.size());

        pnlContenedor.getChildren().clear();
        asientosDisponibles.clear();
        asientosSeleccionados.clear();
        asientosPorFila.clear();
        asientoPanes.clear();

        if (todosAsientos.stream().anyMatch(a -> a.getFila() == null || a.getFila().isEmpty())) {
            organizarAsientosPorFilaSimulada(todosAsientos);
        } else {
            organizarAsientosPorFila(todosAsientos);
        }

        crearFilasDeAsientos();
        actualizarContador();
        asientosCargados = true;

        System.out.println("Asientos cargados: " + todosAsientos.size() +
                " (Disponibles: " + asientosDisponibles.size() +
                ", Ocupados por otros: " + asientosOcupadosPorOtros.size() +
                ", Del usuario actual: " + asientosDelUsuarioActual.size() + ")");
    }

    private void mostrarMensajeSinAsientos() {
        pnlContenedor.getChildren().clear();
        Label mensaje = new Label("No hay asientos disponibles para esta sala");
        mensaje.setTextFill(javafx.scene.paint.Color.WHITE);
        mensaje.setFont(javafx.scene.text.Font.font("Century Gothic", 16));
        mensaje.setAlignment(Pos.CENTER);
        pnlContenedor.getChildren().add(mensaje);
    }

    private void organizarAsientosPorFila(List<Asiento> asientos) {
        for (Asiento asiento : asientos) {
            String fila = asiento.getFila() != null ? asiento.getFila() : "A";
            asientosPorFila.computeIfAbsent(fila, k -> new ArrayList<>()).add(asiento);
        }

        Map<String, List<Asiento>> sortedMap = new LinkedHashMap<>();
        asientosPorFila.keySet().stream()
                .sorted()
                .forEach(key -> sortedMap.put(key, asientosPorFila.get(key)));
        asientosPorFila = sortedMap;

        System.out.println("Filas organizadas: " + asientosPorFila.size());
    }

    private void organizarAsientosPorFilaSimulada(List<Asiento> asientos) {
        asientos.sort(Comparator.comparingInt(Asiento::getNumero));

        String[] filas = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P"};
        int filaIndex = 0;
        int contador = 0;

        for (Asiento asiento : asientos) {
            String fila = filas[Math.min(filaIndex, filas.length - 1)];
            asiento.setFila(fila);
            asientosPorFila.computeIfAbsent(fila, k -> new ArrayList<>()).add(asiento);
            contador++;

            if (contador % ASIENTOS_POR_FILA == 0) {
                filaIndex++;
            }
        }

        System.out.println("Filas simuladas: " + asientosPorFila.size());
    }

    private void crearFilasDeAsientos() {
        if (asientosPorFila.isEmpty()) {
            System.err.println("Error: No hay filas para mostrar");
            mostrarMensajeSinAsientos();
            return;
        }

        for (Map.Entry<String, List<Asiento>> entry : asientosPorFila.entrySet()) {
            String fila = entry.getKey();
            List<Asiento> asientosFila = entry.getValue();

            asientosFila.sort(Comparator.comparingInt(Asiento::getNumero));

            HBox filaHBox = new HBox(12);
            filaHBox.setAlignment(Pos.CENTER_LEFT);
            filaHBox.setPadding(new Insets(4, 0, 4, 0));
            filaHBox.setPrefHeight(45);

            Label lblFila = new Label(fila);
            lblFila.setTextFill(javafx.scene.paint.Color.BLACK);
            lblFila.setFont(javafx.scene.text.Font.font("Century Gothic Bold", 16));
            lblFila.setPrefWidth(35);
            lblFila.setPrefHeight(35);
            lblFila.setAlignment(Pos.CENTER);
            lblFila.setStyle("-fx-background-color: #1A2A4F; -fx-background-radius: 50%; -fx-border-color: #CCCCCC; -fx-border-radius: 50%; -fx-border-width: 1px;");

            HBox asientosHBox = new HBox(8);
            asientosHBox.setAlignment(Pos.CENTER_LEFT);

            for (Asiento asiento : asientosFila) {
                boolean ocupadoPorOtro = asientosOcupadosPorOtros.contains(asiento.getCodigo());
                boolean esDelUsuario = asientosDelUsuarioActual.contains(asiento.getCodigo());
                BorderPane asientoPane = crearBotonAsiento(asiento, ocupadoPorOtro, esDelUsuario);
                asientosHBox.getChildren().add(asientoPane);

                if (!ocupadoPorOtro && !esDelUsuario) {
                    asientosDisponibles.add(asiento);
                }

                // Si es del usuario, agregarlo a la lista de seleccionados
                if (esDelUsuario) {
                    asientosSeleccionados.add(asiento);
                }
            }

            filaHBox.getChildren().addAll(lblFila, asientosHBox);
            pnlContenedor.getChildren().add(filaHBox);
        }

        System.out.println("Filas creadas: " + pnlContenedor.getChildren().size());
    }

    private BorderPane crearBotonAsiento(Asiento asiento, boolean ocupadoPorOtro, boolean esDelUsuario) {
        BorderPane pane = new BorderPane();
        pane.setPrefSize(36, 36);
        pane.setStyle("-fx-background-radius: 50%;");
        pane.setUserData(asiento.getCodigo());

        // Guardar referencia al panel
        asientoPanes.put(asiento.getCodigo(), pane);

        if (ocupadoPorOtro) {
            // Ocupado por otro usuario - color rojo, no interactivo
            pane.setStyle("-fx-background-color: #d62828; -fx-background-radius: 50%;");
            pane.setCursor(javafx.scene.Cursor.DEFAULT);
        } else if (esDelUsuario) {
            // Seleccionado por el usuario actual - color fucsia/rojo claro
            pane.setStyle("-fx-background-color: #f63049; -fx-background-radius: 50%;");
            pane.setCursor(javafx.scene.Cursor.HAND);
            pane.setOnMouseClicked(event -> toggleAsientoSeleccionado(asiento, pane));
        } else {
            // Disponible - color verde
            pane.setStyle("-fx-background-color: #31694e; -fx-background-radius: 50%;");
            pane.setCursor(javafx.scene.Cursor.HAND);
            pane.setOnMouseClicked(event -> toggleAsientoSeleccionado(asiento, pane));
        }

        Label label = new Label(String.valueOf(asiento.getNumero()));
        label.setTextFill(javafx.scene.paint.Color.WHITE);
        label.setFont(javafx.scene.text.Font.font("Century Gothic Bold", 12));
        label.setAlignment(Pos.CENTER);
        pane.setCenter(label);

        return pane;
    }

    private void toggleAsientoSeleccionado(Asiento asiento, BorderPane pane) {
        // Verificar si el asiento está ocupado por otro usuario
        if (asientosOcupadosPorOtros.contains(asiento.getCodigo())) {
            return; // No se puede interactuar con asientos de otros
        }

        if (asientosSeleccionados.contains(asiento)) {
            // Deseleccionar
            asientosSeleccionados.remove(asiento);
            pane.setStyle("-fx-background-color: #31694e; -fx-background-radius: 50%;");
            // Remover de la lista del usuario actual
            asientosDelUsuarioActual.remove(Integer.valueOf(asiento.getCodigo()));
        } else {
            // Seleccionar
            asientosSeleccionados.add(asiento);
            pane.setStyle("-fx-background-color: #f63049; -fx-background-radius: 50%;");
            // Agregar a la lista del usuario actual
            asientosDelUsuarioActual.add(asiento.getCodigo());
        }
        actualizarContador();
    }

    private void actualizarContador() {
        if (lblSeleccionados != null) {
            lblSeleccionados.setText(String.valueOf(asientosSeleccionados.size()));
        }
    }

    @FXML
    public void btnConfirmarClick() {
        // Nota: ya NO se bloquea la confirmación cuando la lista queda vacía.
        // Si el usuario deseleccionó todos sus asientos, el callback debe
        // ejecutarse igual para que quien nos llamó (FuncionCompController)
        // pueda detectar la diferencia y eliminar esos boletos de la factura.
        System.out.println("Confirmando " + asientosSeleccionados.size() + " asientos");

        if (onConfirmarCallback != null) {
            onConfirmarCallback.accept(new ArrayList<>(asientosSeleccionados));
        }
    }

    @FXML
    public void btnCerrarClick() {
        cerrar();
    }

    private void cerrar() {
        if (stage != null) {
            stage.close();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setOnConfirmar(Consumer<List<Asiento>> callback) {
        this.onConfirmarCallback = callback;
    }

}