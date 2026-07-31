package proyecto.com.proyectobasesdedatos.controladores.componentes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;

public class SelectorClienteController {

    @FXML
    public TextField txtFiltrar;

    @FXML
    public TableView<Cliente> tblClientes;

    @FXML
    public TableColumn<Cliente, String> colNombre, colTelefono, colApellido;

    @FXML
    public TableColumn<Cliente, Integer> colEntradas;

    @FXML
    public Label lblCliente;

    public void btnCerrarClick(){


    }

    public void btnSeleccionarClick(){

    }

    public void txtFiltrarKeyReleased(){


    }

}
