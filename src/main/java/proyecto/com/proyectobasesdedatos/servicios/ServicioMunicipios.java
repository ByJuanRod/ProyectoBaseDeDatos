package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Ciudad;
import proyecto.com.proyectobasesdedatos.modelos.Municipio;

public class ServicioMunicipios extends Servicio<Municipio> {
    private static final List<Municipio> municipios = new ArrayList<>();
    private final ServicioCiudades servicioCiudades;

    public ServicioMunicipios() {
        super();
        servicioCiudades = new ServicioCiudades();
    }

    @Override
    public void cargar() {
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
        return new ArrayList<>(municipios);
    }

    @Override
    public Municipio obtenerPorCodigo(int codigo) {
        return municipios.stream()
                .filter(m -> m.getIdMunicipio() == codigo)
                .findFirst()
                .orElse(null);
    }

    public List<Municipio> obtenerPorCiudad(int idCiudad) {
        return municipios.stream()
                .filter(m -> m.getCiudad().getCodigo() == idCiudad)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}