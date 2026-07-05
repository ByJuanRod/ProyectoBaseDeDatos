package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import proyecto.com.proyectobasesdedatos.controladores.Vista;
import proyecto.com.proyectobasesdedatos.modelos.Sala;
import proyecto.com.proyectobasesdedatos.utilidades.Modalidad;

public class VistaSalasController implements Vista<Sala> {

    @FXML
    public TableView<Sala> tblSalas;

    @FXML
    public TableColumn<Sala, String> colNombre;

    @FXML
    public TableColumn<Sala, Integer> colCodigo, colCapacidad;

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
    public void crearPantalla(Modalidad modalidad, Sala objeto) {

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
