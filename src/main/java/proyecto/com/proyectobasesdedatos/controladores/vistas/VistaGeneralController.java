package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import proyecto.com.proyectobasesdedatos.modelos.Usuario;
import proyecto.com.proyectobasesdedatos.utilidades.*;

import java.awt.*;

public class VistaGeneralController {

    @FXML
    public Label lblNombre, lblBnv;

    @FXML
    public void initialize(){
        if(Usuario.empleado.getSexo() == 'F'){
            lblBnv.setText("Bienvenida");
        }
        lblNombre.setText(Usuario.empleado.getNombreFormat());
    }

    public void btnConsultarCiudadesClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido(Vistas.CIUDADES.getArchivoVinculado())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(Vistas.CIUDADES.getNombreOpcion())
                .setSize(new Dimension(820,605))
                .construirPantalla();

        VistaCiudadesController controlador = (VistaCiudadesController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        pnt.pantalla().show();
    }

    public void btnConsultarGenerosClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido(Vistas.GENEROS.getArchivoVinculado())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(Vistas.GENEROS.getNombreOpcion())
                .setSize(new Dimension(820,605))
                .construirPantalla();

        VistaGenerosController controlador = (VistaGenerosController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        pnt.pantalla().show();
    }


    public void btnConsultarIdiomaClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido(Vistas.IDIOMAS.getArchivoVinculado())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(Vistas.IDIOMAS.getNombreOpcion())
                .setSize(new Dimension(820,605))
                .construirPantalla();

        VistaIdiomasController controlador = (VistaIdiomasController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        pnt.pantalla().show();
    }

    public void btnConsultarPaisesClick() {
        Pantalla pnt = new StageBuilder()
                .setContenido(Vistas.PAISES.getArchivoVinculado())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(Vistas.PAISES.getNombreOpcion())
                .setSize(new Dimension(820,605))
                .construirPantalla();

        VistaPaisesController controlador = (VistaPaisesController) pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        pnt.pantalla().show();
    }

    public void btnConsultarMunicipiosClick() {
        Pantalla pnt = new StageBuilder()
                .setContenido(Vistas.MUNICIPIOS.getArchivoVinculado())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(Vistas.MUNICIPIOS.getNombreOpcion())
                .setSize(new Dimension(820,605))
                .construirPantalla();

        VistaMunicipiosController controlador = (VistaMunicipiosController) pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        pnt.pantalla().show();
    }

    public void btnConsultarSectoresClick() {
        Pantalla pnt = new StageBuilder()
                .setContenido(Vistas.SECTORES.getArchivoVinculado())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(Vistas.SECTORES.getNombreOpcion())
                .setSize(new Dimension(820,605))
                .construirPantalla();

        VistaSectoresController controlador = (VistaSectoresController) pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        pnt.pantalla().show();
    }

}
