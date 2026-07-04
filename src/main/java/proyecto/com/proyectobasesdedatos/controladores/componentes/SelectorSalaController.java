package proyecto.com.proyectobasesdedatos.controladores.componentes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import proyecto.com.proyectobasesdedatos.modelos.Sala;

public class SelectorSalaController {

    @FXML
    public Label lblInicio, lblFin, lblNombre;

    @FXML
    public TableView<Sala> tblSalas;

    @FXML
    public TableColumn<Sala, Integer> colCodigo, colCapacidad;

    @FXML
    public TableColumn<Sala, String> colNombre;

    public void btnCerrarClick(){

    }

    public void btnSeleccionarClick(){


    }


}
