package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import proyecto.com.proyectobasesdedatos.controladores.Vista;
import proyecto.com.proyectobasesdedatos.modelos.Venta;
import proyecto.com.proyectobasesdedatos.utilidades.Modalidad;

public class VistaVentasController implements Vista<Venta> {
    @FXML
    public TableView<Venta> tblVentas;

    @FXML
    public TableColumn<Venta, Integer> colCodigo, colEntradas;

    @FXML
    public TableColumn<Venta, String> colCliente, colFecha;

    @FXML
    public TableColumn<Venta, Float> colTotal;

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
    public void crearPantalla(Modalidad modalidad, Venta objeto) {

    }

    public void txtBuscarKeyReleased(){

    }

    public void btnRegistrarClick(){


    }

    public void btnInformeClick(){

    }
}
