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
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioGeneroController;
import proyecto.com.proyectobasesdedatos.modelos.Genero;
import proyecto.com.proyectobasesdedatos.servicios.ServicioGeneros;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

import java.awt.*;

public class VistaGenerosController implements Vista<Genero>, Controlador {
    private final ServicioGeneros servicio = new ServicioGeneros();

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    @FXML
    public TableView<Genero> tblGeneros;

    @FXML
    public TextField txtBuscar;

    @FXML
    public TableColumn<Genero,Integer> colCodigo;

    @FXML
    public TableColumn<Genero,String> colNombre;

    private FilteredList<Genero> datosFiltrados;

    @FXML
    public void initialize() {
        Inicializador.inicializar(this,tblGeneros,txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder(){
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(OpcionMenu.GENEROS,"No se han encontrado géneros.");
        return comp.visual();
    }

    public void btnCerrarClick(){
        stage.close();
    }

    public void btnEliminarClick(){
        try{
            Genero genero = tblGeneros.getSelectionModel().getSelectedItem();
            Alert alt = AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("¿Está seguro de que desea eliminar este registro?");
            alt.showAndWait().ifPresent(resp -> {
                if(resp == ButtonType.OK){
                    if(servicio.eliminar(genero)){
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
        Genero genero =  tblGeneros.getSelectionModel().getSelectedItem();

        if(genero != null){
            crearPantalla(Modalidad.ACTUALIZAR,genero);
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
        ObservableList<Genero> datosOriginales = servicio.consultar();
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);

        tblGeneros.setItems(datosFiltrados);
        filtrar();
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    @Override
    public void crearPantalla(Modalidad modalidad, Genero genero){
        Pantalla pnt = new StageBuilder()
                .setContenido("formularios/formulario-genero.fxml")
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(modalidad.equals(Modalidad.INSERTAR) ? "Registrar Género" : "Actualizar Género")
                .setSize(new Dimension(680,530))
                .construirPantalla();

        FormularioGeneroController controlador = (FormularioGeneroController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(modalidad);
        controlador.setGenero(genero);

        pnt.pantalla().show();
        pnt.pantalla().setOnHidden(event -> cargar());
    }
}
