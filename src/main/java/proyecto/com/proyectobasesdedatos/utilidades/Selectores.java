package proyecto.com.proyectobasesdedatos.utilidades;

public enum Selectores {
    CIUDADES("selectores/selector-ciudad.fxml"),;

    final String nombreArchivo;
    Selectores(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getArchivo(){
        return nombreArchivo;
    }
}
