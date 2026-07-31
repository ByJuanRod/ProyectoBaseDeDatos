package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import proyecto.com.proyectobasesdedatos.modelos.Asiento;
import proyecto.com.proyectobasesdedatos.modelos.Boleto;
import proyecto.com.proyectobasesdedatos.modelos.Funcion;
import proyecto.com.proyectobasesdedatos.modelos.Venta;

public class ServicioBoletos extends Servicio<Boleto> {
    private static ServicioBoletos instancia;
    private final static List<Boleto> boletos = new ArrayList<>();
    // No inicializar otros servicios en el constructor
    private ServicioVentas servicioVentas;
    private ServicioFunciones servicioFunciones;
    private ServicioAsientos servicioAsientos;

    private ServicioBoletos() {
        super();
        // No obtener otros servicios aquí para evitar dependencia circular
    }

    public static synchronized ServicioBoletos getInstance() {
        if (instancia == null) {
            instancia = new ServicioBoletos();
        }
        return instancia;
    }

    /**
     * Obtiene el servicio de ventas de forma perezosa (lazy)
     */
    private ServicioVentas getServicioVentas() {
        if (servicioVentas == null) {
            servicioVentas = ServicioVentas.getInstance();
        }
        return servicioVentas;
    }

    /**
     * Obtiene el servicio de funciones de forma perezosa (lazy)
     */
    private ServicioFunciones getServicioFunciones() {
        if (servicioFunciones == null) {
            servicioFunciones = ServicioFunciones.getInstance();
        }
        return servicioFunciones;
    }

    /**
     * Obtiene el servicio de asientos de forma perezosa (lazy)
     */
    private ServicioAsientos getServicioAsientos() {
        if (servicioAsientos == null) {
            servicioAsientos = ServicioAsientos.getInstance();
        }
        return servicioAsientos;
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

                Venta venta = getServicioVentas().obtenerPorCodigo(rs.getInt("codigo_venta"));
                boleto.setVenta(venta);

                Funcion funcion = getServicioFunciones().obtenerPorCodigo(rs.getInt("codigo_funcion"));
                boleto.setFuncion(funcion);

                Asiento asiento = getServicioAsientos().obtenerPorCodigo(rs.getInt("codigo_asiento"));
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

    /**
     * Obtiene los boletos de una función específica
     */
    public List<Boleto> obtenerBoletosPorFuncion(int codigoFuncion) {
        if (boletos.isEmpty()) {
            cargar();
        }
        return boletos.stream()
                .filter(b -> b.getFuncion() != null && b.getFuncion().getCodigo() == codigoFuncion)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene los asientos ocupados de una función específica
     * @param codigoFuncion Código de la función
     * @return Lista de asientos ocupados para esa función
     */
    public List<Asiento> obtenerAsientosOcupadosPorFuncion(int codigoFuncion) {
        // Obtener todos los boletos de la función
        List<Boleto> boletosFuncion = obtenerBoletosPorFuncion(codigoFuncion);
        List<Asiento> asientosOcupados = new ArrayList<>();

        // Recorrer los boletos y extraer los asientos
        for (Boleto boleto : boletosFuncion) {
            if (boleto.getAsiento() != null) {
                asientosOcupados.add(boleto.getAsiento());
            }
        }

        return asientosOcupados;
    }

    /**
     * Obtiene los códigos de los asientos ocupados de una función específica
     * @param codigoFuncion Código de la función
     * @return Lista de códigos de asientos ocupados
     */
    public List<Integer> obtenerCodigosAsientosOcupadosPorFuncion(int codigoFuncion) {
        List<Asiento> asientosOcupados = obtenerAsientosOcupadosPorFuncion(codigoFuncion);
        List<Integer> codigosAsientos = new ArrayList<>();

        for (Asiento asiento : asientosOcupados) {
            codigosAsientos.add(asiento.getCodigo());
        }

        return codigosAsientos;
    }

    /**
     * Verifica si un asiento está ocupado en una función específica
     * @param codigoFuncion Código de la función
     * @param codigoAsiento Código del asiento a verificar
     * @return true si el asiento está ocupado, false en caso contrario
     */
    public boolean isAsientoOcupado(int codigoFuncion, int codigoAsiento) {
        List<Asiento> asientosOcupados = obtenerAsientosOcupadosPorFuncion(codigoFuncion);

        for (Asiento asiento : asientosOcupados) {
            if (asiento.getCodigo() == codigoAsiento) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene el número de asientos ocupados en una función
     * @param codigoFuncion Código de la función
     * @return Cantidad de asientos ocupados
     */
    public int getAsientosOcupadosCount(int codigoFuncion) {
        return obtenerBoletosPorFuncion(codigoFuncion).size();
    }

    /**
     * Verifica si una función tiene asientos disponibles
     * @param codigoFuncion Código de la función
     * @param capacidadTotal Capacidad total de la sala
     * @return true si hay asientos disponibles, false en caso contrario
     */
    public boolean hayAsientosDisponibles(int codigoFuncion, int capacidadTotal) {
        int ocupados = getAsientosOcupadosCount(codigoFuncion);
        return ocupados < capacidadTotal;
    }

    /**
     * Obtiene la cantidad de asientos disponibles en una función
     * @param codigoFuncion Código de la función
     * @param capacidadTotal Capacidad total de la sala
     * @return Cantidad de asientos disponibles
     */
    public int getAsientosDisponiblesCount(int codigoFuncion, int capacidadTotal) {
        int ocupados = getAsientosOcupadosCount(codigoFuncion);
        return Math.max(0, capacidadTotal - ocupados);
    }

    /**
     * Limpia el caché (útil cuando se agregan nuevos boletos)
     */
    public void limpiarCache() {
        boletos.clear();
    }

    /**
     * Recarga los boletos (después de una venta)
     */
    public void recargar() {
        limpiarCache();
        cargar();
    }
}