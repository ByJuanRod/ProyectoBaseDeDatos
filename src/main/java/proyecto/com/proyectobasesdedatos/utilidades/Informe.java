package proyecto.com.proyectobasesdedatos.utilidades;

public enum Informe {
    BOLETOS_POR_DIA_SEMANA("Informe de Ventas Por Dias de Semana.","Gráfico de Ventas Por Dias","fecha.png",
            "Este informe contiene la información de emisión de boletos respecto a cada día de la semana."),

    GENEROS_CONSUMO("Informe de Segmentación Por Género.","Gráfico de Emisión por Géners","fecha.png",
                                   "Este informe contiene la información sobre la cantidad de boletos que se han emitido respecto a cada géneros de película."),

    INFORME_DISPONIBILIDAD("Informe de Disponibilidad de Películas Por Clasificación.","Gráfico de Distribución de Películas","fecha.png",
                                   "Este apartado contiene los detalles de la disponibilidad de películas según su clasificación.");


    Informe(String titulo, String nombreGrafico, String imagen, String descripcion){
        this.titulo = titulo;
        this.nombreGrafico = nombreGrafico;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    final String titulo;
    final String nombreGrafico;
    final String imagen;
    final String descripcion;

    public String getTitulo() {
        return titulo;
    }
    public String getNombreGrafico() {
        return nombreGrafico;
    }

    public String getImagen(){
        return imagen;
    }

    public String getDescripcion(){
        return descripcion;
    }

}
