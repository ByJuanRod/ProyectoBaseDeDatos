package proyecto.com.proyectobasesdedatos.servicios;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.modelos.Cine;

import java.sql.*;
import java.time.LocalDate;


public class ServicioClientes implements Servicio<Cliente> {
    public ServicioClientes(){}

    @Override
    public ObservableList<Cliente> consultar() {
        return Cine.getInstance().getListaClientes();
    }

    @Override
    public boolean crear(Cliente entidad){
        String sql = "INSERT INTO Clientes (nombres, apellidos, telefono, fecha_registro, cantidad_entradas) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, entidad.getNombres());
            pst.setString(2, entidad.getApellidos());
            pst.setString(3, entidad.getTelefono());
            pst.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
            pst.setInt(5, entidad.getEntradas());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Cine.getInstance().getListaClientes().add(entidad);
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
        for(Cliente cliente : Cine.getInstance().getListaClientes()){
            if(cliente.getCodigo() == codigo){
                return cliente;
            }
        }
        return null;

    }
    @Override
    public boolean actualizar(Cliente entidad){
        String sql = "UPDATE Clientes SET nombres = ?, apellidos = ?, telefono = ?, fecha_registro = ?, cantidad_entradas = ?" +
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
                    int ind = Cine.getInstance().getListaClientes().indexOf(clienteViejo);
                    Cine.getInstance().getListaClientes().set(ind, entidad);
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
        String sql = "SELECT codigo, nombres, apellidos, telefono, fecha_registro, cantidad_entradas FROM Clientes";

        try(Connection con = ConexionBD.obtenerConexion();
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()){

            Cine.getInstance().getListaClientes().clear();

            while(rs.next()){
                int codigo = rs.getInt("codigo");
                String nombres = rs.getString("nombres");
                String apellidos = rs.getString("apellidos");
                String telefono = rs.getString("telefono");
                java.sql.Date fechaSQL = rs.getDate("fecha_registro");
                java.time.LocalDate fechaRegistro = fechaSQL.toLocalDate();
                int entradas = rs.getInt("cantidad_entradas");
                Cliente clt = new Cliente(codigo, entradas, nombres, apellidos, telefono, fechaRegistro);

                Cine.getInstance().getListaClientes().add(clt);
            }
        }catch(SQLException e){
            System.out.println("Error al cargar clientes: " + e.getMessage());
        }
    }
}
