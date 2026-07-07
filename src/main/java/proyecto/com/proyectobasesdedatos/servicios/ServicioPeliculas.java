package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;
import proyecto.com.proyectobasesdedatos.modelos.Cine;

import java.sql.*;

public class ServicioPeliculas implements Servicio<Pelicula>{
    private final ObservableList<Pelicula> listaPeliculas;

    public ServicioPeliculas() {
        this.listaPeliculas = Cine.getInstance().getListaPeliculas();
    }
    @Override
    public ObservableList<Pelicula> consultar() {
        return this.listaPeliculas;
    }

    @Override
    public boolean crear(Pelicula entidad){
        String sql = "INSERT INTO Peliculas (nombre, director, genero, duracionMinutos, clasificacion) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, entidad.getNombre());
            pst.setString(2, entidad.getDirector());
            pst.setString(3, entidad.getGenero());
            pst.setInt(4, entidad.getDuracionMinutos());
            pst.setString(5, entidad.getClasificacion());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entidad.setCodigo(generatedKeys.getInt(1));
                        this.listaPeliculas.add(entidad);
                    }
                }
            }

            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    @Override
    public Pelicula buscar(int codigo){
        for(Pelicula entidad: this.listaPeliculas){
            if(entidad.getCodigo() == codigo){
                return entidad;
            }
        }
        return null;
    }

    @Override
    public boolean actualizar(Pelicula entidad) {
        String sql = "UPDATE Clientes SET nombre = ?, director = ?, genero = ?, duracionMinutos = ?, clasificacion = ?" +
                " WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, entidad.getNombre());
            pst.setString(2, entidad.getDirector());
            pst.setString(3, entidad.getGenero());
            pst.setInt(4, entidad.getDuracionMinutos());
            pst.setString(5, entidad.getClasificacion());
            pst.setInt(6, entidad.getCodigo());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Pelicula peliculaVieja = buscar(entidad.getCodigo());
                if(peliculaVieja != null){
                    int ind = this.listaPeliculas.indexOf(peliculaVieja);
                    this.listaPeliculas.set(ind, entidad);
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            return false;
        }
    }
    @Override
    public boolean eliminar(Pelicula entidad){
        String sqlEliminar = "DELETE FROM Peliculas WHERE codigo = ?";
        try(Connection con = ConexionBD.obtenerConexion();
            PreparedStatement pst = con.prepareStatement(sqlEliminar)){

            pst.setInt(1, entidad.getCodigo());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Pelicula peliculaVieja = buscar(entidad.getCodigo());
                if(peliculaVieja != null){
                    this.listaPeliculas.remove(peliculaVieja);
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            return false;
        }
    }
}
