package proyecto.com.proyectobasesdedatos.controladores.componentes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.modelos.Boleto;
import proyecto.com.proyectobasesdedatos.modelos.Venta;
import proyecto.com.proyectobasesdedatos.utilidades.CargadorFXML;
import proyecto.com.proyectobasesdedatos.utilidades.Componente;

import java.util.List;

public class VistaBoletosController implements Controlador {

    @FXML
    public Label lblCliente;

    @FXML
    public ScrollPane scrp;

    @FXML
    public VBox pnlContenedor;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /** Llena la pantalla con los datos de una venta ya guardada. */
    public void cargarVenta(Venta venta) {
        if (venta == null) {
            return;
        }

        if (venta.getCliente() != null) {
            lblCliente.setText(venta.getCliente().getNombres() + " " + venta.getCliente().getApellidos());
        }

        pnlContenedor.getChildren().clear();

        List<Boleto> boletos = venta.getBoletos();
        if (boletos == null) {
            return;
        }

        int numero = 1;
        for (Boleto boleto : boletos) {
            try {
                CargadorFXML cargador = new CargadorFXML();
                Componente comp = cargador.cargarComponenteConControlador("componentes/boleto-comp.fxml");
                BoletoCompController controlador = (BoletoCompController) comp.controlador();
                controlador.setBoleto(boleto, numero);
                pnlContenedor.getChildren().add(comp.visual());
                numero++;
            } catch (Exception e) {
                System.err.println("Error al cargar la tarjeta del boleto: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void btnCerrarClick() {
        if (stage != null) {
            stage.close();
        }
    }
}