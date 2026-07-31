package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Asiento;
import proyecto.com.proyectobasesdedatos.modelos.Boleto;
import proyecto.com.proyectobasesdedatos.modelos.Funcion;
import proyecto.com.proyectobasesdedatos.modelos.Venta;

public class ServicioBoletos extends Servicio<Boleto> {
    private static ServicioBoletos instancia;
    private final static List<Boleto> boletos = new ArrayList<>();
    private final ServicioVentas servicioVentas;
    private final ServicioFunciones servicioFunciones;
    private final ServicioAsientos servicioAsientos;

    private ServicioBoletos() {
        super();
        servicioVentas = ServicioVentas.getInstance();
        servicioFunciones = ServicioFunciones.getInstance();
        servicioAsientos = ServicioAsientos.getInstance();
    }

    public static synchronized ServicioBoletos getInstance() {
        if (instancia == null) {
            instancia = new ServicioBoletos();
        }
        return instancia;
    }

    @Override
    public void cargar() {
        if (!boletos.isEmpty()) {
            return;
        }

        String sql = "SELECT * FROM Boletos ORDER BY codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Boleto boleto = new Boleto();
                boleto.setCodigo(rs.getInt("codigo"));
                boleto.setPrecioAplicado(rs.getDouble("precio_aplicado"));

                Venta venta = servicioVentas.obtenerPorCodigo(rs.getInt("codigo_venta"));
                boleto.setVenta(venta);

                Funcion funcion = servicioFunciones.obtenerPorCodigo(rs.getInt("codigo_funcion"));
                boleto.setFuncion(funcion);

                Asiento asiento = servicioAsientos.obtenerPorCodigo(rs.getInt("codigo_asiento"));
                boleto.setAsiento(asiento);

                boletos.add(boleto);

                if (venta != null) {
                    if (venta.getBoletos() == null) {
                        venta.setBoletos(new ArrayList<>());
                    }
                    venta.getBoletos().add(boleto);
                }
            }

            System.out.println("Cargados " + boletos.size() + " boletos");

        } catch (SQLException e) {
            System.err.println("Error al cargar boletos: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Boleto> obtenerTodos() {
        if (boletos.isEmpty()) {
            cargar();
        }
        return new ArrayList<>(boletos);
    }

    @Override
    public Boleto obtenerPorCodigo(int codigo) {
        if (boletos.isEmpty()) {
            cargar();
        }
        return boletos.stream()
                .filter(b -> b.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }
}