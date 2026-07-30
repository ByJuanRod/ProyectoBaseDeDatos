package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Director;
import proyecto.com.proyectobasesdedatos.modelos.Idioma;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;

public class ServicioPeliculas extends Servicio<Pelicula> {
    private static final List<Pelicula> peliculas = new ArrayList<>();
    private final ServicioDirectores servicioDirectores;
    private final ServicioIdiomas servicioIdiomas;

    public ServicioPeliculas() {
        super();
        servicioDirectores = new ServicioDirectores();
        servicioIdiomas = new ServicioIdiomas();
    }

    @Override
    public void cargar() {
        String sql = "SELECT * FROM Peliculas ORDER BY codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Pelicula pelicula = new Pelicula();
                pelicula.setCodigo(rs.getInt("codigo"));
                pelicula.setNombre(rs.getString("nombre"));
                pelicula.setDuracionMinutos(rs.getInt("duracion_minutos"));
                pelicula.setClasificacion(rs.getString("clasificacion"));

                Director director = servicioDirectores.obtenerPorCodigo(rs.getInt("codigo_director"));
                pelicula.setDirector(director);

                Idioma idiomaAudio = servicioIdiomas.obtenerPorCodigo(rs.getInt("idioma_audio"));
                pelicula.setIdiomaAudio(idiomaAudio);

                peliculas.add(pelicula);
            }

            System.out.println("Cargadas " + peliculas.size() + " películas");

        } catch (SQLException e) {
            System.err.println("Error al cargar películas: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Pelicula> obtenerTodos() {
        return new ArrayList<>(peliculas);
    }

    @Override
    public Pelicula obtenerPorCodigo(int codigo) {
        return peliculas.stream()
                .filter(p -> p.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }
}