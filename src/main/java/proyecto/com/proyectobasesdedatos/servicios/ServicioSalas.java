package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Asiento;
import proyecto.com.proyectobasesdedatos.modelos.Sala;
import proyecto.com.proyectobasesdedatos.modelos.Cine;
import proyecto.com.proyectobasesdedatos.modelos.Sucursal;

import java.sql.*;


public class ServicioSalas implements Servicio<Sala>{

    public ObservableList<Sala> listaSalas;

    public ServicioSalas() {
        this.listaSalas =  Cine.getInstance().getListaSalas();
    }

    @Override
    public ObservableList<Sala> consultar() {
        return Cine.getInstance().getListaSalas();
    }
    @Override
    public boolean crear (Sala entidad) {
        String sql = "INSERT INTO Salas (nombre, capacidad, codigo_sucursal) VALUES (?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, entidad.getNombre());
            pst.setInt(2, entidad.getCapacidad());

            pst.setInt(3, entidad.getSucursal().getCodigo());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet keys = pst.getGeneratedKeys()) {
                    if (keys.next()) {
                        entidad.setCodigo(keys.getInt(1));
                        Cine.getInstance().getListaSalas().add(entidad);
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al crear sala: " + e.getMessage());
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
        String sql = "DELETE FROM Salas WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, entidad.getCodigo());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Sala salaVieja = buscar(entidad.getCodigo());
                if (salaVieja != null) {
                    Cine.getInstance().getListaSalas().remove(salaVieja);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al eliminar sala: " + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean actualizar (Sala entidad) {
        String sql = "UPDATE Salas SET nombre = ?, capacidad = ?, codigo_sucursal = ? WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, entidad.getNombre());
            pst.setInt(2, entidad.getCapacidad());
            pst.setInt(3, entidad.getSucursal().getCodigo());
            pst.setInt(4, entidad.getCodigo());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Sala salaVieja = buscar(entidad.getCodigo());
                if (salaVieja != null) {
                    int index = Cine.getInstance().getListaSalas().indexOf(salaVieja);
                    Cine.getInstance().getListaSalas().set(index, entidad);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al actualizar sala: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void cargar(){
        String sql = "SELECT codigo, nombre, capacidad, codigo_sucursal FROM Salas";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            Cine.getInstance().getListaSalas().clear();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                int capacidad = rs.getInt("capacidad");
                int sucursalCodigo = rs.getInt("sucursal_codigo");

                Sucursal sucursal = new Sucursal();
                sucursal.setCodigo(sucursalCodigo);

                Sala sala = new Sala(codigo, nombre, capacidad, new java.util.ArrayList<>(), sucursal);
                Cine.getInstance().getListaSalas().add(sala);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar salas: " + e.getMessage());
        }

    }
}
