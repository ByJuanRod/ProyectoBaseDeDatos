package proyecto.com.proyectobasesdedatos.datos;

import proyecto.com.proyectobasesdedatos.servicios.Servicio;
import proyecto.com.proyectobasesdedatos.servicios.ServicioClientes;
import proyecto.com.proyectobasesdedatos.servicios.ServicioGeneros;
import proyecto.com.proyectobasesdedatos.servicios.ServicioPeliculas;

import java.util.LinkedList;
import java.util.Queue;

public class CargadorDatos {

    private final Queue<Servicio> servicios = new LinkedList<>();

    public CargadorDatos(){
        servicios.add(new ServicioClientes());
        servicios.add(new ServicioGeneros());
        servicios.add(new ServicioPeliculas());
        cargarDatos();
    }


    public void cargarDatos(){
        for(Servicio servicio : servicios){
            servicio.cargar();
        }
    }
}
