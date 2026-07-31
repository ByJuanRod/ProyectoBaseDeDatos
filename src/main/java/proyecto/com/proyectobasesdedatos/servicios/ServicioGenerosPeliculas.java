package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.sql.Connection;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Genero;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;

public class ServicioGenerosPeliculas {
    private static ServicioGenerosPeliculas instancia;
    private final Connection conexion;
    private final ServicioGeneros servicioGeneros;
    private final ServicioPeliculas servicioPeliculas;
    private boolean cargado = false;

    private ServicioGenerosPeliculas() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
            this.servicioGeneros = ServicioGeneros.getInstance();
            this.servicioPeliculas = ServicioPeliculas.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión", e);
        }
    }

    public static synchronized ServicioGenerosPeliculas getInstance() {
        if (instancia == null) {
            instancia = new ServicioGenerosPeliculas();
        }
        return instancia;
    }

    public void cargar() {
        if (cargado) {
            return;
        }

        String sql = "SELECT * FROM Generos_Peliculas";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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

            cargado = true;
            System.out.println("Cargadas relaciones películas-géneros");

        } catch (SQLException e) {
            System.err.println("Error al cargar géneros de películas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}