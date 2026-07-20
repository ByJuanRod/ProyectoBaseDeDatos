package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioClienteController;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.servicios.ServicioClientes;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

import java.awt.*;

public class VistaClientesController implements Vista<Cliente> {
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
        Inicializador.inicializar(this,tblClientes,txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder(){
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.CLIENTES,"No se han encontrado clientes.");
        return comp.visual();
    }

    public void btnEliminarClick(){
        try{
            Cliente cliente = tblClientes.getSelectionModel().getSelectedItem();
            Alert alt = AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("¿Está seguro de que desea eliminar este registro?");
            alt.showAndWait().ifPresent(resp -> {
                if(resp == ButtonType.OK){
                    if(servicio.eliminar(cliente)){
                        AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("El cliente ha sido eliminado.").show();
                    }
                    else{
                        AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("No se puede eliminar este cliente.").show();
                    }
                }
            });
        }
        catch (Exception e){
            AlertFactory.obtenerAlerta(TipoAlerta.ERROR).crearAlerta("No se ha logrado eliminar el registro.").show();
        }
    }

    public void btnActualizarClick(){
        Cliente cli =  tblClientes.getSelectionModel().getSelectedItem();

        if(cli != null){
            crearPantalla(Modalidad.ACTUALIZAR,cli);
        }
    }

    public void btnRegistrarClick(){
        crearPantalla(Modalidad.INSERTAR,null);
    }

    public void txtBuscarKeyReleased(){
        filtrar();
    }

    @Override
    public void filtrar() {
        String textoBusqueda = txtBuscar.getText().trim().toLowerCase();

        datosFiltrados.setPredicate(cliente -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(cliente.getCodigo()).contains(textoBusqueda);
            boolean coincideNombre = cliente.getNombres() != null &&
                    cliente.getNombres().toLowerCase().contains(textoBusqueda);

            boolean coincideApellidos = cliente.getApellidos() != null &&
                    cliente.getApellidos().toLowerCase().contains(textoBusqueda);
            boolean coincideTelefono = cliente.getTelefono() != null &&
                    cliente.getTelefono().toLowerCase().contains(textoBusqueda);

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

    @Override
    public void crearPantalla(Modalidad modalidad, Cliente clt){
        Pantalla pnt = new StageBuilder()
                .setContenido(Formularios.CLIENTE.getArchivo())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(modalidad.equals(Modalidad.INSERTAR) ? "Registrar Cliente" : "Actualizar Cliente")
                .setSize(Formularios.CLIENTE.getSize())
                .construirPantalla();

        FormularioClienteController controlador = (FormularioClienteController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(modalidad);
        controlador.setCliente(clt);

        pnt.pantalla().show();
        pnt.pantalla().setOnHidden(event -> cargar());
    }
}
