package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Idioma;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;

public class ServicioSubtitulosPeliculas {
    private final Connection conexion;
    private final ServicioIdiomas servicioIdiomas;
    private final ServicioPeliculas servicioPeliculas;

    public ServicioSubtitulosPeliculas() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
            this.servicioIdiomas = new ServicioIdiomas();
            this.servicioPeliculas = new ServicioPeliculas();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión", e);
        }
    }

    public void cargar() {
        String sql = "SELECT * FROM Peliculas_Subtitulos";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int codigoPelicula = rs.getInt("codigo_pelicula");
                int codigoIdioma = rs.getInt("codigo_idioma");

                Pelicula pelicula = servicioPeliculas.obtenerPorCodigo(codigoPelicula);
                Idioma idioma = servicioIdiomas.obtenerPorCodigo(codigoIdioma);

                if (pelicula != null && idioma != null) {
                    if (pelicula.getSubtitulos() == null) {
                        pelicula.setSubtitulos(new java.util.ArrayList<>());
                    }
                    pelicula.getSubtitulos().add(idioma);
                }
            }

            System.out.println("Cargadas relaciones películas-subtítulos");

        } catch (SQLException e) {
            System.err.println("Error al cargar subtítulos de películas: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}