package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.modelos.Persona;

public class ServicioClientes extends Servicio<Cliente> {
    private static final List<Cliente> clientes = new ArrayList<>();
    private final ServicioPersonas servicioPersonas;

    public ServicioClientes() {
        super();
        servicioPersonas = new ServicioPersonas();
    }

    @Override
    public void cargar() {
        String sql = "SELECT c.*, p.* FROM Clientes c " +
                "INNER JOIN Personas p ON c.codigo = p.codigo " +
                "ORDER BY c.codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Persona persona = servicioPersonas.obtenerPorCodigo(rs.getInt("codigo"));

                if (persona != null) {
                    Cliente cliente = new Cliente();
                    cliente.setCodigo(persona.getCodigo());
                    cliente.setNombres(persona.getNombres());
                    cliente.setApellidos(persona.getApellidos());
                    cliente.setFechaNacimiento(persona.getFechaNacimiento());
                    cliente.setSexo(persona.getSexo());
                    cliente.setTelefono(persona.getTelefono());
                    cliente.setCorreo(persona.getCorreo());
                    cliente.setSectorResidencia(persona.getSectorResidencia());
                    cliente.setCantidadEntradas(rs.getInt("cantidad_entradas"));
                    cliente.setFechaRegistro(rs.getTimestamp("fecha_registro"));

                    clientes.add(cliente);
                }
            }

            System.out.println("Cargados " + clientes.size() + " clientes");

        } catch (SQLException e) {
            System.err.println("Error al cargar clientes: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(clientes);
    }

    @Override
    public Cliente obtenerPorCodigo(int codigo) {
        return clientes.stream()
                .filter(c -> c.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }
}