package proyecto.com.proyectobasesdedatos.controladores.componentes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import proyecto.com.proyectobasesdedatos.modelos.Asiento;
import proyecto.com.proyectobasesdedatos.modelos.Boleto;
import proyecto.com.proyectobasesdedatos.modelos.Funcion;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;

import java.text.SimpleDateFormat;

public class BoletoCompController implements Controlador{

    @FXML
    public Label lblPelicula, lblBoleto, lblCodigoFun, lblFecha, lblDuracion, lblSala, lblAsiento;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm a");

    public void setBoleto(Boleto boleto, int numero) {
        if (boleto == null) {
            return;
        }

        Funcion funcion = boleto.getFuncion();
        Asiento asiento = boleto.getAsiento();

        if (funcion != null) {
            lblCodigoFun.setText(String.format("FUN-%04d", funcion.getCodigo()));

            if (funcion.getPelicula() != null) {
                lblPelicula.setText(funcion.getPelicula().getNombre());
            }

            if (funcion.getFecha() != null && funcion.getHoraInicio() != null && funcion.getHoraFin() != null) {
                lblFecha.setText(DATE_FORMAT.format(funcion.getFecha()));
                lblDuracion.setText(TIME_FORMAT.format(funcion.getHoraInicio()) + " hasta " + TIME_FORMAT.format(funcion.getHoraFin()));
            }

            if (funcion.getSala() != null) {
                lblSala.setText(funcion.getSala().getNombre());
            }
        }

        if (asiento != null) {
            lblAsiento.setText(asiento.getFila() + asiento.getNumero());
        }

    }
}