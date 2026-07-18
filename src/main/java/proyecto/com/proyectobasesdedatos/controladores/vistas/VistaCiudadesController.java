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
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioCiudadController;
import proyecto.com.proyectobasesdedatos.modelos.Ciudad;
import proyecto.com.proyectobasesdedatos.servicios.ServicioCiudades;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

import java.awt.*;

public class VistaCiudadesController implements Vista<Ciudad>, Controlador {
    private final ServicioCiudades servicio = new ServicioCiudades();

    private Stage stage;

    public void setStage(Stage stg){
        stage = stg;
    }

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    @FXML
    public TableView<Ciudad> tblCiudades;

    @FXML
    public TextField txtBuscar;

    @FXML
    public TableColumn<Ciudad,Integer> colCodigo;

    @FXML
    public TableColumn<Ciudad,String> colNombre, colCodigoPostal;

    private FilteredList<Ciudad> datosFiltrados;

    @FXML
    public void initialize() {
        Inicializador.inicializar(this,tblCiudades,txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder(){
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(OpcionMenu.CIUDADES,"No se han encontrado ciudades.");
        return comp.visual();
    }

    public void btnCerrarClick(){
        stage.close();
    }

    public void btnEliminarClick(){
        try{
            Ciudad ciudad = tblCiudades.getSelectionModel().getSelectedItem();
            Alert alt = AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("¿Está seguro de que desea eliminar este registro?");
            alt.showAndWait().ifPresent(resp -> {
                if(resp == ButtonType.OK){
                    if(servicio.eliminar(ciudad)){
                        AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("La ciudad ha sido eliminado.").show();
                    }
                    else{
                        AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("No se puede eliminar esta ciudad.").show();
                    }
                }
            });
        }
        catch (Exception e){
            AlertFactory.obtenerAlerta(TipoAlerta.ERROR).crearAlerta("No se ha logrado eliminar el registro.").show();
        }
    }

    public void btnActualizarClick(){
        Ciudad ciudad =  tblCiudades.getSelectionModel().getSelectedItem();

        if(ciudad != null){
            crearPantalla(Modalidad.ACTUALIZAR,ciudad);
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

            boolean coincideCodigoPost = ciudad.getCodigoPostal() != null &&
                    ciudad.getCodigoPostal().toLowerCase().contains(textoBusqueda);

            return coincideCodigo || coincideNombre || coincideCodigoPost;
        });
    }

    @Override
    public void cargar() {
        ObservableList<Ciudad> datosOriginales = servicio.consultar();
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
    public void crearPantalla(Modalidad modalidad, Ciudad ciudad){
        Pantalla pnt = new StageBuilder()
                .setContenido("formularios/formulario-ciudad.fxml")
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(modalidad.equals(Modalidad.INSERTAR) ? "Registrar Cliente" : "Actualizar Cliente")
                .setSize(new Dimension(680,530))
                .construirPantalla();

        FormularioCiudadController controlador = (FormularioCiudadController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(modalidad);
        controlador.setCiudad(ciudad);

        pnt.pantalla().show();
        pnt.pantalla().setOnHidden(event -> cargar());
    }
}
