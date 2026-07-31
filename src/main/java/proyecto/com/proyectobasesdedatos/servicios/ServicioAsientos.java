package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Asiento;
import proyecto.com.proyectobasesdedatos.modelos.Sala;

public class ServicioAsientos extends Servicio<Asiento> {
    private static ServicioAsientos instancia;
    private static final List<Asiento> asientos = new ArrayList<>();
    private final ServicioSalas servicioSalas;

    private ServicioAsientos() {
        super();
        servicioSalas = ServicioSalas.getInstance();
    }

    public static synchronized ServicioAsientos getInstance() {
        if (instancia == null) {
            instancia = new ServicioAsientos();
        }
        return instancia;
    }

    @Override
    public void cargar() {
        if (!asientos.isEmpty()) {
            return;
        }

        // Asegurar que las salas estén cargadas
        if (servicioSalas.obtenerTodos().isEmpty()) {
            servicioSalas.cargar();
        }

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
                if (sala != null) {
                    asiento.setSala(sala);
                    if (sala.getAsientos() == null) {
                        sala.setAsientos(new ArrayList<>());
                    }
                    sala.getAsientos().add(asiento);
                } else {
                    System.err.println("Advertencia: Sala con código " + codigoSala + " no encontrada para asiento " + rs.getInt("codigo"));
                }

                asientos.add(asiento);
            }

            System.out.println("Cargados " + asientos.size() + " asientos");

        } catch (SQLException e) {
            System.err.println("Error al cargar asientos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Asiento> obtenerTodos() {
        if (asientos.isEmpty()) {
            cargar();
        }
        return new ArrayList<>(asientos);
    }

    @Override
    public Asiento obtenerPorCodigo(int codigo) {
        if (asientos.isEmpty()) {
            cargar();
        }
        return asientos.stream()
                .filter(a -> a.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    public List<Asiento> obtenerPorSala(int codigoSala) {
        if (asientos.isEmpty()) {
            cargar();
        }
        return asientos.stream()
                .filter(a -> a.getSala() != null && a.getSala().getCodigo() == codigoSala)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}