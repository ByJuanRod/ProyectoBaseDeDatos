package proyecto.com.proyectobasesdedatos.utilidades;

public enum OpcionMenu {

    INFORMES("Informes","vistas/informes-view.fxml"),
    CLIENTES("Clientes","vistas/clientes-view.fxml"),
    PELICULAS("Peliculas","vistas/peliculas-view.fxml"),
    FUNCIONES("Funciones","vistas/funciones-view.fxml"),
    SALAS("Salas","vistas/salas-view.fxml"),
    VENTAS("Ventas","vistas/ventas-view.fxml"),;

    private final String nombreOpcion;
    private final String archivoVinculado;

    OpcionMenu(String nombre, String archivo) {
        nombreOpcion = nombre;
        archivoVinculado = archivo;
    }

    public String getNombreOpcion() {
        return nombreOpcion;
    }

    public String getArchivoVinculado() {
        return archivoVinculado;
    }
}
