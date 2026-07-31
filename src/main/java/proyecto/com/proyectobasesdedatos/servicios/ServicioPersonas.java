package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Persona;
import proyecto.com.proyectobasesdedatos.modelos.Sector;

public class ServicioPersonas extends Servicio<Persona> {
    private static ServicioPersonas instancia;
    private static final List<Persona> personas = new ArrayList<>();
    private final ServicioSectores servicioSectores;

    private ServicioPersonas() {
        super();
        servicioSectores = ServicioSectores.getInstance();
    }

    public static synchronized ServicioPersonas getInstance() {
        if (instancia == null) {
            instancia = new ServicioPersonas();
        }
        return instancia;
    }

    @Override
    public void cargar() {
        if (!personas.isEmpty()) {
            return;
        }

        String sql = "SELECT * FROM Personas ORDER BY codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Persona persona = new Persona();
                persona.setCodigo(rs.getInt("codigo"));
                persona.setNombres(rs.getString("nombres"));
                persona.setApellidos(rs.getString("apellidos"));
                persona.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                persona.setSexo(rs.getString("sexo").charAt(0));
                persona.setTelefono(rs.getString("telefono"));
                persona.setCorreo(rs.getString("correo"));

                int idSector = rs.getInt("id_sector_residencia");
                if (!rs.wasNull()) {
                    Sector sector = servicioSectores.obtenerPorCodigo(idSector);
                    persona.setSectorResidencia(sector);
                }

                personas.add(persona);
            }

            System.out.println("Cargadas " + personas.size() + " personas");

        } catch (SQLException e) {
            System.err.println("Error al cargar personas: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Persona> obtenerTodos() {
        if (personas.isEmpty()) {
            cargar();
        }
        return new ArrayList<>(personas);
    }

    @Override
    public Persona obtenerPorCodigo(int codigo) {
        if (personas.isEmpty()) {
            cargar();
        }
        return personas.stream()
                .filter(p -> p.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }
}