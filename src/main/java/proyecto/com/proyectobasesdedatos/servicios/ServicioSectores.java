package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.modelos.Municipio;
import proyecto.com.proyectobasesdedatos.modelos.Sector;

public class ServicioSectores extends Servicio<Sector> {
    private static ServicioSectores instancia;
    private static final List<Sector> sectores = new ArrayList<>();
    private final ServicioMunicipios servicioMunicipios;

    private ServicioSectores() {
        super();
        servicioMunicipios = ServicioMunicipios.getInstance();
    }

    public static synchronized ServicioSectores getInstance() {
        if (instancia == null) {
            instancia = new ServicioSectores();
        }
        return instancia;
    }

    @Override
    public void cargar() {
        if (!sectores.isEmpty()) {
            return;
        }

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
        if (sectores.isEmpty()) {
            cargar();
        }
        return new ArrayList<>(sectores);
    }

    @Override
    public Sector obtenerPorCodigo(int codigo) {
        if (sectores.isEmpty()) {
            cargar();
        }
        return sectores.stream()
                .filter(s -> s.getIdSector() == codigo)
                .findFirst()
                .orElse(null);
    }



    public ObservableList<Sector> consultar() {
        ObservableList<Sector> sectorList = FXCollections.observableArrayList();
        if (sectores != null) {
            sectorList.addAll(sectores);
        }
        return sectorList;
    }
}