package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Genero;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;

public class ServicioGenerosPeliculas {
    private final Connection conexion;
    private final ServicioGeneros servicioGeneros;
    private final ServicioPeliculas servicioPeliculas;

    public ServicioGenerosPeliculas() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
            this.servicioGeneros = new ServicioGeneros();
            this.servicioPeliculas = new ServicioPeliculas();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión", e);
        }
    }

    public void cargar() {
        String sql = "SELECT * FROM Generos_Peliculas";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int codigoPelicula = rs.getInt("codigo_pelicula");
                int codigoGenero = rs.getInt("codigo_generos");

                Pelicula pelicula = servicioPeliculas.obtenerPorCodigo(codigoPelicula);
                Genero genero = servicioGeneros.obtenerPorCodigo(codigoGenero);

                if (pelicula != null && genero != null) {
                    if (pelicula.getGeneros() == null) {
                        pelicula.setGeneros(new ArrayList<>());
                    }
                    pelicula.getGeneros().add(genero);
                }
            }

            System.out.println("Cargadas relaciones películas-géneros");

        } catch (SQLException e) {
            System.err.println("Error al cargar géneros de películas: " + e.getMessage());
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