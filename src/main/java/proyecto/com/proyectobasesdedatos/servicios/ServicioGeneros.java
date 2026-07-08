package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.modelos.Cine;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Genero;

import java.sql.*;

public class ServicioGeneros implements Servicio<Genero> {

    private final ObservableList<Genero> listaGeneros;

    public ServicioGeneros() {
        this.listaGeneros = Cine.getInstance().getListaGeneros();
    }

    @Override
    public ObservableList<Genero> consultar() {
        return this.listaGeneros;
    }

    public void cargarGeneros() {
        String sql = "SELECT codigo, nombre FROM Generos";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            this.listaGeneros.clear();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");

                Genero genero = new Genero(codigo, nombre);
                this.listaGeneros.add(genero);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar géneros: " + e.getMessage());
        }
    }

    @Override
    public boolean crear(Genero entidad) {
        String sql = "INSERT INTO Generos (nombre) VALUES (?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, entidad.getNombre());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet keys = pst.getGeneratedKeys()) {
                    if (keys.next()) {
                        entidad.setCodigo(keys.getInt(1));
                        this.listaGeneros.add(entidad);
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al crear género: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean actualizar(Genero entidad) {
        String sql = "UPDATE Generos SET nombre = ? WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, entidad.getNombre());
            pst.setInt(2, entidad.getCodigo());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Genero generoViejo = buscar(entidad.getCodigo());
                if (generoViejo != null) {
                    int index = this.listaGeneros.indexOf(generoViejo);
                    this.listaGeneros.set(index, entidad);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al actualizar género: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(Genero entidad) {
        String sql = "DELETE FROM Generos WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, entidad.getCodigo());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Genero generoViejo = buscar(entidad.getCodigo());
                if (generoViejo != null) {
                    this.listaGeneros.remove(generoViejo);
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error de BD al eliminar género: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Genero buscar(int codigo) {
        for (Genero g : this.listaGeneros) {
            if (g.getCodigo() == codigo)
                return g;
        }
        return null;
    }
}