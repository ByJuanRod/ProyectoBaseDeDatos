package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Actor;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;

public class ServicioActoresPeliculas {
    private static ServicioActoresPeliculas instancia;
    private final Connection conexion;
    private final ServicioActores servicioActores;
    private final ServicioPeliculas servicioPeliculas;
    private boolean cargado = false;

    private ServicioActoresPeliculas() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
            this.servicioActores = ServicioActores.getInstance();
            this.servicioPeliculas = ServicioPeliculas.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión", e);
        }
    }

    public static synchronized ServicioActoresPeliculas getInstance() {
        if (instancia == null) {
            instancia = new ServicioActoresPeliculas();
        }
        return instancia;
    }

    public void cargar() {
        if (cargado) {
            return;
        }

        String sql = "SELECT * FROM Actores_Peliculas";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int codigoPelicula = rs.getInt("codigo_pelicula");
                int codigoActor = rs.getInt("codigo_actor");

                Pelicula pelicula = servicioPeliculas.obtenerPorCodigo(codigoPelicula);
                Actor actor = servicioActores.obtenerPorCodigo(codigoActor);

                if (pelicula != null && actor != null) {
                    if (pelicula.getActores() == null) {
                        pelicula.setActores(new ArrayList<>());
                    }
                    pelicula.getActores().add(actor);
                }
            }

            cargado = true;
            System.out.println("Cargadas relaciones películas-actores");

        } catch (SQLException e) {
            System.err.println("Error al cargar actores de películas: " + e.getMessage());
        }
    }
}