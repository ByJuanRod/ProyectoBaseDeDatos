package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.PuestoTrabajo;

public class ServicioPuestosTrabajo extends Servicio<PuestoTrabajo> {
    private static final List<PuestoTrabajo> puestos = new ArrayList<>();

    @Override
    public void cargar() {
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
        return new ArrayList<>(puestos);
    }

    @Override
    public PuestoTrabajo obtenerPorCodigo(int codigo) {
        return puestos.stream()
                .filter(p -> p.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }
}