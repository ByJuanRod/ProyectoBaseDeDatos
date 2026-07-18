package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.stage.Modality;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioCiudadController;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioGeneroController;
import proyecto.com.proyectobasesdedatos.utilidades.Modalidad;
import proyecto.com.proyectobasesdedatos.utilidades.OpcionMenu;
import proyecto.com.proyectobasesdedatos.utilidades.Pantalla;
import proyecto.com.proyectobasesdedatos.utilidades.StageBuilder;

import java.awt.*;

public class VistaGeneralController {

    public void btnRegistrarCiudadClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido("formularios/formulario-ciudad.fxml")
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo("Registro de Ciudades")
                .setSize(new Dimension(680,530))
                .construirPantalla();

        FormularioCiudadController controlador = (FormularioCiudadController) pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(Modalidad.INSERTAR);
        pnt.pantalla().show();
    }

    public void btnConsultarCiudadesClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido(OpcionMenu.CIUDADES.getArchivoVinculado())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo("Vista de Ciudades")
                .setSize(new Dimension(820,605))
                .construirPantalla();

        VistaCiudadesController controlador = (VistaCiudadesController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        pnt.pantalla().show();
    }

    public void btnRegistrarGeneroClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido("formularios/formulario-genero.fxml")
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo("Registro de Géneros")
                .setSize(new Dimension(680,530))
                .construirPantalla();

        FormularioGeneroController controlador = (FormularioGeneroController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(Modalidad.INSERTAR);
        pnt.pantalla().show();
    }

    public void btnConsultarGenerosClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido(OpcionMenu.GENEROS.getArchivoVinculado())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo("Vista de Géneros")
                .setSize(new Dimension(820,605))
                .construirPantalla();

        VistaGenerosController controlador = (VistaGenerosController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        pnt.pantalla().show();
    }


}
