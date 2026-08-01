package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.modelos.Empleado;
import proyecto.com.proyectobasesdedatos.modelos.Sucursal;
import proyecto.com.proyectobasesdedatos.modelos.Venta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioVentas {
    private static ServicioVentas instancia;
    private final Connection conexion;
    private List<Venta> ventas;

    private ServicioVentas() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la base de datos", e);
        }
    }

    public static synchronized ServicioVentas getInstance() {
        if (instancia == null) {
            instancia = new ServicioVentas();
        }
        return instancia;
    }

    public void cargar() {
        if (ventas != null && !ventas.isEmpty()) {
            return;
        }

        ventas = new ArrayList<>();
        String sql = "SELECT v.*, p.nombres, p.apellidos, p2.nombres as empleado_nombres, p2.apellidos as empleado_apellidos, " +
                "s.nombre as sucursal_nombre, " +
                "COUNT(b.codigo) as cantidad_boletos " +
                "FROM Ventas v " +
                "INNER JOIN Clientes c ON v.codigo_cliente = c.codigo " +
                "INNER JOIN Personas p ON c.codigo = p.codigo " +
                "INNER JOIN Empleados e ON v.codigo_empleado = e.codigo " +
                "INNER JOIN Personas p2 ON e.codigo = p2.codigo " +
                "INNER JOIN Sucursales s ON v.codigo_sucursal = s.codigo " +
                "LEFT JOIN Boletos b ON v.codigo = b.codigo_venta " +
                "GROUP BY v.codigo, v.fecha, v.hora, v.precio_total, " +
                "v.codigo_cliente, v.codigo_empleado, v.codigo_sucursal, " +
                "p.nombres, p.apellidos, p2.nombres, p2.apellidos, s.nombre " +
                "ORDER BY v.codigo";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Venta venta = new Venta();
                venta.setCodigo(rs.getInt("codigo"));
                venta.setFecha(rs.getDate("fecha"));
                venta.setHora(rs.getTime("hora"));
                venta.setPrecioTotal(rs.getDouble("precio_total"));

                Cliente cliente = new Cliente();
                cliente.setCodigo(rs.getInt("codigo_cliente"));
                cliente.setNombres(rs.getString("nombres"));
                cliente.setApellidos(rs.getString("apellidos"));
                venta.setCliente(cliente);

                Empleado empleado = new Empleado();
                empleado.setCodigo(rs.getInt("codigo_empleado"));
                empleado.setNombres(rs.getString("empleado_nombres"));
                empleado.setApellidos(rs.getString("empleado_apellidos"));
                venta.setEmpleado(empleado);

                Sucursal sucursal = new Sucursal();
                sucursal.setCodigo(rs.getInt("codigo_sucursal"));
                sucursal.setNombre(rs.getString("sucursal_nombre"));
                venta.setSucursal(sucursal);

                venta.setCantidadBoletos(rs.getInt("cantidad_boletos"));

                ventas.add(venta);
            }
            System.out.println("Cargadas " + ventas.size() + " ventas");
        } catch (SQLException e) {
            System.err.println("Error al cargar ventas: " + e.getMessage());
        }
    }

    public List<Venta> obtenerTodos() {
        if (ventas == null || ventas.isEmpty()) {
            cargar();
        }
        return ventas != null ? new ArrayList<>(ventas) : new ArrayList<>();
    }

    public ObservableList<Venta> consultar() {
        ObservableList<Venta> ventasList = FXCollections.observableArrayList();
        if (ventas != null) {
            ventasList.addAll(ventas);
        }
        return ventasList;
    }

    public boolean guardar(Venta venta) {
        // Consultas preparadas para las inserciones
        String sqlVenta = "INSERT INTO Ventas (fecha, hora, precio_total, codigo_cliente, codigo_empleado, codigo_sucursal) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlBoleto = "INSERT INTO Boletos (precio_aplicado, codigo_venta, codigo_funcion, codigo_asiento) VALUES (?, ?, ?, ?)";

        try {
            // 1. Apagamos el auto-commit para iniciar la transacción
            conexion.setAutoCommit(false);

            // 2. Insertamos el registro principal de la Venta
            try (PreparedStatement psVenta = conexion.prepareStatement(sqlVenta, java.sql.Statement.RETURN_GENERATED_KEYS)) {

                psVenta.setDate(1, new java.sql.Date(venta.getFecha().getTime()));
                psVenta.setTime(2, venta.getHora());
                psVenta.setDouble(3, venta.getPrecioTotal());

                // Asignación segura de llaves foráneas (manejando posibles nulos)
                if (venta.getCliente() != null) psVenta.setInt(4, venta.getCliente().getCodigo());
                else psVenta.setNull(4, java.sql.Types.INTEGER);

                if (venta.getEmpleado() != null) psVenta.setInt(5, venta.getEmpleado().getCodigo());
                else psVenta.setNull(5, java.sql.Types.INTEGER);

                if (venta.getSucursal() != null) psVenta.setInt(6, venta.getSucursal().getCodigo());
                else psVenta.setNull(6, java.sql.Types.INTEGER);

                psVenta.executeUpdate();

                // Obtenemos el ID autogenerado por MariaDB para usarlo en los boletos
                try (ResultSet rs = psVenta.getGeneratedKeys()) {
                    if (rs.next()) {
                        int codigoVentaGenerado = rs.getInt(1);
                        venta.setCodigo(codigoVentaGenerado);

                        // 3. Insertamos cada Boleto asociado usando un Batch para optimizar el rendimiento
                        if (venta.getBoletos() != null && !venta.getBoletos().isEmpty()) {
                            try (PreparedStatement psBoleto = conexion.prepareStatement(sqlBoleto)) {
                                for (proyecto.com.proyectobasesdedatos.modelos.Boleto b : venta.getBoletos()) {
                                    psBoleto.setDouble(1, b.getPrecioAplicado());
                                    psBoleto.setInt(2, codigoVentaGenerado);
                                    psBoleto.setInt(3, b.getFuncion().getCodigo());
                                    psBoleto.setInt(4, b.getAsiento().getCodigo());
                                    psBoleto.addBatch();
                                }
                                psBoleto.executeBatch();
                            }
                        }
                    } else {
                        throw new SQLException("No se generó el ID para la venta.");
                    }
                }
            }

            // 4. Confirmamos la transacción (Commit)
            conexion.commit();

            // Actualizamos la lista local en memoria si está instanciada
            if (ventas != null) ventas.add(venta);
            return true;

        } catch (SQLException e) {
            System.err.println("Error en la transacción SQL: " + e.getMessage());
            // 5. Deshacemos todo si hay un error (Rollback)
            try {
                if (conexion != null) conexion.rollback();
            } catch (SQLException ex) {
                System.err.println("Error ejecutando rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            // Restauramos el comportamiento por defecto de la conexión
            try {
                if (conexion != null) conexion.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error restaurando auto-commit: " + e.getMessage());
            }
        }
    }

    public Venta obtenerPorCodigo(int codigoVenta) {
        if (ventas == null || ventas.isEmpty()) {
            cargar();
        }
        if (ventas != null) {
            return ventas.stream()
                    .filter(v -> v.getCodigo() == codigoVenta)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}