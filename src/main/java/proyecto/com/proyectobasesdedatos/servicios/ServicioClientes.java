package proyecto.com.proyectobasesdedatos.servicios;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.modelos.Cine;
import java.sql.*;


public class ServicioClientes implements Servicio<Cliente> {
    public ServicioClientes(){}

    @Override
    public ObservableList<Cliente> consultar() {
        return Cine.getInstance().getListaClientes();
    }

    @Override
    public boolean crear(Cliente entidad){
        return false;
    }


    @Override
    public Cliente buscar(int codigo){
        for(Cliente cliente : Cine.getInstance().getListaClientes()){
            if(cliente.getCodigo() == codigo){
                return cliente;
            }
        }
        return null;

    }
    @Override
    public boolean actualizar(Cliente entidad){
        return false;
    }
    public boolean eliminar(Cliente cliente){
        String sqlEliminar = "DELETE FROM Clientes WHERE codigo = ?";
        try(Connection con = ConexionBD.obtenerConexion();
            PreparedStatement pst = con.prepareStatement(sqlEliminar)){

            pst.setInt(1, cliente.getCodigo());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Cliente clienteViejo = buscar(cliente.getCodigo());
                if(clienteViejo != null){
                    Cine.getInstance().getListaClientes().remove(clienteViejo);
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void cargar(){

    }
}
