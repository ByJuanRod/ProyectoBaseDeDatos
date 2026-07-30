package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Municipio;
import proyecto.com.proyectobasesdedatos.modelos.Sector;

public class ServicioSectores extends Servicio<Sector> {
    private static final List<Sector> sectores = new ArrayList<>();
    private final ServicioMunicipios servicioMunicipios;

    public ServicioSectores() {
        super();
        servicioMunicipios = new ServicioMunicipios();
    }

    @Override
    public void cargar() {
        String sql = "SELECT * FROM Sectores ORDER BY id_sector";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Sector sector = new Sector();
                sector.setIdSector(rs.getInt("id_sector"));
                sector.setNombreSector(rs.getString("nombre_sector"));

                int idMunicipio = rs.getInt("id_municipio");
                Municipio municipio = servicioMunicipios.obtenerPorCodigo(idMunicipio);
                sector.setMunicipio(municipio);

                sectores.add(sector);
            }

            System.out.println("Cargados " + sectores.size() + " sectores");

        } catch (SQLException e) {
            System.err.println("Error al cargar sectores: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Sector> obtenerTodos() {
        return new ArrayList<>(sectores);
    }

    @Override
    public Sector obtenerPorCodigo(int codigo) {
        return sectores.stream()
                .filter(s -> s.getIdSector() == codigo)
                .findFirst()
                .orElse(null);
    }

    public List<Sector> obtenerPorMunicipio(int idMunicipio) {
        return sectores.stream()
                .filter(s -> s.getMunicipio().getIdMunicipio() == idMunicipio)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}