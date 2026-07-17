package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.Vista;
import proyecto.com.proyectobasesdedatos.modelos.Sucursal;
import proyecto.com.proyectobasesdedatos.utilidades.*;

public class VistaSucursalesController implements Vista<Sucursal> {

    @FXML
    public TableView<Sucursal> tblSucursales;

    @FXML
    public TableColumn<Sucursal, Integer> colCodigo;

    @FXML
    public TableColumn<Sucursal, String> colNombre, colCiudad, colTelefono;

    @FXML
    public TextField txtBuscar;

    @FXML
    public void initialize() {
        Inicializador.inicializar(this,tblSucursales,txtBuscar);
    }

    @Override
    public AnchorPane setPlaceholder(){
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(OpcionMenu.SUCURSALES,"No se han encontrado sucursales.");
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
    public void crearPantalla(Modalidad modalidad, Sucursal objeto) {

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
