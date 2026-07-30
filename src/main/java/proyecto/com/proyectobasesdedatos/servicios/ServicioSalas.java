package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Sala;
import proyecto.com.proyectobasesdedatos.modelos.Sucursal;

public class ServicioSalas extends Servicio<Sala> {
    private static final List<Sala> salas = new ArrayList<>();
    private final ServicioSucursales servicioSucursales;

    public ServicioSalas() {
        super();
        servicioSucursales = new ServicioSucursales();
    }

    @Override
    public void cargar() {
        String sql = "SELECT * FROM Salas ORDER BY codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Sala sala = new Sala();
                sala.setCodigo(rs.getInt("codigo"));
                sala.setNombre(rs.getString("nombre"));
                sala.setCapacidad(rs.getInt("capacidad"));

                int codigoSucursal = rs.getInt("codigo_sucursal");
                Sucursal sucursal = servicioSucursales.obtenerPorCodigo(codigoSucursal);
                sala.setSucursal(sucursal);

                salas.add(sala);
            }

            System.out.println("Cargadas " + salas.size() + " salas");

        } catch (SQLException e) {
            System.err.println("Error al cargar salas: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Sala> obtenerTodos() {
        return new ArrayList<>(salas);
    }

    @Override
    public Sala obtenerPorCodigo(int codigo) {
        return salas.stream()
                .filter(s -> s.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    public List<Sala> obtenerPorSucursal(int codigoSucursal) {
        return salas.stream()
                .filter(s -> s.getSucursal().getCodigo() == codigoSucursal)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}