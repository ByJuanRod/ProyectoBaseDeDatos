package proyecto.com.proyectobasesdedatos.modelos;

import java.util.List;

public class Pelicula {
    private int codigo;
    private String nombre;
    private Director director;
    private int duracionMinutos;
    private String clasificacion;
    private Idioma idiomaAudio;
    private List<Idioma> subtitulos;
    private List<Genero> generos;
    private List<Actor> actores;
    private byte[] portada;

    public Pelicula() {}

    public Pelicula(int codigo, String nombre, Director director, int duracionMinutos,
                    String clasificacion, Idioma idiomaAudio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.director = director;
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;
        this.idiomaAudio = idiomaAudio;
    }

    public String getNombreDirector() {
        if (director != null) {
            return director.getNombres() + " " + director.getApellidos();
        }
        return "";
    }

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Director getDirector() { return director; }
    public void setDirector(Director director) { this.director = director; }
    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    public String getClasificacion() { return clasificacion; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }
    public Idioma getIdiomaAudio() { return idiomaAudio; }
    public void setIdiomaAudio(Idioma idiomaAudio) { this.idiomaAudio = idiomaAudio; }
    public List<Idioma> getSubtitulos() { return subtitulos; }
    public void setSubtitulos(List<Idioma> subtitulos) { this.subtitulos = subtitulos; }
    public List<Genero> getGeneros() { return generos; }
    public void setGeneros(List<Genero> generos) { this.generos = generos; }
    public List<Actor> getActores() { return actores; }
    public void setActores(List<Actor> actores) { this.actores = actores; }

    public void setPortada(byte[] portada) {
        this.portada = portada;
    }
}