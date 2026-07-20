package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Cine;
import proyecto.com.proyectobasesdedatos.modelos.Pais;

import java.sql.*;


public class ServicioPaises implements Servicio<Pais>{

    public ServicioPaises() {
    }

    @Override
    public ObservableList<Pais> consultar() {
        return Cine.getInstance().getListaPaises();
    }

    @Override
    public boolean crear(Pais entidad) {
        String sql = "INSERT INTO Paises (nombre) VALUES (?)";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, entidad.getNombre());

            int filasAfectadas = pst.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet keys = pst.getGeneratedKeys()) {
                    if (keys.next()) {
                        entidad.setCodigo(keys.getInt(1));
                        Cine.getInstance().getListaPaises().add(entidad);
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
    public boolean actualizar(Pais entidad) {
        String sql = "UPDATE Ciudades SET nombre = ? WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, entidad.getNombre());
            pst.setInt(2, entidad.getCodigo());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Pais paisViejo = buscar(entidad.getCodigo());
                if (paisViejo != null) {
                    int index = Cine.getInstance().getListaPaises().indexOf(paisViejo);
                    Cine.getInstance().getListaPaises().set(index, entidad);
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
    public boolean eliminar(Pais entidad) {
        String sql = "DELETE FROM Paises WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, entidad.getCodigo());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Pais paisViejo = buscar(entidad.getCodigo());
                if (paisViejo != null) {
                    Cine.getInstance().getListaPaises().remove(paisViejo);
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
    public Pais buscar(int codigo) {
        for (Pais p : Cine.getInstance().getListaPaises()) {
            if (p.getCodigo() == codigo)
                return p;
        }
        return null;
    }

    @Override
    public void cargar() {
        String sql = "SELECT codigo, nombre FROM Paises";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            Cine.getInstance().getListaCiudades().clear();

            while (rs.next()) {
                int codigo =  rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                Pais pais = new Pais(codigo, nombre);
                Cine.getInstance().getListaPaises().add(pais);
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar las ciudades: " + e.getMessage());
        }
    }
}
