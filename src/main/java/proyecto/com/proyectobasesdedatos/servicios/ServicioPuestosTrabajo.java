package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.PuestoTrabajo;

public class ServicioPuestosTrabajo extends Servicio<PuestoTrabajo> {
    private static ServicioPuestosTrabajo instancia;
    private static final List<PuestoTrabajo> puestos = new ArrayList<>();

    private ServicioPuestosTrabajo() {
        super();
    }

    public static synchronized ServicioPuestosTrabajo getInstance() {
        if (instancia == null) {
            instancia = new ServicioPuestosTrabajo();
        }
        return instancia;
    }

    @Override
    public void cargar() {
        if (!puestos.isEmpty()) {
            return;
        }

        String sql = "SELECT * FROM Puestos_Trabajo ORDER BY codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                PuestoTrabajo puesto = new PuestoTrabajo();
                puesto.setCodigo(rs.getInt("codigo"));
                puesto.setNombre(rs.getString("nombre"));
                puesto.setSalarioBase(rs.getDouble("salario_base"));
                puestos.add(puesto);
            }

            System.out.println("Cargados " + puestos.size() + " puestos de trabajo");

        } catch (SQLException e) {
            System.err.println("Error al cargar puestos de trabajo: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<PuestoTrabajo> obtenerTodos() {
        if (puestos.isEmpty()) {
            cargar();
        }
        return new ArrayList<>(puestos);
    }

    @Override
    public PuestoTrabajo obtenerPorCodigo(int codigo) {
        if (puestos.isEmpty()) {
            cargar();
        }
        return puestos.stream()
                .filter(p -> p.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }
}