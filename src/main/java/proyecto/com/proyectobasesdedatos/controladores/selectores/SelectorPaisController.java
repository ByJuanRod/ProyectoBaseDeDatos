package proyecto.com.proyectobasesdedatos.controladores.selectores;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioArtistaController;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioCiudadController;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioPaisController;
import proyecto.com.proyectobasesdedatos.controladores.vistas.Inicializador;
import proyecto.com.proyectobasesdedatos.controladores.vistas.Vista;
import proyecto.com.proyectobasesdedatos.modelos.Pais;
import proyecto.com.proyectobasesdedatos.servicios.ServicioPaises;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

import java.awt.*;

public class SelectorPaisController implements Vista<Pais>, Controlador {
    private final ServicioPaises srv = new ServicioPaises();

    private Stage stage;

    @FXML
    public TableView<Pais> tblPaises;

    @FXML
    public TableColumn<Pais,Integer> colCodigo;

    @FXML
    public TableColumn<Pais,String> colNombre;

    @FXML
    public TextField txtFiltrar;

    private FilteredList<Pais> datosFiltrados;

    private FormularioArtistaController formularioArtista;

    private FormularioCiudadController formularioCiudad;

    public void setFormularioArtista(FormularioArtistaController formularioArtista){
        this.formularioArtista = formularioArtista;
    }

    public void setFormularioCiudad(FormularioCiudadController formularioCiudad){
        this.formularioCiudad = formularioCiudad;
    }

    private void propagarPais(Pais pais){
        if(formularioArtista != null){
            formularioArtista.setPaisSeleccionado(pais);
        }

        if(formularioCiudad != null){
            formularioCiudad.setPaisSeleccionado(pais);
        }
    }

    @FXML
    public void initialize(){
        Inicializador.inicializar(this,tblPaises,txtFiltrar);
    }

    public void btnCerrarClick(){
        stage.close();
    }

    public void btnSeleccionarClick(){
        Pais pais =  tblPaises.getSelectionModel().getSelectedItem();

        if(pais != null){
            propagarPais(pais);
            stage.close();
        }
        else{
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("Debe seleccionar un país.").show();
        }
    }

    public void asignarPaisCreado(Pais pais){
        propagarPais(pais);
        stage.close();
    }

    public void txtFiltrarKeyReleased(){
        filtrar();
    }

    public void btnNuevoClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido(Formularios.PAIS.getArchivo())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(Formularios.PAIS.getTitulo())
                .setSize(Formularios.PAIS.getSize())
                .construirPantalla();

        FormularioPaisController controlador = (FormularioPaisController) pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setPais(null);
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

            return coincideCodigo || coincideNombre;
        });
    }

    @Override
    public void cargar() {
        ObservableList<Pais> datosOriginales = srv.consultar();
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);

        tblPaises.setItems(datosFiltrados);
        filtrar();
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    @Override
    public void crearPantalla(Modalidad modalidad, Pais objeto) {
        Pantalla pnt = new StageBuilder()
                .setContenido(Formularios.PAIS.getArchivo())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(Formularios.PAIS.getArchivo())
                .setSize(Formularios.PAIS.getSize())
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
        cont.setContenido(Vistas.PAISES,"No se han encontrado países.");
        return comp.visual();
    }
}