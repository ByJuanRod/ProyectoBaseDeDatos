package proyecto.com.proyectobasesdedatos.controladores.selectores;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioCiudadController;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioClienteController;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioSucursalController;
import proyecto.com.proyectobasesdedatos.controladores.vistas.Inicializador;
import proyecto.com.proyectobasesdedatos.controladores.vistas.Vista;
import proyecto.com.proyectobasesdedatos.modelos.Ciudad;
import proyecto.com.proyectobasesdedatos.servicios.ServicioCiudades;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

import java.awt.*;

public class SelectorCiudadController implements Vista<Ciudad>, Controlador {
    private ServicioCiudades srv = new ServicioCiudades();

    private Stage stage;

    @FXML
    public TableView<Ciudad> tblCiudades;

    @FXML
    public TableColumn<Ciudad,Integer> colCodigo;

    @FXML
    public TableColumn<Ciudad,String> colNombre, colCodigoPostal;

    @FXML
    public TextField txtFiltrar;

    private FilteredList<Ciudad> datosFiltrados;

    private Ciudad ciudadSeleccionada;

    private FormularioSucursalController formularioSucursal;

    private FormularioClienteController formularioCliente;

    public void setFormularioSucursal(FormularioSucursalController formularioSucursal){
        this.formularioSucursal = formularioSucursal;
    }

    public void setFormularioCliente(FormularioClienteController formularioCliente){
        this.formularioCliente = formularioCliente;
    }

    private void propagarCiudad(Ciudad ciudad){
        if(formularioSucursal != null){
            formularioSucursal.setCiudadSeleccionada(ciudad);
        }

        if(formularioCliente != null){
            formularioCliente.setCiudadSeleccionada(ciudad);
        }
    }

    @FXML
    public void initialize(){
        Inicializador.inicializar(this,tblCiudades,txtFiltrar);
    }

    public void btnCerrarClick(){
        stage.close();
    }

    public void btnSeleccionarClick(){
        Ciudad ciudad =  tblCiudades.getSelectionModel().getSelectedItem();

        if(ciudad != null){
            ciudadSeleccionada = ciudad;
            propagarCiudad(ciudad);
            stage.close();
        }
        else{
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("Debe seleccionar una ciudad.").show();
        }
    }

    /**
     * Se invoca desde FormularioCiudadController cuando se crea una ciudad
     * como operación externa (por ejemplo, desde el formulario de sucursal).
     * Propaga la ciudad recién creada al formulario que originalmente abrió
     * este selector y cierra la pantalla.
     */
    public void asignarCiudadCreada(Ciudad ciudad){
        ciudadSeleccionada = ciudad;
        propagarCiudad(ciudad);
        stage.close();
    }

    public void txtFiltrarKeyReleased(){
        filtrar();
    }

    public void btnNuevoClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido("formularios/formulario-ciudad.fxml")
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo("Registro de Ciudad")
                .setSize(new Dimension(680,530))
                .construirPantalla();

        FormularioCiudadController controlador = (FormularioCiudadController) pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setCiudad(null);
        controlador.setModalidad(Modalidad.OPERACION_EXTERNA);
        controlador.setControlador(this);
        pnt.pantalla().show();

        pnt.pantalla().setOnHidden(event -> {
                    cargar();
                }
        );
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void seleccionar(Ciudad ciudad){
        tblCiudades.getSelectionModel().select(ciudad);
    }

    @Override
    public void filtrar() {
        String textoBusqueda = txtFiltrar.getText().trim().toLowerCase();

        datosFiltrados.setPredicate(ciudad -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(ciudad.getCodigo()).contains(textoBusqueda);
            boolean coincideNombre = ciudad.getNombre() != null &&
                    ciudad.getNombre().toLowerCase().contains(textoBusqueda);

            boolean coincideCodigoPost = ciudad.getCodigoPostal() != null &&
                    ciudad.getCodigoPostal().toLowerCase().contains(textoBusqueda);

            return coincideCodigo || coincideNombre || coincideCodigoPost;
        });
    }

    @Override
    public void cargar() {
        ObservableList<Ciudad> datosOriginales = srv.consultar();
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);

        tblCiudades.setItems(datosFiltrados);
        filtrar();
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCodigoPostal.setCellValueFactory(new PropertyValueFactory<>("codigoPostal"));
    }

    @Override
    public void crearPantalla(Modalidad modalidad, Ciudad objeto) {
        Pantalla pnt = new StageBuilder()
                .setContenido("formularios/formulario-ciudad.fxml")
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo("Registro de Ciudad")
                .setSize(new Dimension(680,530))
                .construirPantalla();

        FormularioCiudadController controlador = (FormularioCiudadController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(modalidad);
        controlador.setCiudad(null);

        pnt.pantalla().show();
        pnt.pantalla().setOnHidden(event -> cargar());
    }

    @Override
    public AnchorPane setPlaceholder() {
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(OpcionMenu.CIUDADES,"No se han encontrado ciudades.");
        return comp.visual();
    }
}