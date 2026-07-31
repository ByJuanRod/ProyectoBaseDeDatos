package proyecto.com.proyectobasesdedatos.utilidades;

import java.awt.*;

public enum Selectores {
    SECTORES("componentes/selector-sector.fxml","Selector Sector",new Dimension(780,600)),;

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
