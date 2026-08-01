package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.modelos.Ciudad;
import proyecto.com.proyectobasesdedatos.modelos.Municipio;

public class ServicioMunicipios extends Servicio<Municipio> {
    private static ServicioMunicipios instancia;
    private static final List<Municipio> municipios = new ArrayList<>();
    private final ServicioCiudades servicioCiudades;

    private ServicioMunicipios() {
        super();
        servicioCiudades = ServicioCiudades.getInstance();
    }

    public static synchronized ServicioMunicipios getInstance() {
        if (instancia == null) {
            instancia = new ServicioMunicipios();
        }
        return instancia;
    }

    @Override
    public void cargar() {
        if (!municipios.isEmpty()) {
            return;
        }

        String sql = "SELECT * FROM Municipios ORDER BY id_municipio";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Municipio municipio = new Municipio();
                municipio.setIdMunicipio(rs.getInt("id_municipio"));
                municipio.setNombreMunicipio(rs.getString("nombre_municipio"));

                int idCiudad = rs.getInt("id_ciudad");
                Ciudad ciudad = servicioCiudades.obtenerPorCodigo(idCiudad);
                municipio.setCiudad(ciudad);

                municipios.add(municipio);
            }

            System.out.println("Cargados " + municipios.size() + " municipios");

        } catch (SQLException e) {
            System.err.println("Error al cargar municipios: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Municipio> obtenerTodos() {
        if (municipios.isEmpty()) {
            cargar();
        }
        return new ArrayList<>(municipios);
    }

    public ObservableList<Municipio> consultar() {
        ObservableList<Municipio> municipioList = FXCollections.observableArrayList();
        if (municipios != null) {
            municipioList.addAll(municipios);
        }
        return municipioList;
    }

    @Override
    public Municipio obtenerPorCodigo(int codigo) {
        if (municipios.isEmpty()) {
            cargar();
        }
        return municipios.stream()
                .filter(m -> m.getIdMunicipio() == codigo)
                .findFirst()
                .orElse(null);
    }

    public List<Municipio> obtenerPorCiudad(int idCiudad) {
        if (municipios.isEmpty()) {
            cargar();
        }
        return municipios.stream()
                .filter(m -> m.getCiudad().getCodigo() == idCiudad)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}