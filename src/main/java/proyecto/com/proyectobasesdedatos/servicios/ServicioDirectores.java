package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Cine;
import proyecto.com.proyectobasesdedatos.modelos.Director;

import java.sql.*;

public class ServicioDirectores implements Servicio<Director> {
    @Override
    public ObservableList<Director> consultar() {
        return Cine.getInstance().getListaDirectores();
    }

    @Override
    public boolean crear(Director entidad) {
        String sql = "INSERT INTO Directores (nombre, apellido, fechaNacimiento, sexo) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, entidad.getNombre());
            pst.setString(2, entidad.getApellido());

            pst.setDate(3, java.sql.Date.valueOf(entidad.getFechaNacimiento()));

            pst.setString(4, String.valueOf(entidad.getSexo()));

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet keys = pst.getGeneratedKeys()) {
                    if (keys.next()) {
                        entidad.setCodigo(keys.getInt(1));
                        Cine.getInstance().getListaDirectores().add(entidad);
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al crear director: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Director entidad) {
        String sql = "UPDATE Directores SET nombre = ?, apellido = ?, fechaNacimiento = ?, sexo = ? WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, entidad.getNombre());
            pst.setString(2, entidad.getApellido());
            pst.setDate(3, java.sql.Date.valueOf(entidad.getFechaNacimiento()));
            pst.setString(4, String.valueOf(entidad.getSexo()));
            pst.setInt(5, entidad.getCodigo());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Director directorViejo = buscar(entidad.getCodigo());
                if (directorViejo != null) {
                    int index = Cine.getInstance().getListaDirectores().indexOf(directorViejo);
                    Cine.getInstance().getListaDirectores().set(index, entidad);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al actualizar director: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(Director entidad) {
        String sql = "DELETE FROM Directores WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, entidad.getCodigo());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Director directorViejo = buscar(entidad.getCodigo());
                if (directorViejo != null) {
                    Cine.getInstance().getListaDirectores().remove(directorViejo);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al eliminar director: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Director buscar(int codigo) {
        for (Director d : Cine.getInstance().getListaDirectores()) {
            if (d.getCodigo() == codigo)
                return d;
        }
        return null;
    }

    @Override
    public void cargar() {
        String sql = "SELECT codigo, nombre, apellido, fechaNacimiento, sexo FROM Directores";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            Cine.getInstance().getListaDirectores().clear();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");

                java.sql.Date fechaSql = rs.getDate("fechaNacimiento");
                java.time.LocalDate fechaNacimiento = fechaSql.toLocalDate();

                String sexoStr = rs.getString("sexo");
                char sexo = sexoStr.charAt(0);

                Director director = new Director(codigo, nombre, apellido, fechaNacimiento, sexo);
                Cine.getInstance().getListaDirectores().add(director);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar directores: " + e.getMessage());
        }
    }
}
