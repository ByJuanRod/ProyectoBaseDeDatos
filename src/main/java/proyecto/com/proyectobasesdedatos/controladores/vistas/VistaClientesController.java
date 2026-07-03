package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import proyecto.com.proyectobasesdedatos.controladores.Vista;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.servicios.ServicioClientes;
import proyecto.com.proyectobasesdedatos.utilidades.FormatearTabla;

public class VistaClientesController implements Vista {
    private final ServicioClientes servicio = new ServicioClientes();

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    @FXML
    public TableView<Cliente> tblClientes;

    @FXML
    public TextField txtBuscar;

    @FXML
    public TableColumn<Cliente,Integer> colCodigo, colEntradas;

    @FXML
    public TableColumn<Cliente,String> colNombres, colApellidos, colTelefono;

    private FilteredList<Cliente> datosFiltrados;

    @FXML
    public void initialize() {
        configurarColumnas();
        cargar();
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> filtrar());
        tblClientes.widthProperty().addListener((obs, oldWidth, newWidth) -> FormatearTabla.ajustarAnchoColumnas(tblClientes));
    }

    public void btnEliminarClick(){
        Cliente cliente = tblClientes.getSelectionModel().getSelectedItem();
    }

    public void btnActualizarClick(){

    }

    public void btnRegistrarClick(){

    }

    public void txtBuscarKeyReleased(){
        filtrar();
    }

    @Override
    public void filtrar() {
        String textoBusqueda = txtBuscar.getText().trim().toLowerCase();

        datosFiltrados.setPredicate(parada -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = false;
            boolean coincideNombre = false;
            boolean coincideApellidos = false;
            boolean coincideTelefono = false;

            return coincideCodigo || coincideNombre || coincideApellidos || coincideTelefono;
        });
    }

    @Override
    public void cargar() {
        ObservableList<Cliente> datosOriginales = servicio.consultar();
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);

        tblClientes.setItems(datosFiltrados);
        filtrar();
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colEntradas.setCellValueFactory(new PropertyValueFactory<>("entradas"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    }
}
