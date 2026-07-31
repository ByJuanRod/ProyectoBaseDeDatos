package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Cliente;
import proyecto.com.proyectobasesdedatos.modelos.Sector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioClientes {
    private static ServicioClientes instancia;
    private final Connection conexion;
    private List<Cliente> clientes;

    private ServicioClientes() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la base de datos", e);
        }
    }

    public static synchronized ServicioClientes getInstance() {
        if (instancia == null) {
            instancia = new ServicioClientes();
        }
        return instancia;
    }

    public void cargar() {
        if (clientes != null && !clientes.isEmpty()) {
            return;
        }

        clientes = new ArrayList<>();
        String sql = "SELECT c.*, p.nombres, p.apellidos, p.fecha_nacimiento, p.sexo, " +
                "p.telefono, p.correo, p.id_sector_residencia " +
                "FROM Clientes c " +
                "INNER JOIN Personas p ON c.codigo = p.codigo " +
                "ORDER BY c.codigo";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setCodigo(rs.getInt("codigo"));
                cliente.setNombres(rs.getString("nombres"));
                cliente.setApellidos(rs.getString("apellidos"));
                cliente.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                cliente.setSexo(rs.getString("sexo").charAt(0));
                cliente.setTelefono(rs.getString("telefono"));
                cliente.setCorreo(rs.getString("correo"));
                cliente.setCantidadEntradas(rs.getInt("cantidad_entradas"));
                cliente.setFechaRegistro(rs.getDate("fecha_registro"));

                int idSector = rs.getInt("id_sector_residencia");
                if (!rs.wasNull()) {
                    Sector sector = cargarSector(idSector);
                    cliente.setSectorResidencia(sector);
                }

                clientes.add(cliente);
            }
            System.out.println("Cargados " + clientes.size() + " clientes");
        } catch (SQLException e) {
            System.err.println("Error al cargar clientes: " + e.getMessage());
        }
    }

    private Sector cargarSector(int idSector) {
        String sql = "SELECT * FROM Sectores WHERE id_sector = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, idSector);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Sector sector = new Sector();
                    sector.setIdSector(rs.getInt("id_sector"));
                    sector.setNombreSector(rs.getString("nombre_sector"));
                    return sector;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar sector: " + e.getMessage());
        }
        return null;
    }

    public List<Cliente> obtenerTodos() {
        if (clientes == null || clientes.isEmpty()) {
            cargar();
        }
        return clientes != null ? new ArrayList<>(clientes) : new ArrayList<>();
    }

    public ObservableList<Cliente> consultar() {
        ObservableList<Cliente> clientesList = FXCollections.observableArrayList();
        if (clientes != null) {
            clientesList.addAll(clientes);
        }
        return clientesList;
    }

    public Cliente consultarPorCodigo(int codigo) {
        if (clientes == null || clientes.isEmpty()) {
            cargar();
        }
        if (clientes != null) {
            return clientes.stream()
                    .filter(c -> c.getCodigo() == codigo)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public boolean guardar(Cliente cliente) {
        return false;
    }
}