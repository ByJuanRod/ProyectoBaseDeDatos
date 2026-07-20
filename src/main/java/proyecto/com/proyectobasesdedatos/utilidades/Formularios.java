package proyecto.com.proyectobasesdedatos.utilidades;

import java.awt.*;

public enum Formularios {
    PAIS("formularios/formulario-pais.fxml","Registro de Pais",new Dimension(680,530)),
    IDIOMA("formularios/formulario-idioma.fxml","Registro de Idioma", new Dimension(680,530)),
    GENERO("formularios/formulario-genero.fxml","Registro de Género", new Dimension(680,530)),
    CIUDAD("formularios/formulario-ciudad.fxml","Registro de Ciudad", new Dimension(680,530)),
    CLIENTE("formularios/formulario-cliente.fxml","Registro de Cliente",new Dimension(680,530)),
    SALA("formularios/formulario-salas.fxml","Registro de Sala",new Dimension(670,500)),
    SUCURSAL("formularios/formulario-sucursal.fxml","Registro de Sucursal",new Dimension(680,530)),

    ;
    final String nombreArchivo;
    final String tituloFormulario;
    final Dimension dimension;

    Formularios(String nombreArchivo, String tituloFormulario, Dimension dimension) {
        this.nombreArchivo = nombreArchivo;
        this.tituloFormulario = tituloFormulario;
        this.dimension = dimension;
    }

    public Dimension getSize(){
        return dimension;
    }

    public String getArchivo(){
        return nombreArchivo;
    }

    public String getTitulo(){
        return tituloFormulario;
    }
}
