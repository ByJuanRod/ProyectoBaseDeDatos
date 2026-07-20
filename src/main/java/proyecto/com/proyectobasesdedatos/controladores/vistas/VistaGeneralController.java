package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.stage.Modality;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioCiudadController;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioGeneroController;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioIdiomaController;
import proyecto.com.proyectobasesdedatos.controladores.formularios.FormularioPaisController;
import proyecto.com.proyectobasesdedatos.utilidades.*;

import java.awt.*;

public class VistaGeneralController {

    public void btnRegistrarCiudadClick(){
        Pantalla pnt = new StageBuilder()
                .setContenido(Formularios.CIUDAD.getArchivo())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(Formularios.CIUDAD.getTitulo())
                .setSize(new Dimension(680,530))
                .construirPantalla();

        FormularioCiudadController controlador = (FormularioCiudadController) pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(Modalidad.INSERTAR);
        pnt.pantalla().show();
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
        Pantalla pnt = new StageBuilder()
                .setContenido(Formularios.GENERO.getArchivo())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(Formularios.GENERO.getTitulo())
                .setSize(new Dimension(680,530))
                .construirPantalla();

        FormularioGeneroController controlador = (FormularioGeneroController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(Modalidad.INSERTAR);
        pnt.pantalla().show();
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
        Pantalla pnt = new StageBuilder()
                .setContenido(Formularios.IDIOMA.getArchivo())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(Formularios.IDIOMA.getTitulo())
                .setSize(new Dimension(680,530))
                .construirPantalla();

        FormularioIdiomaController controlador = (FormularioIdiomaController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(Modalidad.INSERTAR);
        pnt.pantalla().show();
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
        Pantalla pnt = new StageBuilder()
                .setContenido(Formularios.PAIS.getArchivo())
                .setModalidad(Modality.APPLICATION_MODAL)
                .setTitulo(Formularios.PAIS.getTitulo())
                .setSize(new Dimension(680,530))
                .construirPantalla();

        FormularioPaisController controlador = (FormularioPaisController)pnt.componte().controlador();
        controlador.setStage(pnt.pantalla());
        controlador.setModalidad(Modalidad.INSERTAR);
        pnt.pantalla().show();
    }

}
