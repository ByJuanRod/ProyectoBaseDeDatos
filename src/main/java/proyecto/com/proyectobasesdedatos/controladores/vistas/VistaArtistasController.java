package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import proyecto.com.proyectobasesdedatos.PlaceholderController;
import proyecto.com.proyectobasesdedatos.modelos.Actor;
import proyecto.com.proyectobasesdedatos.modelos.Artista;
import proyecto.com.proyectobasesdedatos.modelos.Director;
import proyecto.com.proyectobasesdedatos.servicios.ServicioActores;
import proyecto.com.proyectobasesdedatos.servicios.ServicioDirectores;
import proyecto.com.proyectobasesdedatos.utilidades.*;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

public class VistaArtistasController implements Vista<Artista> {

    private enum TipoArtista { ACTOR, DIRECTOR }

    private final ServicioActores servicioActores = new ServicioActores();
    private final ServicioDirectores servicioDirectores = new ServicioDirectores();

    private TipoArtista tipoActual = TipoArtista.ACTOR;

    @FXML
    public Button btnCambiarModalidad;

    @FXML
    public TableView<Artista> tblArtistas;

    @FXML
    public Label lblTitulo;

    @FXML
    public TextField txtBuscar;

    @FXML
    public TableColumn<Artista,Integer> colCodigo;

    @FXML
    public TableColumn<Artista,String> colNombres, colApellidos, colNacionalidad;

    private FilteredList<Artista> datosFiltrados;

    @FXML
    public void initialize() {
        Inicializador.inicializar(this,tblArtistas,txtBuscar);
        actualizarEncabezados();
    }

    @Override
    public AnchorPane setPlaceholder(){
        CargadorFXML cargadorFXML = new CargadorFXML();
        Componente comp = cargadorFXML.cargarComponenteConControlador("placeholder.fxml");
        PlaceholderController cont = (PlaceholderController) comp.controlador();
        cont.setContenido(Vistas.ARTISTAS, tipoActual == TipoArtista.ACTOR
                ? "No se han encontrado actores."
                : "No se han encontrado directores.");
        return comp.visual();
    }

    public void btnEliminarClick(){
        try{
            Artista artista = tblArtistas.getSelectionModel().getSelectedItem();
            Alert alt = AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("¿Está seguro de que desea eliminar este registro?");
            alt.showAndWait().ifPresent(resp -> {
                if(resp == ButtonType.OK){
                    boolean eliminado = (tipoActual == TipoArtista.ACTOR)
                            ? servicioActores.eliminar((Actor) artista)
                            : servicioDirectores.eliminar((Director) artista);

                    if(eliminado){
                        AlertFactory.obtenerAlerta(TipoAlerta.INFORMACION).crearAlerta("El registro ha sido eliminado.").show();
                        cargar();
                    }
                    else{
                        AlertFactory.obtenerAlerta(TipoAlerta.ADVERTENCIA).crearAlerta("No se puede eliminar este registro.").show();
                    }
                }
            });
        }
        catch (Exception e){
            AlertFactory.obtenerAlerta(TipoAlerta.ERROR).crearAlerta("No se ha logrado eliminar el registro.").show();
        }
    }

    public void btnActualizarClick(){
        Artista art =  tblArtistas.getSelectionModel().getSelectedItem();

        if(art != null){
            crearPantalla(Modalidad.ACTUALIZAR,art);
        }
    }

    public void btnRegistrarClick(){
        crearPantalla(Modalidad.INSERTAR,null);
    }

    public void txtBuscarKeyReleased(){
        filtrar();
    }

    @Override
    public void filtrar() {
        String textoBusqueda = txtBuscar.getText().trim().toLowerCase();

        datosFiltrados.setPredicate(artista -> {
            if (textoBusqueda.isEmpty()) {
                return true;
            }

            boolean coincideCodigo = String.valueOf(artista.getCodigo()).contains(textoBusqueda);
            boolean coincideNombre = artista.getNombre() != null &&
                    artista.getNombre().toLowerCase().contains(textoBusqueda);

            boolean coincideApellido = artista.getApellido() != null &&
                    artista.getApellido().toLowerCase().contains(textoBusqueda);

            return coincideCodigo || coincideNombre || coincideApellido;
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public void cargar() {
        ObservableList<Artista> datosOriginales = (tipoActual == TipoArtista.ACTOR)
                ? (ObservableList<Artista>)(ObservableList<?>) servicioActores.consultar()
                : (ObservableList<Artista>)(ObservableList<?>) servicioDirectores.consultar();

        datosFiltrados = new FilteredList<>(datosOriginales, p -> true);

        tblArtistas.setItems(datosFiltrados);
        filtrar();
    }

    @Override
    public void configurarColumnas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombres.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colNacionalidad.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getSexo() == 'M' ? "Masculino" : "Femenino"));
    }

    @Override
    public void crearPantalla(Modalidad modalidad, Artista art){
       /* if(tipoActual == TipoArtista.ACTOR){
            Pantalla pnt = new StageBuilder()
                    .setContenido(Formularios.ACTOR.getArchivo())
                    .setModalidad(Modality.APPLICATION_MODAL)
                    .setTitulo(modalidad.equals(Modalidad.INSERTAR) ? "Registrar Actor" : "Actualizar Actor")
                    .setSize(Formularios.ACTOR.getSize())
                    .construirPantalla();

            FormularioActorController controlador = (FormularioActorController) pnt.componte().controlador();
            controlador.setStage(pnt.pantalla());
            controlador.setModalidad(modalidad);
            controlador.setActor((Actor) art);

            pnt.pantalla().show();
            pnt.pantalla().setOnHidden(event -> cargar());
        }
        else{
            Pantalla pnt = new StageBuilder()
                    .setContenido(Formularios.DIRECTOR.getArchivo())
                    .setModalidad(Modality.APPLICATION_MODAL)
                    .setTitulo(modalidad.equals(Modalidad.INSERTAR) ? "Registrar Director" : "Actualizar Director")
                    .setSize(Formularios.DIRECTOR.getSize())
                    .construirPantalla();

            FormularioDirectorController controlador = (FormularioDirectorController) pnt.componte().controlador();
            controlador.setStage(pnt.pantalla());
            controlador.setModalidad(modalidad);
            controlador.setDirector((Director) art);

            pnt.pantalla().show();
            pnt.pantalla().setOnHidden(event -> cargar());
        }*/
    }

    public void btnCambiarModalidadClick(){
        tipoActual = (tipoActual == TipoArtista.ACTOR) ? TipoArtista.DIRECTOR : TipoArtista.ACTOR;
        actualizarEncabezados();
        cargar();
    }

    private void actualizarEncabezados(){
        if(tipoActual == TipoArtista.ACTOR){
            lblTitulo.setText("Actores");
            btnCambiarModalidad.setText("Ver Directores");
        }
        else{
            lblTitulo.setText("Directores");
            btnCambiarModalidad.setText("Ver Actores");
        }
    }
}