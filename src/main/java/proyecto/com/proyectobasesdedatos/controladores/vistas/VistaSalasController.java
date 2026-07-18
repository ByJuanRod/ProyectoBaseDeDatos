package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioSalaController;
import proyecto.com.proyectobasesdedatos.modelos.Sala;
import proyecto.com.proyectobasesdedatos.utilidades.*;

import java.awt.*;

public class VistaSalasController implements Vista<Sala> {

    @FXML
    public TableView<Sala> tblSalas;

    @FXML
    public TableColumn<Sala, String> colNombre;

    @FXML
    public TableColumn<Sala, Integer> colCodigo, colCapacidad;

    @FXML
    public TextField txtBuscar;

    @FXML
    public void initialize() {
        Inicializador.inicializar(this,tblSalas,txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder(){
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(OpcionMenu.SALAS,"No se han encontrado salas.");
        return comp.visual();
    }

    @Override
    public void filtrar() {

    }

    @Override
    public void cargar() {

    }

    @Override
    public void configurarColumnas() {

    }

    @Override
    public void crearPantalla(Modalidad modalidad, Sala sl) {
        Pantalla pnt = new StageBuilder()
                .setContenido("formularios/formulario-salas.fxml")
                .setModalidad(Modality.WINDOW_MODAL)
                .setTitulo(modalidad.equals(Modalidad.INSERTAR) ? "Registrar Sala" : "Actualizar Sala")
                .setSize(new Dimension(670,500))
                .construirPantalla();

        FormularioSalaController controlador = (FormularioSalaController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(modalidad);
        controlador.setSala(sl);

        pnt.pantalla().show();
        pnt.pantalla().setOnHidden(event -> cargar());
    }

    public void btnRegistrarClick(){


    }

    public void btnActualizarClick(){

    }

    public void btnEliminarClick(){

    }

    public void txtBuscarKeyReleased(){

    }
}
