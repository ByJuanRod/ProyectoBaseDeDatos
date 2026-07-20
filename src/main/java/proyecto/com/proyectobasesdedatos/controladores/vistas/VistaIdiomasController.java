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
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioIdiomaController;
import proyecto.com.proyectobasesdedatos.modelos.Idioma;
import proyecto.com.proyectobasesdedatos.servicios.ServicioIdiomas;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

import java.awt.*;

public class VistaIdiomasController implements Vista<Idioma>, Controlador {
    private final ServicioIdiomas servicio = new ServicioIdiomas();

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    @FXML
    public TableView<Idioma> tblIdiomas;

    @FXML
    public TextField txtBuscar;

    @FXML
    public TableColumn<Idioma,Integer> colCodigo;

    @FXML
    public TableColumn<Idioma,String> colNombre;

    private FilteredList<Idioma> datosFiltrados;

    @FXML
    public void initialize() {
        Inicializador.inicializar(this,tblIdiomas,txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder(){
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.IDIOMAS,"No se han encontrado idiomas.");
        return comp.visual();
    }

    public void btnCerrarClick(){
        stage.close();
    }

    public void btnEliminarClick(){
        try{
            Idioma idm = tblIdiomas.getSelectionModel().getSelectedItem();
            Alert alt = AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("¿Está seguro de que desea eliminar este registro?");
            alt.showAndWait().ifPresent(resp -> {
                if(resp == ButtonType.OK){
                    if(servicio.eliminar(idm)){
                        AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("El género ha sido eliminado.").show();
                    }
                    else{
                        AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("No se puede eliminar este género").show();
                    }
                }
            });
        }
        catch (Exception e){
            AlertFactory.obtenerAlerta(TipoAlerta.ERROR).crearAlerta("No se ha logrado eliminar el registro.").show();
        }
    }

    public void btnActualizarClick(){
        Idioma idioma =  tblIdiomas.getSelectionModel().getSelectedItem();

        if(idioma != null){
            crearPantalla(Modalidad.ACTUALIZAR,idioma);
        }
    }

    public void txtBuscarKeyReleased(){
        filtrar();
    }

    @Override
    public void filtrar() {
        String textoBusqueda = txtBuscar.getText().trim().toLowerCase();

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
        ObservableList<Idioma> datosOriginales = servicio.consultar();
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);

        tblIdiomas.setItems(datosFiltrados);
        filtrar();
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    @Override
    public void crearPantalla(Modalidad modalidad, Idioma objeto) {
        Pantalla pnt = new StageBuilder()
                .setContenido(Formularios.IDIOMA.getArchivo())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(modalidad.equals(Modalidad.INSERTAR) ? "Registrar Idioma" : "Actualizar Idioma")
                .setSize(Formularios.IDIOMA.getSize())
                .construirPantalla();

        FormularioIdiomaController controlador = (FormularioIdiomaController) pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(modalidad);
        controlador.setIdioma(objeto);

        pnt.pantalla().show();
        pnt.pantalla().setOnHidden(event -> cargar());
    }

}
