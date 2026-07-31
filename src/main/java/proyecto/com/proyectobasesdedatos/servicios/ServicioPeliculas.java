package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Director;
import proyecto.com.proyectobasesdedatos.modelos.Idioma;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioPeliculas {
    private static ServicioPeliculas instancia;
    private final Connection conexion;
    private List<Pelicula> peliculas;

    private ServicioPeliculas() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la base de datos", e);
        }
    }

    public static synchronized ServicioPeliculas getInstance() {
        if (instancia == null) {
            instancia = new ServicioPeliculas();
        }
        return instancia;
    }

    public void cargar() {
        if (peliculas != null && !peliculas.isEmpty()) {
            return;
        }

        peliculas = new ArrayList<>();
        String sql = "SELECT p.*, " +
                "d.codigo as director_codigo, " +
                "per_d.nombres as director_nombres, " +
                "per_d.apellidos as director_apellidos, " +
                "i.codigo as idioma_codigo, " +
                "i.nombre as idioma_nombre " +
                "FROM Peliculas p " +
                "INNER JOIN Directores d ON p.codigo_director = d.codigo " +
                "INNER JOIN Personas per_d ON d.codigo = per_d.codigo " +
                "INNER JOIN Idiomas i ON p.idioma_audio = i.codigo " +
                "ORDER BY p.codigo";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pelicula pelicula = new Pelicula();
                pelicula.setCodigo(rs.getInt("codigo"));
                pelicula.setNombre(rs.getString("nombre"));
                pelicula.setDuracionMinutos(rs.getInt("duracion_minutos"));
                pelicula.setClasificacion(rs.getString("clasificacion"));

                byte[] portada = rs.getBytes("portada");
                pelicula.setPortada(portada);

                Director director = new Director();
                director.setCodigo(rs.getInt("director_codigo"));
                director.setNombres(rs.getString("director_nombres"));
                director.setApellidos(rs.getString("director_apellidos"));
                pelicula.setDirector(director);

                Idioma idiomaAudio = new Idioma();
                idiomaAudio.setCodigo(rs.getInt("idioma_codigo"));
                idiomaAudio.setNombre(rs.getString("idioma_nombre"));
                pelicula.setIdiomaAudio(idiomaAudio);

                peliculas.add(pelicula);
            }
            System.out.println("Cargadas " + peliculas.size() + " películas");
        } catch (SQLException e) {
            System.err.println("Error al cargar películas: " + e.getMessage());
        }
    }

    public List<Pelicula> obtenerTodos() {
        if (peliculas == null || peliculas.isEmpty()) {
            cargar();
        }
        return peliculas != null ? new ArrayList<>(peliculas) : new ArrayList<>();
    }

    public ObservableList<Pelicula> consultar() {
        ObservableList<Pelicula> peliculasList = FXCollections.observableArrayList();
        if (peliculas != null) {
            peliculasList.addAll(peliculas);
        }
        return peliculasList;
    }

    public Pelicula obtenerPorCodigo(int codigo) {
        if (peliculas == null || peliculas.isEmpty()) {
            cargar();
        }
        if (peliculas != null) {
            return peliculas.stream()
                    .filter(p -> p.getCodigo() == codigo)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}