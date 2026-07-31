package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Funcion;
import proyecto.com.proyectobasesdedatos.modelos.Idioma;
import proyecto.com.proyectobasesdedatos.modelos.Pelicula;
import proyecto.com.proyectobasesdedatos.modelos.Sala;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioFunciones {
    private static ServicioFunciones instancia;
    private final Connection conexion;
    private List<Funcion> funciones;

    private ServicioFunciones() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la base de datos", e);
        }
    }

    public static synchronized ServicioFunciones getInstance() {
        if (instancia == null) {
            instancia = new ServicioFunciones();
        }
        return instancia;
    }

    public void cargar() {
        if (funciones != null && !funciones.isEmpty()) {
            return;
        }

        funciones = new ArrayList<>();
        String sql = "SELECT f.*, p.nombre as nombre_pelicula, s.nombre as nombre_sala, " +
                "i.nombre as nombre_idioma_subtitulo " +
                "FROM Funciones f " +
                "INNER JOIN Peliculas p ON f.codigo_pelicula = p.codigo " +
                "INNER JOIN Salas s ON f.codigo_sala = s.codigo " +
                "LEFT JOIN Idiomas i ON f.codigo_idioma_subtitulo = i.codigo " +
                "ORDER BY f.codigo";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Funcion funcion = new Funcion();
                funcion.setCodigo(rs.getInt("codigo"));
                funcion.setFecha(rs.getDate("fecha"));
                funcion.setHoraInicio(rs.getTime("hora_inicio"));
                funcion.setHoraFin(rs.getTime("hora_fin"));
                funcion.setPrecioEntrada(rs.getDouble("precio_entrada"));

                Pelicula pelicula = new Pelicula();
                pelicula.setCodigo(rs.getInt("codigo_pelicula"));
                pelicula.setNombre(rs.getString("nombre_pelicula"));
                funcion.setPelicula(pelicula);

                Sala sala = new Sala();
                sala.setCodigo(rs.getInt("codigo_sala"));
                sala.setNombre(rs.getString("nombre_sala"));
                funcion.setSala(sala);

                int codigoIdiomaSubtitulo = rs.getInt("codigo_idioma_subtitulo");
                if (!rs.wasNull()) {
                    Idioma idiomaSubtitulo = new Idioma();
                    idiomaSubtitulo.setCodigo(codigoIdiomaSubtitulo);
                    idiomaSubtitulo.setNombre(rs.getString("nombre_idioma_subtitulo"));
                    funcion.setIdiomaSubtitulo(idiomaSubtitulo);
                }

                funciones.add(funcion);
            }
            System.out.println("Cargadas " + funciones.size() + " funciones");
        } catch (SQLException e) {
            System.err.println("Error al cargar funciones: " + e.getMessage());
        }
    }

    public List<Funcion> obtenerTodos() {
        if (funciones == null || funciones.isEmpty()) {
            cargar();
        }
        return funciones != null ? new ArrayList<>(funciones) : new ArrayList<>();
    }

    public ObservableList<Funcion> consultar() {
        ObservableList<Funcion> funcionesList = FXCollections.observableArrayList();
        if (funciones != null) {
            funcionesList.addAll(funciones);
        }
        return funcionesList;
    }

    public Funcion obtenerPorCodigo(int codigo) {
        if (funciones == null || funciones.isEmpty()) {
            cargar();
        }
        if (funciones != null) {
            return funciones.stream()
                    .filter(a -> a.getCodigo() == codigo)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}