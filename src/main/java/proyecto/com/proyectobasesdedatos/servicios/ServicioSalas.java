package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Sala;
import proyecto.com.proyectobasesdedatos.modelos.Sucursal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioSalas {
    private static ServicioSalas instancia;
    private final Connection conexion;
    private List<Sala> salas;

    private ServicioSalas() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la base de datos", e);
        }
    }

    public static synchronized ServicioSalas getInstance() {
        if (instancia == null) {
            instancia = new ServicioSalas();
        }
        return instancia;
    }

    public void cargar() {
        if (salas != null && !salas.isEmpty()) {
            return;
        }

        salas = new ArrayList<>();
        String sql = "SELECT s.*, su.nombre as nombre_sucursal " +
                "FROM Salas s " +
                "INNER JOIN Sucursales su ON s.codigo_sucursal = su.codigo " +
                "ORDER BY s.codigo";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Sala sala = new Sala();
                sala.setCodigo(rs.getInt("codigo"));
                sala.setNombre(rs.getString("nombre"));
                sala.setCapacidad(rs.getInt("capacidad"));

                Sucursal sucursal = new Sucursal();
                sucursal.setCodigo(rs.getInt("codigo_sucursal"));
                sucursal.setNombre(rs.getString("nombre_sucursal"));
                sala.setSucursal(sucursal);

                salas.add(sala);
            }
            System.out.println("Cargadas " + salas.size() + " salas");
        } catch (SQLException e) {
            System.err.println("Error al cargar salas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Sala> obtenerTodos() {
        if (salas == null || salas.isEmpty()) {
            cargar();
        }
        return salas != null ? new ArrayList<>(salas) : new ArrayList<>();
    }

    public ObservableList<Sala> consultar() {
        ObservableList<Sala> salasList = FXCollections.observableArrayList();
        if (salas != null) {
            salasList.addAll(salas);
        }
        return salasList;
    }

    public Sala consultarPorCodigo(int codigo) {
        if (salas == null || salas.isEmpty()) {
            cargar();
        }
        if (salas != null) {
            return salas.stream()
                    .filter(s -> s.getCodigo() == codigo)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public Sala obtenerPorCodigo(int codigo) {
        if (salas == null || salas.isEmpty()) {
            cargar();
        }
        if (salas != null) {
            return salas.stream()
                    .filter(s -> s.getCodigo() == codigo)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public boolean eliminar(Sala sala) {
        return false;
    }

    public boolean actualizar(Sala sala) {
        return false;
    }

    public boolean guardar(Sala sala) {
        return false;
    }
}