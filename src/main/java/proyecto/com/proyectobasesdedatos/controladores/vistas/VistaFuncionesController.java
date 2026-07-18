package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.modelos.Funcion;
import proyecto.com.proyectobasesdedatos.utilidades.*;

public class VistaFuncionesController implements Vista<Funcion> {

    @FXML
    public TableView<Funcion> tblFunciones;

    @FXML
    public TableColumn<Funcion,String> colPelicula, colFecha, colSala;

    @FXML
    public TableColumn<Funcion, Integer> colCodigo;

    @FXML
    public TableColumn<Funcion,Float> colPrecio;

    @FXML
    public TextField txtBuscar;

    @FXML
    public void initialize(){
        Inicializador.inicializar(this,tblFunciones,txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder(){
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(OpcionMenu.FUNCIONES,"No se han encontrado funciones.");
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
    public void crearPantalla(Modalidad modalidad, Funcion objeto) {

    }

    public void btnEliminarClick(){

    }

    public void btnActualizarClick(){

    }

    public void btnRegistrarClick(){

    }

    public void txtBuscarKeyReleased(){

    }
}

