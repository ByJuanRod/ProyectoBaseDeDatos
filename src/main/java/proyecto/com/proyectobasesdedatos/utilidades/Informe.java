package proyecto.com.proyectobasesdedatos.utilidades;

public enum Informe {
    BOLETOS_POR_DIA_SEMANA("Informe de Ventas Por Dias de Semana","Gráfico de Ventas Por Dias");
    Informe(String titulo, String nombreGrafico){
        this.titulo = titulo;
        this.nombreGrafico = nombreGrafico;
    }

    final String titulo;
    final String nombreGrafico;

    public String getTitulo() {
        return titulo;
    }
    public String getNombreGrafico() {
        return nombreGrafico;
    }

}
