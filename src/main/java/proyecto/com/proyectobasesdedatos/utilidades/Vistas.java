package proyecto.com.proyectobasesdedatos.utilidades;

public enum Vistas {

    GENERAL("Vista General","vistas/general-view.fxml","informes.png"),
    CLIENTES("Vista de Clientes","vistas/clientes-view.fxml","clientes.png"),
    PELICULAS("Vista de Peliculas","vistas/peliculas-view.fxml","peliculas.png"),
    FUNCIONES("Vista de Funciones","vistas/funciones-view.fxml","funciones.png"),
    SALAS("Salas","vistas/salas-view.fxml","salas.png"),
    VENTAS("Ventas","vistas/ventas-view.fxml","ventas.png"),
    SUCURSALES("Sucursales","vistas/sucursales-view.fxml","sucursales.png"),
    CIUDADES("Vista de Ciudades","vistas/ciudades-view.fxml","ciudad.png"),
    GENEROS("Vista de Géneros","vistas/generos-view.fxml","genero.png"),
    IDIOMAS("Vista de Idiomas","vistas/idiomas-view.fxml","idiomas.png"),
    SECTORES("Vista de Sectores","vistas/sectores-view.fxml","sectores.png"),
    MUNICIPIOS("Vista de Municipios","vistas/municipios-view.fxml","municipios.png"),
    PAISES("Vista de Países","vistas/paises-view.fxml","paises.png");

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
