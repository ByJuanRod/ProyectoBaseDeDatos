package proyecto.com.proyectobasesdedatos.utilidades;

import java.awt.*;

public enum Selectores {
    CIUDADES("selectores/selector-ciudad.fxml","Selector de Ciudad",new Dimension(780,600)),
    PAIS("selectores/selector-pais.fxml","Selector País",new Dimension(780,600)),;

    final String nombreArchivo;
    final String titulo;
    final Dimension dimension;

    Selectores(String nombreArchivo, String titulo, Dimension dimension) {
        this.nombreArchivo = nombreArchivo;
        this.titulo = titulo;
        this.dimension = dimension;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArchivo(){
        return nombreArchivo;
    }

    public Dimension getSize() {
        return dimension;
    }
}
