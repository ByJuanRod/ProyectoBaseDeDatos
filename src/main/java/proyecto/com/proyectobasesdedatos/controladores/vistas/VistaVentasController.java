package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.Vista;
import proyecto.com.proyectobasesdedatos.modelos.Venta;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

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

    @FXML
    public void initialize() {
        Inicializador.inicializar(this,tblVentas,txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder(){
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(OpcionMenu.VENTAS,"No se han encontrado ventas.");
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
    public void crearPantalla(Modalidad modalidad, Venta objeto) {

    }

    public void txtBuscarKeyReleased(){

    }

    public void btnRegistrarClick(){


    }

    public void btnInformeClick(){

    }
}
