package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Actor;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;

public class ServicioActoresPeliculas {
    private final Connection conexion;
    private final ServicioActores servicioActores;
    private final ServicioPeliculas servicioPeliculas;

    public ServicioActoresPeliculas() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
            this.servicioActores = new ServicioActores();
            this.servicioPeliculas = new ServicioPeliculas();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión", e);
        }
    }

    public void cargar() {
        String sql = "SELECT * FROM Actores_Peliculas";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int codigoPelicula = rs.getInt("codigo_pelicula");
                int codigoActor = rs.getInt("codigo_actor");

                Pelicula pelicula = servicioPeliculas.obtenerPorCodigo(codigoPelicula);
                Actor actor = servicioActores.obtenerPorCodigo(codigoActor);

                if (pelicula != null && actor != null) {
                    if (pelicula.getActores() == null) {
                        pelicula.setActores(new java.util.ArrayList<>());
                    }
                    pelicula.getActores().add(actor);
                }
            }

            System.out.println("Cargadas relaciones películas-actores");

        } catch (SQLException e) {
            System.err.println("Error al cargar actores de películas: " + e.getMessage());
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