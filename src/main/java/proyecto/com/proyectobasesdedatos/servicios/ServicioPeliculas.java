package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.ClasificacionPelicula;
import proyecto.com.proyectobasesdedatos.modelos.Genero;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;
import proyecto.com.proyectobasesdedatos.modelos.Cine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioPeliculas implements Servicio<Pelicula>{

    public ServicioPeliculas() {}
    @Override
    public ObservableList<Pelicula> consultar() {
        return Cine.getInstance().getListaPeliculas();
    }

    @Override
    public boolean crear(Pelicula entidad){
        String sql = "INSERT INTO Peliculas (nombre, director, duracionMinutos, clasificacion) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setString(1, entidad.getNombre());
            pst.setString(2, entidad.getDirector());
            pst.setInt(3, entidad.getDuracionMinutos());
            pst.setString(4, entidad.getClasificacion());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entidad.setCodigo(generatedKeys.getInt(1));
                        guardarGeneros(con, entidad);
                        Cine.getInstance().getListaPeliculas().add(entidad);
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
        for(Pelicula entidad: Cine.getInstance().getListaPeliculas()){
            if(entidad.getCodigo() == codigo){
                return entidad;
            }
        }
        return null;
    }

    @Override
    public boolean actualizar(Pelicula entidad) {
        String sql = "UPDATE Peliculas SET nombre = ?, director = ?, duracionMinutos = ?, clasificacion = ?" +
                " WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, entidad.getNombre());
            pst.setString(2, entidad.getDirector());
            pst.setInt(3, entidad.getDuracionMinutos());
            pst.setString(4, entidad.getClasificacion());
            pst.setInt(5, entidad.getCodigo());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {

                String sqlBorrarGeneros = "DELETE FROM Generos_Peliculas WHERE id_pelicula = ?";
                try (PreparedStatement pstDel = con.prepareStatement(sqlBorrarGeneros)) {
                    pstDel.setInt(1, entidad.getCodigo());
                    pstDel.executeUpdate();
                }

                guardarGeneros(con, entidad);
                Pelicula peliculaVieja = buscar(entidad.getCodigo());
                if(peliculaVieja != null){
                    int ind = Cine.getInstance().getListaPeliculas().indexOf(peliculaVieja);
                    Cine.getInstance().getListaPeliculas().set(ind, entidad);
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
                    Cine.getInstance().getListaPeliculas().remove(peliculaVieja);
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public void cargar() {
        String sql = "SELECT codigo, nombre, director, duracion_minutos, clasificacion FROM Peliculas";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
             Cine.getInstance().getListaPeliculas().clear();

            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String nombre = rs.getString("nombre");
                String director = rs.getString("director");
                int duracionMinutos = rs.getInt("duracion_minutos");
                String clasificacionSQL = rs.getString("clasificacion");
                ClasificacionPelicula clasificacion = ClasificacionPelicula.valueOf(clasificacionSQL);

                Pelicula peli = new Pelicula(codigo, nombre, director, duracionMinutos, clasificacionSQL);
                List<Genero> generosDeEstaPelicula = obtenerGenerosDePelicula(con, codigo);
                peli.setListaGeneros(generosDeEstaPelicula);

                Cine.getInstance().getListaPeliculas().add(peli);
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar películas: " + e.getMessage());
        }
    }
    private void guardarGeneros(Connection con, Pelicula pelicula) throws SQLException {

        if (pelicula.getListaGeneros() != null && !pelicula.getListaGeneros().isEmpty()) {

            String sqlGeneros = "INSERT INTO Generos_Peliculas (codigo_pelicula, codigo_generos) VALUES (?, ?)";

            try (PreparedStatement pstGeneros = con.prepareStatement(sqlGeneros)) {
                for (Genero g : pelicula.getListaGeneros()) {
                    pstGeneros.setInt(1, pelicula.getCodigo());
                    pstGeneros.setInt(2, g.getCodigo());

                    pstGeneros.executeUpdate();
                }
            }
        }
    }
    private List<Genero> obtenerGenerosDePelicula(Connection con, int idPelicula) {
        List<Genero> generosEncontrados = new ArrayList<>();

        String sql = "SELECT g.codigo, g.nombre FROM Generos g " +
                "INNER JOIN Generos_Peliculas gp ON g.codigo = gp.codigo_generos " +
                "WHERE gp.codigo_pelicula = ?";

        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, idPelicula);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int codGenero = rs.getInt("codigo");
                    String nombreGenero = rs.getString("nombre");

                    Genero genero = new Genero(codGenero, nombreGenero);
                    generosEncontrados.add(genero);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar géneros de la película " + idPelicula + ": " + e.getMessage());
        }

        return generosEncontrados;
    }
}
