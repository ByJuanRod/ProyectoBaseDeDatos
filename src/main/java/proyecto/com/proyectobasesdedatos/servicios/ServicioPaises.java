package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Pais;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioPaises {
    private static ServicioPaises instancia;
    private final Connection conexion;
    private List<Pais> paises;

    private ServicioPaises() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la base de datos", e);
        }
    }

    public static synchronized ServicioPaises getInstance() {
        if (instancia == null) {
            instancia = new ServicioPaises();
        }
        return instancia;
    }

    public void cargar() {
        if (paises != null && !paises.isEmpty()) {
            return;
        }

        paises = new ArrayList<>();
        String sql = "SELECT * FROM Paises ORDER BY codigo";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pais pais = new Pais();
                pais.setCodigo(rs.getInt("codigo"));
                pais.setNombre(rs.getString("nombre"));
                paises.add(pais);
            }
            System.out.println("Cargados " + paises.size() + " países");
        } catch (SQLException e) {
            System.err.println("Error al cargar países: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Pais> obtenerTodos() {
        if (paises == null || paises.isEmpty()) {
            cargar();
        }
        return paises != null ? new ArrayList<>(paises) : new ArrayList<>();
    }

    public ObservableList<Pais> consultar() {
        ObservableList<Pais> paisesList = FXCollections.observableArrayList();
        if (paises != null) {
            paisesList.addAll(paises);
        }
        return paisesList;
    }

    public Pais consultarPorCodigo(int codigo) {
        if (paises == null || paises.isEmpty()) {
            cargar();
        }
        if (paises != null) {
            return paises.stream()
                    .filter(p -> p.getCodigo() == codigo)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    public boolean eliminar(Pais pais) {
        return false;
    }

    public boolean actualizar(Pais pais) {
        return false;
    }

    public boolean guardar(Pais pais) {
        return false;
    }
}