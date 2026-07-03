package proyecto.com.proyectobasesdedatos.controladores.formularios;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import proyecto.com.proyectobasesdedatos.modelos.Boleto;

public class FormularioFacturarController {

    @FXML
    public Button btnVender, btnEliminar;

    @FXML
    public TextField txtMonto;

    @FXML
    public Label lblMonto;

    @FXML
    public TableView<Boleto> tblBoletos;

    @FXML
    public TableColumn<Boleto,String> colAsiento;

    @FXML
    public TableColumn<Boleto,Float> colPrecio;

    @FXML
    public TableColumn<Boleto,Integer> colFuncion;

    public void btnCerrarClick(){

    }

    public void btnNuevoClick(){

    }

    public void btnVenderClick(){

    }

    public void btnEliminarClick(){

    }

    public void btnExistenteClick(){

    }

    public void btnAgregarClick(){

    }
}
