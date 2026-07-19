package proyecto.com.proyectobasesdedatos.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.CargadorDatos;


public class Cine {
    private static Cine cine;

    private final ObservableList<Cliente> listaClientes;
    private final ObservableList<Pelicula> listaPeliculas;
    private final ObservableList<Sala> listaSalas;
    private final ObservableList<Funcion> listaFunciones;
    private final ObservableList<Genero> listaGeneros;
    private final ObservableList<Actor> listaActores;
    private final ObservableList<Empleado> listaEmpleados;
    private final ObservableList<Director> listaDirectores;
    private final ObservableList<PuestoTrabajo> listaPuestoTrabajo;
    private final ObservableList<Idioma> listaIdiomas;


    private Cine() {
        this.listaClientes = FXCollections.observableArrayList();
        this.listaPeliculas = FXCollections.observableArrayList();
        this.listaSalas = FXCollections.observableArrayList();
        this.listaFunciones = FXCollections.observableArrayList();
        this.listaGeneros = FXCollections.observableArrayList();
        this.listaActores = FXCollections.observableArrayList();
        this.listaDirectores = FXCollections.observableArrayList();
        this.listaEmpleados = FXCollections.observableArrayList();
        this.listaPuestoTrabajo = FXCollections.observableArrayList();
        this.listaIdiomas = FXCollections.observableArrayList();
        cine = this;

        new CargadorDatos().cargarDatos();
    }



    public static Cine getInstance() {
        if(cine == null){
            cine = new Cine();
        }
        return cine;
    }

    public ObservableList<Genero> getListaGeneros() {return listaGeneros;}

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


    public ObservableList<Actor> getListaActores() {
        return listaActores;
    }

    public ObservableList<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public ObservableList<Director> getListaDirectores() {
        return listaDirectores;
    }

    public ObservableList<PuestoTrabajo> getListaPuestoTrabajo() {
        return listaPuestoTrabajo;
    }

    public ObservableList<Idioma> getListaIdiomas() {
        return listaIdiomas;
    }
}
