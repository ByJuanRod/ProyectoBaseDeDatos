package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Genero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioGeneros {
    private static ServicioGeneros instancia;
    private final Connection conexion;
    private List<Genero> generos;

    private ServicioGeneros() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la base de datos", e);
        }
    }

    public static synchronized ServicioGeneros getInstance() {
        if (instancia == null) {
            instancia = new ServicioGeneros();
        }
        return instancia;
    }

    public void cargar() {
        if (generos != null && !generos.isEmpty()) {
            return;
        }

        generos = new ArrayList<>();
        String sql = "SELECT * FROM Generos";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Genero genero = new Genero();
                genero.setCodigo(rs.getInt("codigo"));
                genero.setNombre(rs.getString("nombre"));
                generos.add(genero);
            }
            System.out.println("Cargados " + generos.size() + " géneros");
        } catch (SQLException e) {
            System.err.println("Error al cargar géneros: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Genero> obtenerTodos() {
        if (generos == null || generos.isEmpty()) {
            cargar();
        }
        return generos != null ? new ArrayList<>(generos) : new ArrayList<>();
    }

    public ObservableList<Genero> consultar() {
        ObservableList<Genero> generosList = FXCollections.observableArrayList();
        if (generos != null) {
            generosList.addAll(generos);
        }
        return generosList;
    }

    public Genero obtenerPorCodigo(int codigoGenero) {
        if (generos == null || generos.isEmpty()) {
            cargar();
        }
        if (generos != null) {
            return generos.stream()
                    .filter(g -> g.getCodigo() == codigoGenero)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}