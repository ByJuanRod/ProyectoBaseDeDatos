package proyecto.com.proyectobasesdedatos.servicios;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.modelos.Cine;


public class ServicioClientes implements Servicio<Cliente> {
    private final ObservableList<Cliente> listaClientes;

    public ServicioClientes(){
        this.listaClientes = Cine.getInstance().getListaClientes();
    }

    @Override
    public ObservableList<Cliente> consultar() {
        return this.listaClientes;
    }

    @Override
    public boolean crear(Cliente entidad){
        try{
            this.listaClientes.add(entidad);
            return true;
        }catch(Exception e){
            System.out.println("Error al crear cliente: " + e.getMessage());
            return false;
        }
    }


    @Override
    public Cliente buscar(int codigo){
        for(Cliente cliente : this.listaClientes){
            if(cliente.getCodigo() == codigo){
                return cliente;
            }
        }
        return null;

    }
    @Override
    public boolean actualizar(Cliente entidad){
        try{
            Cliente clienteActualizar = buscar(entidad.getCodigo());
            if(clienteActualizar != null){
                int indice =  this.listaClientes.indexOf(entidad);

                this.listaClientes.set(indice, entidad);
                return true;
            }
            return false;
        }catch(Exception e){
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }
    public boolean eliminar(Cliente cliente){

        try{
            Cliente clienteEliminar = buscar(cliente.getCodigo());
            if(clienteEliminar != null) {
                this.listaClientes.remove(clienteEliminar);
                return true;
            }
            return false;
        }catch(Exception e){
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}
