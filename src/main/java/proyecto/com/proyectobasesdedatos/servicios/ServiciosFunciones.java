package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.modelos.Funcion;

public class ServiciosFunciones implements Servicio<Funcion>{
    public ObservableList<Funcion> listaFunciones;

    public ServiciosFunciones(){
        listaFunciones = FXCollections.observableArrayList();
    }
    @Override
    public ObservableList<Funcion> consultar() {
        return this.listaFunciones;
    }
    @Override
    public boolean crear(Funcion entidad){
        try{
            this.listaFunciones.add(entidad);
            return true;
        }catch(Exception e){
            System.out.println("Error al crear sala: " + e.getMessage());
            return false;
        }
    }
    @Override
    public Funcion buscar(int codigo){
        for(Funcion funcion: listaFunciones){
            if(funcion.getCodigo() == codigo){
                return funcion;
            }
        }
        return null;
    }
    @Override
    public boolean actualizar(Funcion entidad){
        try{
            Funcion funcionActu = buscar(entidad.getCodigo());
            if(funcionActu != null){
                int ind = this.listaFunciones.indexOf(entidad);
                this.listaFunciones.set(ind, entidad);
                return true;
            }
            return false;
        }catch(Exception e){
            System.out.println("Error al actualizar funcion: " + e.getMessage());
            return false;
        }
    }
    @Override
    public boolean eliminar(Funcion entidad){
        try{
            Funcion funcion = buscar(entidad.getCodigo());
            if(funcion != null){
                this.listaFunciones.remove(funcion);
                return true;
            }
            return false;
        }catch(Exception e){
            System.out.println("Error al eliminar funcion: " + e.getMessage());
            return false;
        }
    }
}
