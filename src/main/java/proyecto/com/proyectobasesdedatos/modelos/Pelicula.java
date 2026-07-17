package proyecto.com.proyectobasesdedatos.modelos;

import java.util.ArrayList;
import java.util.List;

public class Pelicula {
    private int codigo;
    private String nombre;
    private Director director;
    private List<Genero> listaGeneros;
    private List<Actor> listaActores;
    private int duracionMinutos;
    private String clasificacion;

    public Pelicula(int codigo, String nombre, Director director, int duracionMinutos, String clasificacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.director = director;
        this.listaGeneros = new ArrayList<>();
        this.duracionMinutos = duracionMinutos;
        this.clasificacion = clasificacion;
        this.listaActores = new ArrayList<>();
    }

    public List<Actor> getListaActores() {
        return listaActores;
    }

    public void setListaActores(List<Actor> listaActores) {
        this.listaActores = listaActores;
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

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Genero> getListaGeneros() {
        return listaGeneros;
    }

    public void setListaGeneros(List<Genero> listaGeneros) {
        this.listaGeneros = listaGeneros;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }
}
