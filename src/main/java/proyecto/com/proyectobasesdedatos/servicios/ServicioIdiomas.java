package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Idioma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioIdiomas {
    private static ServicioIdiomas instancia;
    private final Connection conexion;
    private List<Idioma> idiomas;

    private ServicioIdiomas() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la base de datos", e);
        }
    }

    public static synchronized ServicioIdiomas getInstance() {
        if (instancia == null) {
            instancia = new ServicioIdiomas();
        }
        return instancia;
    }

    public void cargar() {
        if (idiomas != null && !idiomas.isEmpty()) {
            return;
        }

        idiomas = new ArrayList<>();
        String sql = "SELECT * FROM Idiomas ORDER BY codigo";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Idioma idioma = new Idioma();
                idioma.setCodigo(rs.getInt("codigo"));
                idioma.setNombre(rs.getString("nombre"));
                idiomas.add(idioma);
            }
            System.out.println("Cargados " + idiomas.size() + " idiomas");
        } catch (SQLException e) {
            System.err.println("Error al cargar idiomas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Idioma> obtenerTodos() {
        if (idiomas == null || idiomas.isEmpty()) {
            cargar();
        }
        return idiomas != null ? new ArrayList<>(idiomas) : new ArrayList<>();
    }

    public ObservableList<Idioma> consultar() {
        ObservableList<Idioma> idiomasList = FXCollections.observableArrayList();
        if (idiomas != null) {
            idiomasList.addAll(idiomas);
        }
        return idiomasList;
    }

    public Idioma obtenerPorCodigo(int codigoIdioma) {
        if (idiomas == null || idiomas.isEmpty()) {
            cargar();
        }
        if (idiomas != null) {
            return idiomas.stream()
                    .filter(i -> i.getCodigo() == codigoIdioma)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}