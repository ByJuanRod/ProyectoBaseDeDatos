package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Ciudad;
import proyecto.com.proyectobasesdedatos.modelos.Pais;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioCiudades {
    private static ServicioCiudades instancia;
    private final Connection conexion;
    private List<Ciudad> ciudades;

    private ServicioCiudades() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la base de datos", e);
        }
    }

    public static synchronized ServicioCiudades getInstance() {
        if (instancia == null) {
            instancia = new ServicioCiudades();
        }
        return instancia;
    }

    public void cargar() {
        if (ciudades != null && !ciudades.isEmpty()) {
            return;
        }

        ciudades = new ArrayList<>();
        String sql = "SELECT c.*, p.nombre as nombre_pais FROM Ciudades c " +
                "INNER JOIN Paises p ON c.codigo_pais = p.codigo " +
                "ORDER BY c.codigo";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Ciudad ciudad = new Ciudad();
                ciudad.setCodigo(rs.getInt("codigo"));
                ciudad.setNombre(rs.getString("nombre"));
                ciudad.setCodigoPostal(rs.getString("codigo_postal"));

                Pais pais = new Pais();
                pais.setCodigo(rs.getInt("codigo_pais"));
                pais.setNombre(rs.getString("nombre_pais"));
                ciudad.setPais(pais);

                ciudades.add(ciudad);
            }
            System.out.println("Cargadas " + ciudades.size() + " ciudades");
        } catch (SQLException e) {
            System.err.println("Error al cargar ciudades: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Ciudad> obtenerTodos() {
        if (ciudades == null || ciudades.isEmpty()) {
            cargar();
        }
        return ciudades != null ? new ArrayList<>(ciudades) : new ArrayList<>();
    }

    public ObservableList<Ciudad> consultar() {
        ObservableList<Ciudad> ciudadesList = FXCollections.observableArrayList();
        if (ciudades != null) {
            ciudadesList.addAll(ciudades);
        }
        return ciudadesList;
    }

    public Ciudad obtenerPorCodigo(int idCiudad) {
        if (ciudades == null || ciudades.isEmpty()) {
            cargar();
        }
        if (ciudades != null) {
            return ciudades.stream()
                    .filter(c -> c.getCodigo() == idCiudad)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}