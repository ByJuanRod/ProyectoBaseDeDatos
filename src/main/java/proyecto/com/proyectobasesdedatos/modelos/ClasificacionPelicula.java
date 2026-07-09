package proyecto.com.proyectobasesdedatos.modelos;

public enum ClasificacionPelicula {
    TODAS_AUDIENCIAS("G","Película para todo público"),
    GUIA_PARENTAL("PG","Película con material para niños"),
    ADOLESCENTES("PG-13","Película con contenido fuerte, mayores de 13 años"),
    RESTRINGIDO("R","Requieren ir acompañado por un adulto los menores de 17 años"),
    PROHIBIDO_MENORES("RC-17","Película para mayores de 17 años");

    ClasificacionPelicula(String nombre, String descripcion){
        nombreClasificacion = nombre;
        this.descripcion = descripcion;
    }

    private final String nombreClasificacion;
    private final String descripcion;

    public String getNombreClasificacion() {
        return nombreClasificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion + "(" + nombreClasificacion + ")";
    }

}
