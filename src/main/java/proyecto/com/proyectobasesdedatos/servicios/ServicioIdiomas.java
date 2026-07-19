package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Cine;
import proyecto.com.proyectobasesdedatos.modelos.Idioma;

import java.sql.*;

public class ServicioIdiomas implements Servicio<Idioma>{
    @Override
    public ObservableList<Idioma> consultar() {
        return Cine.getInstance().getListaIdiomas();
    }

    @Override
    public boolean crear(Idioma entidad) {
        String sql = "INSERT INTO Idiomas (nombre) VALUES (?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, entidad.getNombre());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet keys = pst.getGeneratedKeys()) {
                    if (keys.next()) {
                        entidad.setCodigo(keys.getInt(1));
                        Cine.getInstance().getListaIdiomas().add(entidad);
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al crear idioma: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Idioma entidad) {
        String sql = "UPDATE Idiomas SET nombre = ? WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, entidad.getNombre());
            pst.setInt(2, entidad.getCodigo());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Idioma idiomaViejo = buscar(entidad.getCodigo());
                if (idiomaViejo != null) {
                    int index = Cine.getInstance().getListaIdiomas().indexOf(idiomaViejo);
                    Cine.getInstance().getListaIdiomas().set(index, entidad);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al actualizar idioma: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(Idioma entidad) {
        String sql = "DELETE FROM Idiomas WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, entidad.getCodigo());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Idioma idiomaViejo = buscar(entidad.getCodigo());
                if (idiomaViejo != null) {
                    Cine.getInstance().getListaIdiomas().remove(idiomaViejo);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al eliminar idioma: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Idioma buscar(int codigo) {
        for (Idioma i : Cine.getInstance().getListaIdiomas()) {
            if (i.getCodigo() == codigo)
                return i;
        }
        return null;
    }

    @Override
    public void cargar() {
        String sql = "SELECT codigo, nombre FROM Idiomas";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            Cine.getInstance().getListaIdiomas().clear();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");

                Idioma idioma = new Idioma(codigo, nombre);
                Cine.getInstance().getListaIdiomas().add(idioma);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar idiomas: " + e.getMessage());
        }

    }
}
