package proyecto.com.proyectobasesdedatos.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import proyecto.com.proyectobasesdedatos.servicios.ServicioInformes;

public class InformesController {
    private final ServicioInformes serInf = new ServicioInformes();

    @FXML
    public Label lblClientes, lblFunciones, lblPeliculas;

    @FXML
    public void initialize() {
        lblClientes.setText(serInf.getCantClientes() + "");
        lblFunciones.setText(serInf.getCantFunciones() + "");
        lblPeliculas.setText(serInf.getCantPeliculas() + "");
    }

}
