package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioClienteController;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioSucursalController;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.modelos.Sucursal;
import proyecto.com.proyectobasesdedatos.servicios.ServicioSucursales;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

import java.awt.*;

public class VistaSucursalesController implements Vista<Sucursal> {
    private final ServicioSucursales servicio = new ServicioSucursales();

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
        Pantalla pnt = new StageBuilder()
                .setContenido("formularios/formulario-sucursal.fxml")
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(modalidad.equals(Modalidad.INSERTAR) ? "Registrar Sucursal" : "Actualizar Sucursal")
                .setSize(new Dimension(680,530))
                .construirPantalla();

        FormularioSucursalController controlador = (FormularioSucursalController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(modalidad);
        controlador.setSucursal(objeto);

        pnt.pantalla().show();
        pnt.pantalla().setOnHidden(event -> cargar());
    }

    public void txtBuscarKeyReleased(){
        filtrar();
    }

    public void btnRegistrarClick(){
        crearPantalla(Modalidad.INSERTAR,null);
    }

    public void btnEliminarClick(){
        try{
            Sucursal suc = tblSucursales.getSelectionModel().getSelectedItem();
            Alert alt = AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("¿Está seguro de que desea eliminar este registro?");
            alt.showAndWait().ifPresent(resp -> {
                if(resp == ButtonType.OK){
                    if(servicio.eliminar(suc)){
                        AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("El cliente ha sido eliminado.").show();
                    }
                    else{
                        AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("No se puede eliminar este cliente.").show();
                    }
                }
            });
        }
        catch (Exception e){
            AlertFactory.obtenerAlerta(TipoAlerta.ERROR).crearAlerta("No se ha logrado eliminar el registro.").show();
        }
    }

    public void btnActualizarClick(){
        Sucursal suc =  tblSucursales.getSelectionModel().getSelectedItem();

        if(suc != null){
            crearPantalla(Modalidad.ACTUALIZAR,suc);
        }
    }

}
