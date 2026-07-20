package proyecto.com.proyectobasesdedatos.utilidades;

public enum Vistas {

    GENERAL("General","vistas/general-view.fxml","informes.png"),
    CLIENTES("Clientes","vistas/clientes-view.fxml","clientes.png"),
    PELICULAS("Peliculas","vistas/peliculas-view.fxml","peliculas.png"),
    FUNCIONES("Funciones","vistas/funciones-view.fxml","funciones.png"),
    SALAS("Salas","vistas/salas-view.fxml","salas.png"),
    VENTAS("Ventas","vistas/ventas-view.fxml","ventas.png"),
    SUCURSALES("Sucursales","vistas/sucursales-view.fxml","sucursales.png"),
    ARTISTAS("Artistas","vistas/artistas-view.fxml","artistas.png"),
    CIUDADES("Ciudades","vistas/ciudades-view.fxml","ciudad.png"),
    GENEROS("Géneros","vistas/generos-view.fxml","genero.png"),
    IDIOMAS("Idiomas","vistas/idiomas-view.fxml","idiomas.png"),
    PAISES("Países","vistas/paises-view.fxml","paises.png");

    private final String nombreOpcion;
    private final String archivoVinculado;
    private final String archivoImagen;

    Vistas(String nombre, String archivo, String archivoImagen) {
        nombreOpcion = nombre;
        archivoVinculado = archivo;
        this.archivoImagen = archivoImagen;
    }

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public String getArchivoVinculado() {
        return archivoVinculado;
    }

    public String getArchivoImagen() {
        return archivoImagen;
    }
}
