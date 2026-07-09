package proyecto.com.proyectobasesdedatos.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.CargadorDatos;
import proyecto.com.proyectobasesdedatos.servicios.Servicio;
import proyecto.com.proyectobasesdedatos.servicios.ServicioClientes;
import proyecto.com.proyectobasesdedatos.servicios.ServicioGeneros;
import proyecto.com.proyectobasesdedatos.servicios.ServicioPeliculas;

import java.util.LinkedList;
import java.util.Queue;

public class Cine {
    private static Cine cine;

    private final ObservableList<Cliente> listaClientes;
    private final ObservableList<Pelicula> listaPeliculas;
    private final ObservableList<Sala> listaSalas;
    private final ObservableList<Funcion> listaFunciones;
    private final ObservableList<Genero> listaGeneros;

    private Cine() {
        this.listaClientes = FXCollections.observableArrayList();
        this.listaPeliculas = FXCollections.observableArrayList();
        this.listaSalas = FXCollections.observableArrayList();
        this.listaFunciones = FXCollections.observableArrayList();
        this.listaGeneros = FXCollections.observableArrayList();
        cine = this;

        new CargadorDatos().cargarDatos();
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
