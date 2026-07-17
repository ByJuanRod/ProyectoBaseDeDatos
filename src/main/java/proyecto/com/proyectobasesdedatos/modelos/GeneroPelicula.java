package proyecto.com.proyectobasesdedatos.modelos;

import java.util.ArrayList;
import java.util.List;

public class GeneroPelicula {
    private List<Pelicula> listaPeliculas;
    private List<Genero> listaGeneros;

    public GeneroPelicula() {
        this.listaPeliculas = new ArrayList<>();
        this.listaGeneros = new ArrayList<>();
    }

    public List<Pelicula> getListaPeliculas() {
        return listaPeliculas;
    }

    public void setListaPeliculas(List<Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }

    public List<Genero> getListaGeneros() {
        return listaGeneros;
    }

    public void setListaGeneros(List<Genero> listaGeneros) {
        this.listaGeneros = listaGeneros;
    }
}
