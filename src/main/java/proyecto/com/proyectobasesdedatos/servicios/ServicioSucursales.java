package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Cine;
import proyecto.com.proyectobasesdedatos.modelos.Ciudad;
import proyecto.com.proyectobasesdedatos.modelos.Sucursal;

import java.sql.*;

public class ServicioSucursales implements Servicio<Sucursal>{
    @Override
    public ObservableList<Sucursal> consultar() {
        return Cine.getInstance().getListaSucursales();
    }

    @Override
    public boolean crear(Sucursal entidad) {
        String sql = "INSERT INTO Sucursales (nombre, direccion, telefono, correo, codigo_ciudad) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, entidad.getNombre());
            pst.setString(2, entidad.getDireccion());
            pst.setString(3, entidad.getTelefono());
            pst.setString(4, entidad.getCorreo());
            pst.setInt(5, entidad.getCiudad().getCodigo());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet keys = pst.getGeneratedKeys()) {
                    if (keys.next()) {
                        entidad.setCodigo(keys.getInt(1));
                        Cine.getInstance().getListaSucursales().add(entidad);
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al crear sucursal: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Sucursal entidad) {
        String sql = "UPDATE Sucursales SET nombre = ?, direccion = ?, telefono = ?, correo = ?, codigo_ciudad = ? WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, entidad.getNombre());
            pst.setString(2, entidad.getDireccion());
            pst.setString(3, entidad.getTelefono());
            pst.setString(4, entidad.getCorreo());
            pst.setInt(5, entidad.getCiudad().getCodigo());
            pst.setInt(6, entidad.getCodigo());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Sucursal sucursalVieja = buscar(entidad.getCodigo());
                if (sucursalVieja != null) {
                    int index = Cine.getInstance().getListaSucursales().indexOf(sucursalVieja);
                    Cine.getInstance().getListaSucursales().set(index, entidad);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al actualizar sucursal: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(Sucursal entidad) {
        String sql = "DELETE FROM Sucursales WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, entidad.getCodigo());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Sucursal sucursalVieja = buscar(entidad.getCodigo());
                if (sucursalVieja != null) {
                    Cine.getInstance().getListaSucursales().remove(sucursalVieja);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al eliminar sucursal: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Sucursal buscar(int codigo) {
        for (Sucursal s : Cine.getInstance().getListaSucursales()) {
            if (s.getCodigo() == codigo)
                return s;
        }
        return null;
    }

    @Override
    public void cargar() {
        String sql = "SELECT codigo, nombre, direccion, telefono, correo, codigo_ciudad FROM Sucursales";

        ServicioCiudades servicioCiudades = new ServicioCiudades();

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            Cine.getInstance().getListaSucursales().clear();

            while (rs.next()) {
                int codigoCiudad = rs.getInt("codigo_ciudad");

                Ciudad ciudadAsignada = servicioCiudades.buscar(codigoCiudad);

                Sucursal sucursal = new Sucursal(
                        rs.getInt("codigo"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        ciudadAsignada
                );

                Cine.getInstance().getListaSucursales().add(sucursal);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar sucursales: " + e.getMessage());
        }

    }
}
