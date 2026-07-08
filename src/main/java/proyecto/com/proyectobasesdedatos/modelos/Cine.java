package proyecto.com.proyectobasesdedatos.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cine {
    private static Cine cine;
    private final ObservableList<Cliente> listaClientes;
    private final ObservableList<Pelicula> listaPeliculas;
    private final ObservableList<Sala> listaSalas;
    private final ObservableList<Funcion> listaFunciones;
    private final ObservableList<Genero> listaGeneros;

    private Cine() {
        this.listaClientes = FXCollections.observableArrayList();
        this.listaPeliculas = FXCollections.observableArrayList();;
        this.listaSalas = FXCollections.observableArrayList();;
        this.listaFunciones = FXCollections.observableArrayList();;
        this.listaGeneros = FXCollections.observableArrayList();
    }
    public static Cine getInstance() {
        if(cine == null){
            cine = new Cine();
        }
        return cine;
    }

    public ObservableList<Genero> getListaGeneros() {
        return listaGeneros;
    }

    public ObservableList<Cliente> getListaClientes() {
        return listaClientes;
    }


    public ObservableList<Pelicula> getListaPeliculas() {
        return listaPeliculas;
    }


    public ObservableList<Sala> getListaSalas() {
        return listaSalas;
    }


    public ObservableList<Funcion> getListaFunciones() {
        return listaFunciones;
    }

}
