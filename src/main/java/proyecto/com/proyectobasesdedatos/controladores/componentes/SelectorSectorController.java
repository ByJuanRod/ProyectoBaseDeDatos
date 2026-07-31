package proyecto.com.proyectobasesdedatos.controladores.componentes;

import java.util.function.Consumer;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.controladores.vistas.Inicializador;
import proyecto.com.proyectobasesdedatos.controladores.vistas.Vista;
import proyecto.com.proyectobasesdedatos.modelos.Sector;
import proyecto.com.proyectobasesdedatos.servicios.ServicioSectores;
import proyecto.com.proyectobasesdedatos.utilidades.CargadorFXML;
import proyecto.com.proyectobasesdedatos.utilidades.Componente;
import proyecto.com.proyectobasesdedatos.utilidades.Vistas;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

public class SelectorSectorController implements Vista<Sector>, Controlador {
    private final ServicioSectores srv = ServicioSectores.getInstance();

    private Stage stage;

    @FXML
    public TableView<Sector> tblSectores;

    @FXML
    public TableColumn<Sector,Integer> colCodigo;

    @FXML
    public TableColumn<Sector,String> colNombre, colMunicipio;

    @FXML
    public TextField txtFiltrar;

    private FilteredList<Sector> datosFiltrados;

    private Sector sectorSeleccionado;

    private Consumer<Sector> onSeleccionar;

    public void setOnSeleccionar(Consumer<Sector> onSeleccionar){
        this.onSeleccionar = onSeleccionar;
    }

    @FXML
    public void initialize(){
        Inicializador.inicializar(this,tblSectores,txtFiltrar);
    }

    public void btnCerrarClick(){
        stage.close();
    }

    public void btnSeleccionarClick(){
        Sector sector = tblSectores.getSelectionModel().getSelectedItem();

        if(sector != null){
            sectorSeleccionado = sector;

            if(onSeleccionar != null){
                onSeleccionar.accept(sector);
            }

            stage.close();
        }
        else{
            AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("Debe seleccionar un sector.").show();
        }
    }

    public void txtFiltrarKeyReleased(){
        filtrar();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void seleccionar(Sector sector){
        tblSectores.getSelectionModel().select(sector);
    }

    @Override
    public void filtrar() {
        String textoBusqueda = txtFiltrar.getText().trim().toLowerCase();

        datosFiltrados.setPredicate(sector -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(sector.getIdSector()).contains(textoBusqueda);

            boolean coincideNombre = sector.getNombreSector() != null &&
                    sector.getNombreSector().toLowerCase().contains(textoBusqueda);

            boolean coincideMunicipio = sector.getMunicipio() != null &&
                    sector.getMunicipio().getNombreMunicipio() != null &&
                    sector.getMunicipio().getNombreMunicipio().toLowerCase().contains(textoBusqueda);

            return coincideCodigo || coincideNombre || coincideMunicipio;
        });
    }

    @Override
    public void cargar() {
        ObservableList<Sector> datosOriginales = FXCollections.observableArrayList(srv.obtenerTodos());
        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);

        tblSectores.setItems(datosFiltrados);
        filtrar();
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("idSector"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreSector"));
        colMunicipio.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getMunicipio() != null
                                ? data.getValue().getMunicipio().getNombreMunicipio()
                                : ""
                ));
    }


    @Override
    public AnchorPane setPlaceholder() {
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.SECTORES,"No se han encontrado sectores.");
        return comp.visual();
    }
}
