package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Actor;
import proyecto.com.proyectobasesdedatos.modelos.Cine;

import java.sql.*;

public class ServicioActores implements Servicio<Actor>{
    @Override
    public ObservableList<Actor> consultar() {
        return Cine.getInstance().getListaActores();
    }

    @Override
    public boolean crear(Actor entidad) {
        String sql = "INSERT INTO Actores (nombres, apellidos, fechaNacimiento, sexo) VALUES (?, ?, ?, ?)";

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
                        Cine.getInstance().getListaActores().add(entidad);
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al crear actor: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Actor entidad) {
        String sql = "UPDATE Actores SET nombres = ?, apellidos = ?, fechaNacimiento = ?, sexo = ? WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, entidad.getNombre());
            pst.setString(2, entidad.getApellido());
            pst.setDate(3, java.sql.Date.valueOf(entidad.getFechaNacimiento()));
            pst.setString(4, String.valueOf(entidad.getSexo()));
            pst.setInt(5, entidad.getCodigo());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Actor actorViejo = buscar(entidad.getCodigo());
                if (actorViejo != null) {
                    int index = Cine.getInstance().getListaActores().indexOf(actorViejo);
                    Cine.getInstance().getListaActores().set(index, entidad);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al actualizar actor: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(Actor entidad) {
        String sql = "DELETE FROM Actores WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, entidad.getCodigo());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Actor actorViejo = buscar(entidad.getCodigo());
                if (actorViejo != null) {
                    Cine.getInstance().getListaActores().remove(actorViejo);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al eliminar actor: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Actor buscar(int codigo) {
        for (Actor a : Cine.getInstance().getListaActores()) {
            if (a.getCodigo() == codigo)
                return a;
        }
        return null;
    }

    @Override
    public void cargar() {
        String sql = "SELECT codigo, nombres, apellidos, fechaNacimiento, sexo FROM Actores";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            Cine.getInstance().getListaActores().clear();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");

                java.sql.Date fechaSql = rs.getDate("fechaNacimiento");
                java.time.LocalDate fechaNacimiento = fechaSql.toLocalDate();

                String sexoStr = rs.getString("sexo");
                char sexo = sexoStr.charAt(0);

                Actor actor = new Actor(codigo, nombre, apellido, fechaNacimiento, sexo);
                Cine.getInstance().getListaActores().add(actor);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar actores: " + e.getMessage());
        }

    }
}
