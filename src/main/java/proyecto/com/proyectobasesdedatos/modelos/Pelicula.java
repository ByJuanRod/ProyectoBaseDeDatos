package proyecto.com.proyectobasesdedatos.modelos;

public class Pelicula {
    private int codigo;
    private String nombre;
    private String director;
    private String genero;
    private int duracion_minutos;
    private String clasificacion;

    public Pelicula(int codigo, String nombre, String director, String genero, int duracion_minutos, String clasificacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.director = director;
        this.genero = genero;
        this.duracion_minutos = duracion_minutos;
        this.clasificacion = clasificacion;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getDuracion_minutos() {
        return duracion_minutos;
    }

    public void setDuracion_minutos(int duracion_minutos) {
        this.duracion_minutos = duracion_minutos;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
}
