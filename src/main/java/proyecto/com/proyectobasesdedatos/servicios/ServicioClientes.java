package proyecto.com.proyectobasesdedatos.servicios;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.modelos.Cine;

import java.sql.*;


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
        String sql = "INSERT INTO Clientes (nombres, apellidos, telefono, fechaRegistro, cantidadEntradas) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, entidad.getNombres());
            pst.setString(2, entidad.getApellidos());
            pst.setString(3, entidad.getTelefono());
            pst.setDate(4, java.sql.Date.valueOf(entidad.getFecha()));
            pst.setInt(5, entidad.getEntradas());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entidad.setCodigo(generatedKeys.getInt(1));
                        this.listaClientes.add(entidad);
                    }
                }
            }

            return true;
        } catch (SQLException e) {
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
        String sql = "UPDATE Clientes SET nombres = ?, apellidos = ?, telefono = ?, fechaRegistro = ?, cantidadEntradas = ?" +
                " WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, entidad.getNombres());
            pst.setString(2, entidad.getApellidos());
            pst.setString(3, entidad.getTelefono());
            pst.setDate(4, java.sql.Date.valueOf(entidad.getFecha()));
            pst.setInt(5, entidad.getEntradas());
            pst.setInt(6, entidad.getCodigo());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Cliente clienteViejo = buscar(entidad.getCodigo());
                if(clienteViejo != null){
                    int ind = this.listaClientes.indexOf(clienteViejo);
                    this.listaClientes.set(ind, entidad);
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            return false;
        }
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
                    this.listaClientes.remove(clienteViejo);
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            return false;
        }
    }
}
