package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Asiento;
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
    private final ServicioBoletos servicioBoletos;

    private ServicioFunciones() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
            this.servicioBoletos = ServicioBoletos.getInstance();
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
        String sql = "SELECT f.*, " +
                "p.codigo as pelicula_codigo, " +
                "p.nombre as pelicula_nombre, " +
                "p.duracion_minutos, " +
                "p.clasificacion, " +
                "s.codigo as sala_codigo, " +
                "s.nombre as sala_nombre, " +
                "s.capacidad, " +
                "i.codigo as idioma_codigo, " +
                "i.nombre as idioma_nombre " +
                "FROM Funciones f " +
                "INNER JOIN Peliculas p ON f.codigo_pelicula = p.codigo " +
                "INNER JOIN Salas s ON f.codigo_sala = s.codigo " +
                "LEFT JOIN Idiomas i ON f.codigo_idioma_subtitulo = i.codigo " +
                "ORDER BY f.codigo";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            // Asegurar que los boletos estén cargados
          //  servicioBoletos.cargar();

            while (rs.next()) {
                Funcion funcion = new Funcion();
                funcion.setCodigo(rs.getInt("codigo"));
                funcion.setFecha(rs.getDate("fecha"));
                funcion.setHoraInicio(rs.getTime("hora_inicio"));
                funcion.setHoraFin(rs.getTime("hora_fin"));
                funcion.setPrecioEntrada(rs.getDouble("precio_entrada"));

                // Crear la película con todos sus datos
                Pelicula pelicula = new Pelicula();
                pelicula.setCodigo(rs.getInt("pelicula_codigo"));
                pelicula.setNombre(rs.getString("pelicula_nombre"));
                pelicula.setDuracionMinutos(rs.getInt("duracion_minutos"));
                pelicula.setClasificacion(rs.getString("clasificacion"));
                funcion.setPelicula(pelicula);

                // Crear la sala con todos sus datos
                Sala sala = new Sala();
                sala.setCodigo(rs.getInt("sala_codigo"));
                sala.setNombre(rs.getString("sala_nombre"));
                sala.setCapacidad(rs.getInt("capacidad"));
                funcion.setSala(sala);

                // Idioma de subtítulo (si existe)
                int codigoIdiomaSubtitulo = rs.getInt("idioma_codigo");
                if (!rs.wasNull()) {
                    Idioma idiomaSubtitulo = new Idioma();
                    idiomaSubtitulo.setCodigo(codigoIdiomaSubtitulo);
                    idiomaSubtitulo.setNombre(rs.getString("idioma_nombre"));
                    funcion.setIdiomaSubtitulo(idiomaSubtitulo);
                }

                funciones.add(funcion);
            }
            System.out.println("Cargadas " + funciones.size() + " funciones");

        } catch (SQLException e) {
            System.err.println("Error al cargar funciones: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Asiento> getAsientosOcupados(int codigoFuncion) {
        return servicioBoletos.obtenerAsientosOcupadosPorFuncion(codigoFuncion);
    }

    public boolean isAsientoOcupado(int codigoFuncion, int codigoAsiento) {
        return servicioBoletos.isAsientoOcupado(codigoFuncion, codigoAsiento);
    }

    /** Cuenta cuántos asientos tiene REALMENTE insertados la sala de esta función. */
    public int getCapacidadTotal(int codigoFuncion) {
        Funcion funcion = obtenerPorCodigo(codigoFuncion);
        if (funcion == null || funcion.getSala() == null) {
            return 0;
        }

        String sql = "SELECT COUNT(*) as total FROM Asientos WHERE codigo_sala = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, funcion.getSala().getCodigo());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al contar los asientos de la sala: " + e.getMessage());
        }
        return 0;
    }

    public int getCapacidadRestante(int codigoFuncion) {
        int capacidadTotal = getCapacidadTotal(codigoFuncion);
        int ocupados = servicioBoletos.getAsientosOcupadosCount(codigoFuncion);
        return Math.max(0, capacidadTotal - ocupados);
    }

    public int[] getCapacidadRestanteConTotal(int codigoFuncion) {
        int capacidadTotal = getCapacidadTotal(codigoFuncion);
        int ocupados = servicioBoletos.getAsientosOcupadosCount(codigoFuncion);
        int restante = Math.max(0, capacidadTotal - ocupados);
        return new int[]{restante, capacidadTotal};
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

    public void recargar() {
        funciones = null;
        cargar();
    }
}