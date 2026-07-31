package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Ciudad;
import proyecto.com.proyectobasesdedatos.modelos.Municipio;
import proyecto.com.proyectobasesdedatos.modelos.Sector;
import proyecto.com.proyectobasesdedatos.modelos.Sucursal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioSucursales {
    private static ServicioSucursales instancia;
    private final Connection conexion;
    private List<Sucursal> sucursales;

    private ServicioSucursales() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la base de datos", e);
        }
    }

    public static synchronized ServicioSucursales getInstance() {
        if (instancia == null) {
            instancia = new ServicioSucursales();
        }
        return instancia;
    }

    public void cargar() {
        if (sucursales != null && !sucursales.isEmpty()) {
            return;
        }

        sucursales = new ArrayList<>();
        String sql = "SELECT s.*, sec.nombre_sector, m.nombre_municipio, c.nombre as nombre_ciudad " +
                "FROM Sucursales s " +
                "INNER JOIN Sectores sec ON s.id_sector = sec.id_sector " +
                "INNER JOIN Municipios m ON sec.id_municipio = m.id_municipio " +
                "INNER JOIN Ciudades c ON m.id_ciudad = c.codigo " +
                "ORDER BY s.codigo";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Sucursal sucursal = new Sucursal();
                sucursal.setCodigo(rs.getInt("codigo"));
                sucursal.setNombre(rs.getString("nombre"));
                sucursal.setCalle(rs.getString("calle"));
                sucursal.setNumero(rs.getString("numero"));
                sucursal.setTelefono(rs.getString("telefono"));
                sucursal.setCorreo(rs.getString("correo"));

                Ciudad ciudad = new Ciudad();
                ciudad.setNombre(rs.getString("nombre_ciudad"));

                Municipio municipio = new Municipio();
                municipio.setNombreMunicipio(rs.getString("nombre_municipio"));
                municipio.setCiudad(ciudad);

                Sector sector = new Sector();
                sector.setIdSector(rs.getInt("id_sector"));
                sector.setNombreSector(rs.getString("nombre_sector"));
                sector.setMunicipio(municipio);

                sucursal.setSector(sector);

                sucursales.add(sucursal);
            }
            System.out.println("Cargadas " + sucursales.size() + " sucursales");
        } catch (SQLException e) {
            System.err.println("Error al cargar sucursales: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Sucursal> obtenerTodos() {
        if (sucursales == null || sucursales.isEmpty()) {
            cargar();
        }
        return sucursales != null ? new ArrayList<>(sucursales) : new ArrayList<>();
    }

    public ObservableList<Sucursal> consultar() {
        ObservableList<Sucursal> sucursalesList = FXCollections.observableArrayList();
        if (sucursales != null) {
            sucursalesList.addAll(sucursales);
        }
        return sucursalesList;
    }

    public Sucursal obtenerPorCodigo(int codigoSucursal) {
        if (sucursales == null || sucursales.isEmpty()) {
            cargar();
        }
        if (sucursales != null) {
            return sucursales.stream()
                    .filter(s -> s.getCodigo() == codigoSucursal)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}