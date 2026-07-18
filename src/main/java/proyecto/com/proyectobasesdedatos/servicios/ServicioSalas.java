package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.modelos.Asiento;
import proyecto.com.proyectobasesdedatos.modelos.Sala;
import proyecto.com.proyectobasesdedatos.modelos.Cine;


public class ServicioSalas implements Servicio<Sala>{

    public ObservableList<Sala> listaSalas;

    public ServicioSalas() {
        this.listaSalas =  Cine.getInstance().getListaSalas();
    }

    @Override
    public ObservableList<Sala> consultar() {
        return this.listaSalas;
    }
    @Override
    public boolean crear (Sala entidad) {
        return false;
    }

    @Override
    public Sala buscar(int codigo){
        for(Sala sala : this.listaSalas){
            if(sala.getCodigo() == codigo){
                return sala;
            }
        }
        return null;
    }

    @Override
    public boolean eliminar (Sala entidad) {
        try{
            Sala sala = buscar(entidad.getCodigo());
            if(sala != null){
                this.listaSalas.remove(sala);
                return true;
            }
            return false;
        }catch(Exception e){
            System.out.println("Error al eliminar sala: " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean actualizar (Sala entidad) {
        return false;
    }

    @Override
    public void cargar(){

    }
}
