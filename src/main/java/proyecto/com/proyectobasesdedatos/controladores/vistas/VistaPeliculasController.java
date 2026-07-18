package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;
import proyecto.com.proyectobasesdedatos.utilidades.*;

public class VistaPeliculasController implements Vista<Pelicula> {

    @FXML
    public TableView<Pelicula> tblPeliculas;

    @FXML
    public TableColumn<Pelicula, Integer> colCodigo;

    @FXML
    public TableColumn<Pelicula, String> colNombre, colDirector, colDuracion, colClasificacion;

    @FXML
    public TextField txtBuscar;

    @FXML
    public void initialize() {
        Inicializador.inicializar(this,tblPeliculas,txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder(){
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(OpcionMenu.PELICULAS,"No se han encontrado películas.");
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
    public void crearPantalla(Modalidad modalidad, Pelicula objeto) {

    }

    public void txtBuscarKeyReleased(){

    }

    public void btnRegistrarClick(){

    }

    public void btnEliminarClick(){

    }

    public void btnActualizarClick(){

    }

}
