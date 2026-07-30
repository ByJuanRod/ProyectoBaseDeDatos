package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Asiento;
import proyecto.com.proyectobasesdedatos.modelos.Sala;

public class ServicioAsientos extends Servicio<Asiento> {
    private static final List<Asiento> asientos = new ArrayList<>();
    private final ServicioSalas servicioSalas;

    public ServicioAsientos() {
        super();
        servicioSalas = new ServicioSalas();
    }

    @Override
    public void cargar() {
        String sql = "SELECT * FROM Asientos ORDER BY codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Asiento asiento = new Asiento();
                asiento.setCodigo(rs.getInt("codigo"));
                asiento.setNumero(rs.getInt("numero"));
                asiento.setFila(rs.getString("fila"));

                int codigoSala = rs.getInt("codigo_sala");
                Sala sala = servicioSalas.obtenerPorCodigo(codigoSala);
                asiento.setSala(sala);

                asientos.add(asiento);
            }

            System.out.println("Cargados " + asientos.size() + " asientos");

        } catch (SQLException e) {
            System.err.println("Error al cargar asientos: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Asiento> obtenerTodos() {
        return new ArrayList<>(asientos);
    }

    @Override
    public Asiento obtenerPorCodigo(int codigo) {
        return asientos.stream()
                .filter(a -> a.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    public List<Asiento> obtenerPorSala(int codigoSala) {
        return asientos.stream()
                .filter(a -> a.getSala().getCodigo() == codigoSala)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}