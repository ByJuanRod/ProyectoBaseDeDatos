package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import proyecto.com.proyectobasesdedatos.controladores.Vista;
import proyecto.com.proyectobasesdedatos.modelos.Funcion;
import proyecto.com.proyectobasesdedatos.utilidades.Modalidad;

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

