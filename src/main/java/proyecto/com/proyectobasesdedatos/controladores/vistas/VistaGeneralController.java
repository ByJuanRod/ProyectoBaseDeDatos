package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.stage.Modality;
import proyecto.com.proyectobasesdedatos.utilidades.*;

import java.awt.*;

public class VistaGeneralController {

    public void btnRegistrarCiudadClick(){
    }

    public void btnConsultarCiudadesClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido(Vistas.CIUDADES.getArchivoVinculado())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo("Vista de Ciudades")
                .setSize(new Dimension(820,605))
                .construirPantalla();

        VistaCiudadesController controlador = (VistaCiudadesController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        pnt.pantalla().show();
    }

    public void btnRegistrarGeneroClick(){

    }

    public void btnConsultarGenerosClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido(Vistas.GENEROS.getArchivoVinculado())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo("Vista de Géneros")
                .setSize(new Dimension(820,605))
                .construirPantalla();

        VistaGenerosController controlador = (VistaGenerosController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        pnt.pantalla().show();
    }

    public void btnRegistrarIdiomaClick(){

    }

    public void btnConsultarIdiomaClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido(Vistas.IDIOMAS.getArchivoVinculado())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo("Vista de Idiomas")
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
                .setTitulo("Vista de Países")
                .setSize(new Dimension(820,605))
                .construirPantalla();

        VistaPaisesController controlador = (VistaPaisesController) pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        pnt.pantalla().show();
    }

    public void btnRegistrarPaisClick(){

    }

}
