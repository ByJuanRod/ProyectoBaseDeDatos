package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Funcion;
import proyecto.com.proyectobasesdedatos.modelos.Idioma;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;
import proyecto.com.proyectobasesdedatos.modelos.Sala;

public class ServicioFunciones extends Servicio<Funcion> {
    private final static List<Funcion> funciones = new ArrayList<>();
    private final ServicioPeliculas servicioPeliculas;
    private final ServicioSalas servicioSalas;
    private final ServicioIdiomas servicioIdiomas;

    public ServicioFunciones() {
        super();
        servicioPeliculas = new ServicioPeliculas();
        servicioSalas = new ServicioSalas();
        servicioIdiomas = new ServicioIdiomas();
    }

    @Override
    public void cargar() {
        String sql = "SELECT * FROM Funciones ORDER BY codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Funcion funcion = new Funcion();
                funcion.setCodigo(rs.getInt("codigo"));
                funcion.setFecha(rs.getDate("fecha"));
                funcion.setHoraInicio(rs.getTime("hora_inicio"));
                funcion.setHoraFin(rs.getTime("hora_fin"));
                funcion.setPrecioEntrada(rs.getDouble("precio_entrada"));

                Pelicula pelicula = servicioPeliculas.obtenerPorCodigo(rs.getInt("codigo_pelicula"));
                funcion.setPelicula(pelicula);

                Sala sala = servicioSalas.obtenerPorCodigo(rs.getInt("codigo_sala"));
                funcion.setSala(sala);

                int codigoSubtitulo = rs.getInt("codigo_idioma_subtitulo");
                if (!rs.wasNull()) {
                    Idioma idiomaSubtitulo = servicioIdiomas.obtenerPorCodigo(codigoSubtitulo);
                    funcion.setIdiomaSubtitulo(idiomaSubtitulo);
                }

                funciones.add(funcion);
            }

            System.out.println("Cargadas " + funciones.size() + " funciones");

        } catch (SQLException e) {
            System.err.println("Error al cargar funciones: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Funcion> obtenerTodos() {
        return new ArrayList<>(funciones);
    }

    @Override
    public Funcion obtenerPorCodigo(int codigo) {
        return funciones.stream()
                .filter(f -> f.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    public List<Funcion> obtenerPorPelicula(int codigoPelicula) {
        return funciones.stream()
                .filter(f -> f.getPelicula().getCodigo() == codigoPelicula)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Funcion> obtenerPorSala(int codigoSala) {
        return funciones.stream()
                .filter(f -> f.getSala().getCodigo() == codigoSala)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}