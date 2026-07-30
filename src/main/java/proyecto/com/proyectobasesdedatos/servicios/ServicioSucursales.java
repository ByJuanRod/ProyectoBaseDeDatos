package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Sector;
import proyecto.com.proyectobasesdedatos.modelos.Sucursal;

public class ServicioSucursales extends Servicio<Sucursal> {
    private static final List<Sucursal> sucursales = new ArrayList<>();
    private final ServicioSectores servicioSectores;

    public ServicioSucursales() {
        super();
        servicioSectores = new ServicioSectores();
    }

    @Override
    public void cargar() {
        String sql = "SELECT * FROM Sucursales ORDER BY codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Sucursal sucursal = new Sucursal();
                sucursal.setCodigo(rs.getInt("codigo"));
                sucursal.setNombre(rs.getString("nombre"));
                sucursal.setCalle(rs.getString("calle"));
                sucursal.setNumero(rs.getString("numero"));
                sucursal.setTelefono(rs.getString("telefono"));
                sucursal.setCorreo(rs.getString("correo"));

                int idSector = rs.getInt("id_sector");
                Sector sector = servicioSectores.obtenerPorCodigo(idSector);
                sucursal.setSector(sector);

                sucursales.add(sucursal);
            }

            System.out.println("Cargadas " + sucursales.size() + " sucursales");

        } catch (SQLException e) {
            System.err.println("Error al cargar sucursales: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Sucursal> obtenerTodos() {
        return new ArrayList<>(sucursales);
    }

    @Override
    public Sucursal obtenerPorCodigo(int codigo) {
        return sucursales.stream()
                .filter(s -> s.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }
}