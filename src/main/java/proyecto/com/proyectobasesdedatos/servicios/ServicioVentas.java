package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.modelos.Empleado;
import proyecto.com.proyectobasesdedatos.modelos.Sucursal;
import proyecto.com.proyectobasesdedatos.modelos.Venta;

public class ServicioVentas extends Servicio<Venta> {
    private final static List<Venta> ventas = new ArrayList<>();
    private final ServicioClientes servicioClientes;
    private final ServicioEmpleados servicioEmpleados;
    private final ServicioSucursales servicioSucursales;

    public ServicioVentas() {
        super();
        servicioClientes = new ServicioClientes();
        servicioEmpleados = new ServicioEmpleados();
        servicioSucursales = new ServicioSucursales();
    }

    @Override
    public void cargar() {
        String sql = "SELECT * FROM Ventas ORDER BY codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Venta venta = new Venta();
                venta.setCodigo(rs.getInt("codigo"));
                venta.setFecha(rs.getDate("fecha"));
                venta.setHora(rs.getTime("hora"));
                venta.setPrecioTotal(rs.getDouble("precio_total"));

                Cliente cliente = servicioClientes.obtenerPorCodigo(rs.getInt("codigo_cliente"));
                venta.setCliente(cliente);

                Empleado empleado = servicioEmpleados.obtenerPorCodigo(rs.getInt("codigo_empleado"));
                venta.setEmpleado(empleado);

                Sucursal sucursal = servicioSucursales.obtenerPorCodigo(rs.getInt("codigo_sucursal"));
                venta.setSucursal(sucursal);

                ventas.add(venta);
            }

            System.out.println("Cargadas " + ventas.size() + " ventas");

        } catch (SQLException e) {
            System.err.println("Error al cargar ventas: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Venta> obtenerTodos() {
        return new ArrayList<>(ventas);
    }

    @Override
    public Venta obtenerPorCodigo(int codigo) {
        return ventas.stream()
                .filter(v -> v.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }
}