package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import proyecto.com.proyectobasesdedatos.controladores.Vista;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;
import proyecto.com.proyectobasesdedatos.utilidades.Modalidad;

public class VistaPeliculasController implements Vista<Pelicula> {

    @FXML
    public TableView<Pelicula> tblPeliculas;

    @FXML
    public TableColumn<Pelicula, Integer> colCodigo;

    @FXML
    public TableColumn<Pelicula, String> colNombre, colDirector, colDuracion, colClasificacion;

    @FXML
    public TextField txtBuscar;

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
