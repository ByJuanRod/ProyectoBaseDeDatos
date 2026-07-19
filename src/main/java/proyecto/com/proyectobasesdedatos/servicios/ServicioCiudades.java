package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Cine;
import proyecto.com.proyectobasesdedatos.modelos.Ciudad;

import java.sql.*;


public class ServicioCiudades implements Servicio<Ciudad>{

    private ObservableList<Ciudad> listaCiudades;

    public ServicioCiudades() {
        this.listaCiudades = FXCollections.observableArrayList();
    }

    @Override
    public ObservableList<Ciudad> consultar() {
        return Cine.getInstance().getListaCiudades();
    }

    @Override
    public boolean crear(Ciudad entidad) {
        String sql = "INSERT INTO Ciudades (nombre, codigo_postal) VALUES (?, ?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, entidad.getNombre());
            pst.setString(2, entidad.getCodigoPostal());

            int filasAfectadas = pst.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet keys = pst.getGeneratedKeys()) {
                    if (keys.next()) {
                        entidad.setCodigo(keys.getInt(1));
                        Cine.getInstance().getListaCiudades().add(entidad);
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al crear ciudad: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean actualizar(Ciudad entidad) {
        String sql = "UPDATE Ciudades SET nombre = ?, codigo_postal = ? WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, entidad.getNombre());
            pst.setString(2, entidad.getCodigoPostal());
            pst.setInt(3, entidad.getCodigo());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Ciudad ciudadVieja = buscar(entidad.getCodigo());
                if (ciudadVieja != null) {
                    int index = Cine.getInstance().getListaCiudades().indexOf(ciudadVieja);
                    Cine.getInstance().getListaCiudades().set(index, entidad);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al actualizar ciudad: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(Ciudad entidad) {
        String sql = "DELETE FROM Ciudades WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, entidad.getCodigo());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Ciudad ciudadVieja = buscar(entidad.getCodigo());
                if (ciudadVieja != null) {
                    Cine.getInstance().getListaCiudades().remove(ciudadVieja);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al eliminar ciudad: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Ciudad buscar(int codigo) {
        for (Ciudad c : Cine.getInstance().getListaCiudades()) {
            if (c.getCodigo() == codigo)
                return c;
        }
        return null;
    }

    @Override
    public void cargar() {
        String sql = "SELECT * FROM Ciudades";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            Cine.getInstance().getListaCiudades().clear();

            while (rs.next()) {
                int codigo =  rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                String codigo_postal = rs.getString("codigo_postal");
                Ciudad ciudad = new Ciudad(codigo, nombre, codigo_postal);
                Cine.getInstance().getListaCiudades().add(ciudad);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar las ciudades: " + e.getMessage());
        }
    }
}
