package proyecto.com.proyectobasesdedatos.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cine {
    private static Cine cine;
    private ObservableList<Cliente> listaClientes;
    private ObservableList<Pelicula> listaPeliculas;
    private ObservableList<Sala> listaSalas;
    private ObservableList<Funcion> listaFunciones;

    private Cine() {
        this.listaClientes = FXCollections.observableArrayList();
        this.listaPeliculas = FXCollections.observableArrayList();;
        this.listaSalas = FXCollections.observableArrayList();;
        this.listaFunciones = FXCollections.observableArrayList();;
    }
    public static Cine getInstance() {
        if(cine == null){
            cine = new Cine();
        }
        return cine;
    }

    public ObservableList<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(ObservableList<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public ObservableList<Pelicula> getListaPeliculas() {
        return listaPeliculas;
    }

    public void setListaPeliculas(ObservableList<Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }

    public ObservableList<Sala> getListaSalas() {
        return listaSalas;
    }

    public void setListaSalas(ObservableList<Sala> listaSalas) {
        this.listaSalas = listaSalas;
    }

    public ObservableList<Funcion> getListaFunciones() {
        return listaFunciones;
    }

    public void setListaFunciones(ObservableList<Funcion> listaFunciones) {
        this.listaFunciones = listaFunciones;
    }
}
