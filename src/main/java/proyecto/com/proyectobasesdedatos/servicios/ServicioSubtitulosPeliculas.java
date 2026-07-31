package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Idioma;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;

public class ServicioSubtitulosPeliculas {
    private static ServicioSubtitulosPeliculas instancia;
    private final Connection conexion;
    private final ServicioIdiomas servicioIdiomas;
    private final ServicioPeliculas servicioPeliculas;
    private boolean cargado = false;

    private ServicioSubtitulosPeliculas() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
            this.servicioIdiomas = ServicioIdiomas.getInstance();
            this.servicioPeliculas = ServicioPeliculas.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión", e);
        }
    }

    public static synchronized ServicioSubtitulosPeliculas getInstance() {
        if (instancia == null) {
            instancia = new ServicioSubtitulosPeliculas();
        }
        return instancia;
    }

    public void cargar() {
        if (cargado) {
            return;
        }

        String sql = "SELECT * FROM Peliculas_Subtitulos";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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

            cargado = true;
            System.out.println("Cargadas relaciones películas-subtítulos");

        } catch (SQLException e) {
            System.err.println("Error al cargar subtítulos de películas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}