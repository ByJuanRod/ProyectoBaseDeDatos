package proyecto.com.proyectobasesdedatos.utilidades;

public enum OpcionesMenu {

    INFORMES("Informes","informes-view.fxml"),
    CLIENTES("Clientes","clientes-view.fxml"),
    PELICULAS("Peliculas","peliculas-view.fxml"),
    FUNCIONES("Funciones","funciones-view.fxml"),
    SALAS("Salas","salas-view.fxml"),
    VENTAS("Ventas","ventas-view.fxml"),;

    private final String nombreOpcion;
    private final String archivoVinculado;

    OpcionesMenu(String nombre, String archivo) {
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
