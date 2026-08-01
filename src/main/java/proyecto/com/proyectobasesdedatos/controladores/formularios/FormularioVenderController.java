package proyecto.com.proyectobasesdedatos.controladores.formularios;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.componentes.SelectorClienteController;
import proyecto.com.proyectobasesdedatos.modelos.*;
import proyecto.com.proyectobasesdedatos.modelos.wrappers.BoletoWrapper;
import proyecto.com.proyectobasesdedatos.servicios.ServicioBoletos;
import proyecto.com.proyectobasesdedatos.servicios.ServicioBoletosTemporal;
import proyecto.com.proyectobasesdedatos.servicios.ServicioVentas;
import proyecto.com.proyectobasesdedatos.utilidades.Pantalla;
import proyecto.com.proyectobasesdedatos.utilidades.StageBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormularioVenderController implements Controlador {

    // Debe coincidir exactamente con el umbral usado en el trigger
    // trg_boleto_fidelidad. Se usa AQUÍ ÚNICAMENTE para mostrar una vista
    // previa en pantalla; no escribe nada en la base de datos. La fuente
    // de verdad real sigue siendo el trigger al momento de facturar.
    private static final int PUNTOS_PARA_BOLETO_GRATIS = 9;

    private Stage stage;

    private Cliente cliente;

    private final ServicioBoletosTemporal servicioBoletosTemp = ServicioBoletosTemporal.getInstance();
    private final ServicioVentas servicioVentas = ServicioVentas.getInstance();
    private final ServicioBoletos servicioBoletos = ServicioBoletos.getInstance();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public TextField txtCliente;

    @FXML
    public Label lblMonto, lblDescuento, lblTotal;

    @FXML
    public void initialize() {
        txtCliente.setEditable(false);
        actualizarUI();
    }

    private double calcularDescuentoEstimado(List<BoletoWrapper> carrito, int puntosIniciales) {
        double montoDescuento = 0.0;
        int puntos = puntosIniciales;

        for (BoletoWrapper boleto : carrito) {
            if (puntos == PUNTOS_PARA_BOLETO_GRATIS) {
                montoDescuento += boleto.getPrecio();
                puntos = 0;
            } else {
                puntos++;
            }
        }

        return montoDescuento;
    }

    private void actualizarUI() {
        List<BoletoWrapper> boletosCarrito = servicioBoletosTemp.getBoletosSeleccionados();
        double totalOriginal = servicioBoletosTemp.getTotal();

        double montoDescuentoEstimado = 0.0;
        if (cliente != null && !boletosCarrito.isEmpty()) {
            montoDescuentoEstimado = calcularDescuentoEstimado(boletosCarrito, cliente.getCantidadEntradas());
        }

        lblTotal.setText(String.format("$%.2f", totalOriginal));
        double totalEstimado = totalOriginal - montoDescuentoEstimado;
        lblMonto.setText(String.format("$%.2f", totalEstimado));

        if (montoDescuentoEstimado > 0) {
            lblDescuento.setText("-$" + String.format("%.2f", montoDescuentoEstimado));
        } else {
            lblDescuento.setText("$0.00");
        }
    }

    public void btnSeleccionarClick() {
        Pantalla pnt = new StageBuilder()
                .setContenido("componentes/selector-cliente.fxml")
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo("Sleector de Clientes")
                .setSize(new Dimension(780, 600))
                .construirPantalla();

        SelectorClienteController controlador = (SelectorClienteController) pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setOnSeleccionar(this::setClienteSeleccionado);
        pnt.pantalla().show();
    }

    public void setClienteSeleccionado(Cliente cliente) {
        if (cliente != null) {
            this.cliente = cliente;
            txtCliente.setText(cliente.getNombres() + " " + cliente.getApellidos());
            actualizarUI();
        }
    }

    public void btnFacturarClick() {
        if (cliente == null) {
            mostrarAlerta("Error", "Selecciona un cliente antes de facturar");
            return;
        }

        List<BoletoWrapper> boletosWrapper = servicioBoletosTemp.getBoletosSeleccionados();
        if (boletosWrapper.isEmpty()) {
            mostrarAlerta("Error", "No hay boletos para facturar");
            return;
        }

        Venta venta = new Venta();
        venta.setFecha(new java.sql.Date(System.currentTimeMillis()));
        venta.setHora(new java.sql.Time(System.currentTimeMillis()));
        venta.setCliente(cliente);

        venta.setEmpleado(Usuario.empleado);
        venta.setSucursal(Usuario.sucursal);

        List<Boleto> boletos = new ArrayList<>();
        for (BoletoWrapper wrapper : boletosWrapper) {
            Boleto boleto = wrapper.crearBoleto();
            boleto.setVenta(venta);
            boletos.add(boleto);
        }
        venta.setBoletos(boletos);

        venta.setPrecioTotal(servicioBoletosTemp.getTotal());

        boolean exito = servicioVentas.guardar(venta);

        if (exito) {
            servicioBoletosTemp.limpiar();
            servicioBoletos.recargar();

            mostrarAlerta("Éxito", String.format(
                    "Venta registrada correctamente.\nTotal cobrado: $%.2f",
                    venta.getPrecioTotal()));

            if (stage != null) {
                stage.close();
            }
        } else {
            mostrarAlerta("Error", "No se pudo registrar la venta. Intenta nuevamente.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void btnCerrarClick() {
        stage.close();
    }

}