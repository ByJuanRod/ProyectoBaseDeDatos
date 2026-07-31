package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Director;
import proyecto.com.proyectobasesdedatos.modelos.Persona;

public class ServicioDirectores extends Servicio<Director> {
    private static ServicioDirectores instancia;
    private static final List<Director> directores = new ArrayList<>();
    private final ServicioPersonas servicioPersonas;

    private ServicioDirectores() {
        super();
        servicioPersonas = ServicioPersonas.getInstance();
    }

    public static synchronized ServicioDirectores getInstance() {
        if (instancia == null) {
            instancia = new ServicioDirectores();
        }
        return instancia;
    }

    @Override
    public void cargar() {
        if (!directores.isEmpty()) {
            return;
        }

        String sql = "SELECT d.codigo FROM Directores d " +
                "INNER JOIN Personas p ON d.codigo = p.codigo " +
                "ORDER BY d.codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Persona persona = servicioPersonas.obtenerPorCodigo(rs.getInt("codigo"));

                if (persona != null) {
                    Director director = new Director();
                    director.setCodigo(persona.getCodigo());
                    director.setNombres(persona.getNombres());
                    director.setApellidos(persona.getApellidos());
                    director.setFechaNacimiento(persona.getFechaNacimiento());
                    director.setSexo(persona.getSexo());
                    director.setTelefono(persona.getTelefono());
                    director.setCorreo(persona.getCorreo());
                    director.setSectorResidencia(persona.getSectorResidencia());

                    directores.add(director);
                }
            }

            System.out.println("Cargados " + directores.size() + " directores");

        } catch (SQLException e) {
            System.err.println("Error al cargar directores: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Director> obtenerTodos() {
        if (directores.isEmpty()) {
            cargar();
        }
        return new ArrayList<>(directores);
    }

    @Override
    public Director obtenerPorCodigo(int codigo) {
        if (directores.isEmpty()) {
            cargar();
        }
        return directores.stream()
                .filter(d -> d.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }
}