package proyecto.com.proyectobasesdedatos.modelos;

import java.util.ArrayList;
import java.util.List;

public class Pelicula {
    private int codigo;
    private String nombre;
    private String director;
    private List<Genero> listaGeneros;
    private int duracionMinutos;
    private String clasificacion;

    public Pelicula(int codigo, String nombre, String director, int duracionMinutos, String clasificacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.director = director;
        this.listaGeneros = new ArrayList<>();
        this.duracionMinutos = duracionMinutos;
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
