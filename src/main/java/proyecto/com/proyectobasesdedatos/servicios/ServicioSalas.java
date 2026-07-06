package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
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

       try{
           int capacidad = entidad.getCapacidad();
           for(int i = 0; i <= capacidad; i++){
               Asiento nuevoAsiento = new Asiento(0, entidad, "regular");
               entidad.agregarAsiento(nuevoAsiento);
           }
           this.listaSalas.add(entidad);
           return true;
       }catch(Exception e){
           System.out.println("Error al crear sala: " + e.getMessage());
           return false;
       }
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
        try{
            Sala sala = buscar(entidad.getCodigo());
            if(sala != null){
                int indice = this.listaSalas.indexOf(entidad);
                listaSalas.set(indice, entidad);
                return true;
            }
            return false;
        }catch(Exception e){
            System.out.println("Error al actualizar sala: " + e.getMessage());
            return false;
        }

    }
}
