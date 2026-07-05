package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;

public class ServicioPeliculas implements Servicio<Pelicula>{
    private ObservableList<Pelicula> listaPeliculas;

    public ServicioPeliculas() {
        this.listaPeliculas = FXCollections.observableArrayList();
    }
    @Override
    public ObservableList<Pelicula> consultar() {
        return this.listaPeliculas;
    }

    @Override
    public boolean crear(Pelicula entidad){
        try{
            this.listaPeliculas.add(entidad);
            return true;
        }catch(Exception e){
            System.out.println("Error al crear Película: " + e.getMessage());
            return false;
        }
    }
    @Override
    public Pelicula buscar(int codigo){
        for(Pelicula entidad: this.listaPeliculas){
            if(entidad.getCodigo() == codigo){
                return entidad;
            }
        }
        return null;
    }

    @Override
    public boolean actualizar(Pelicula entidad) {
        try{
            Pelicula peliculaActu = buscar(entidad.getCodigo());
            if(peliculaActu != null){
                int indice = this.listaPeliculas.indexOf(peliculaActu);

                this.listaPeliculas.set(indice, entidad);
                return true;
            }
            return false;
        }catch(Exception e){
            System.out.println("Error al actualizar película: " + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean eliminar(Pelicula entidad){
        try{
            Pelicula pelicula = buscar(entidad.getCodigo());
            if(pelicula != null){
                this.listaPeliculas.remove(pelicula);
                return true;
            }
            return false;
        }catch(Exception e){
            System.out.println("Error al eliminar pelicula: " + e.getMessage());
            return false;
        }
    }
}
