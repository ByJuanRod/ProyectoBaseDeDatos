package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Actor;
import proyecto.com.proyectobasesdedatos.modelos.Persona;

public class ServicioActores extends Servicio<Actor> {
    private static ServicioActores instancia;
    private static final List<Actor> actores = new ArrayList<>();
    private final ServicioPersonas servicioPersonas;

    private ServicioActores() {
        super();
        servicioPersonas = ServicioPersonas.getInstance();
    }

    public static synchronized ServicioActores getInstance() {
        if (instancia == null) {
            instancia = new ServicioActores();
        }
        return instancia;
    }

    @Override
    public void cargar() {
        if (!actores.isEmpty()) {
            return;
        }

        String sql = "SELECT a.codigo FROM Actores a " +
                "INNER JOIN Personas p ON a.codigo = p.codigo " +
                "ORDER BY a.codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Persona persona = servicioPersonas.obtenerPorCodigo(rs.getInt("codigo"));

                if (persona != null) {
                    Actor actor = new Actor();
                    actor.setCodigo(persona.getCodigo());
                    actor.setNombres(persona.getNombres());
                    actor.setApellidos(persona.getApellidos());
                    actor.setFechaNacimiento(persona.getFechaNacimiento());
                    actor.setSexo(persona.getSexo());
                    actor.setTelefono(persona.getTelefono());
                    actor.setCorreo(persona.getCorreo());
                    actor.setSectorResidencia(persona.getSectorResidencia());

                    actores.add(actor);
                }
            }

            System.out.println("Cargados " + actores.size() + " actores");

        } catch (SQLException e) {
            System.err.println("Error al cargar actores: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Actor> obtenerTodos() {
        if (actores.isEmpty()) {
            cargar();
        }
        return new ArrayList<>(actores);
    }

    @Override
    public Actor obtenerPorCodigo(int codigo) {
        if (actores.isEmpty()) {
            cargar();
        }
        return actores.stream()
                .filter(a -> a.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }
}