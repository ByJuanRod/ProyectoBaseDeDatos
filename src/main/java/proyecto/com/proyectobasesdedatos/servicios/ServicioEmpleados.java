package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Empleado;
import proyecto.com.proyectobasesdedatos.modelos.Persona;
import proyecto.com.proyectobasesdedatos.modelos.PuestoTrabajo;
import proyecto.com.proyectobasesdedatos.modelos.Sucursal;

public class ServicioEmpleados extends Servicio<Empleado> {
    private static final List<Empleado> empleados = new ArrayList<>();
    private final ServicioPersonas servicioPersonas;
    private final ServicioPuestosTrabajo servicioPuestos;
    private final ServicioSucursales servicioSucursales;

    public ServicioEmpleados() {
        super();
        servicioPersonas = new ServicioPersonas();
        servicioPuestos = new ServicioPuestosTrabajo();
        servicioSucursales = new ServicioSucursales();
    }

    @Override
    public void cargar() {
        String sql = "SELECT e.* FROM Empleados e " +
                "INNER JOIN Personas p ON e.codigo = p.codigo " +
                "ORDER BY e.codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Persona persona = servicioPersonas.obtenerPorCodigo(rs.getInt("codigo"));

                if (persona != null) {
                    Empleado empleado = new Empleado();
                    empleado.setCodigo(persona.getCodigo());
                    empleado.setNombres(persona.getNombres());
                    empleado.setApellidos(persona.getApellidos());
                    empleado.setFechaNacimiento(persona.getFechaNacimiento());
                    empleado.setSexo(persona.getSexo());
                    empleado.setTelefono(persona.getTelefono());
                    empleado.setCorreo(persona.getCorreo());
                    empleado.setSectorResidencia(persona.getSectorResidencia());

                    PuestoTrabajo puesto = servicioPuestos.obtenerPorCodigo(rs.getInt("codigo_puesto"));
                    empleado.setPuestoTrabajo(puesto);

                    empleado.setFechaContratacion(rs.getDate("fecha_contratacion"));
                    empleado.setSalario(rs.getDouble("salario"));

                    Sucursal sucursal = servicioSucursales.obtenerPorCodigo(rs.getInt("codigo_sucursal"));
                    empleado.setSucursal(sucursal);

                    empleados.add(empleado);
                }
            }

            System.out.println("Cargados " + empleados.size() + " empleados");

        } catch (SQLException e) {
            System.err.println("Error al cargar empleados: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Empleado> obtenerTodos() {
        return new ArrayList<>(empleados);
    }

    @Override
    public Empleado obtenerPorCodigo(int codigo) {
        return empleados.stream()
                .filter(e -> e.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }
}