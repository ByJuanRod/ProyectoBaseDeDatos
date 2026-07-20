package proyecto.com.proyectobasesdedatos.datos;

import proyecto.com.proyectobasesdedatos.servicios.*;

import java.util.LinkedList;
import java.util.Queue;

public class CargadorDatos {

    private final Queue<Servicio> servicios = new LinkedList<>();

    public CargadorDatos(){
        servicios.add(new ServicioPaises());
        servicios.add(new ServicioGeneros());
        servicios.add(new ServicioIdiomas());
        servicios.add(new ServicioCiudades());
        servicios.add(new ServicioSucursales());
        servicios.add(new ServicioClientes());

        /*


        servicios.add(new ServicioActores());
        servicios.add(new ServicioDirectores());
        servicios.add(new ServicioSalas());*/

        cargarDatos();
    }


    public void cargarDatos(){
        for(Servicio servicio : servicios){
            servicio.cargar();
        }
    }
}
