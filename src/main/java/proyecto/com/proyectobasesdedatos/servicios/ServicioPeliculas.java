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
        return false;
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
        return false;
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
