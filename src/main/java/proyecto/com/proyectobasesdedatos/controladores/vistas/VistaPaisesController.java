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
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioPaisController;
import proyecto.com.proyectobasesdedatos.modelos.Pais;
import proyecto.com.proyectobasesdedatos.servicios.ServicioPaises;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

import java.awt.*;

public class VistaPaisesController implements Vista<Pais>, Controlador {
    private final ServicioPaises servicio = new ServicioPaises();

    private Stage stage;

    public void setStage(Stage stg){
        stage = stg;
    }

    @FXML
    public Button btnRegistrar, btnActualizar, btnEliminar;

    @FXML
    public TableView<Pais> tblPaises;

    @FXML
    public TextField txtBuscar;

    @FXML
    public TableColumn<Pais,Integer> colCodigo;

    @FXML
    public TableColumn<Pais,String> colNombre;

    private FilteredList<Pais> datosFiltrados;

    @FXML
    public void initialize() {
        Inicializador.inicializar(this,tblPaises,txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder(){
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.PAISES,"No se han encontrado países.");
        return comp.visual();
    }

    public void btnCerrarClick(){
        stage.close();
    }

    public void btnEliminarClick(){
        try{
            Pais pais = tblPaises.getSelectionModel().getSelectedItem();
            Alert alt = AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("¿Está seguro de que desea eliminar este registro?");
            alt.showAndWait().ifPresent(resp -> {
                if(resp == ButtonType.OK){
                    if(servicio.eliminar(pais)){
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
        Pais pais =  tblPaises.getSelectionModel().getSelectedItem();

        if(pais != null){
            crearPantalla(Modalidad.ACTUALIZAR,pais);
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
        ObservableList<Pais> datosOriginales = servicio.consultar();
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
    public void crearPantalla(Modalidad modalidad, Pais pais){
        Pantalla pnt = new StageBuilder()
                .setContenido(Formularios.PAIS.getArchivo())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(modalidad.equals(Modalidad.INSERTAR) ? "Registrar País" : "Actualizar País")
                .setSize(Formularios.PAIS.getSize())
                .construirPantalla();

        FormularioPaisController controlador = (FormularioPaisController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(modalidad);
        controlador.setPais(pais);

        pnt.pantalla().show();
        pnt.pantalla().setOnHidden(event -> cargar());
    }
}
