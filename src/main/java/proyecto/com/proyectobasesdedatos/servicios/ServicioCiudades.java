package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Ciudad;
import proyecto.com.proyectobasesdedatos.modelos.Pais;

public class ServicioCiudades extends Servicio<Ciudad> {
    private static final List<Ciudad> ciudades = new ArrayList<>();
    private final ServicioPaises servicioPaises;

    public ServicioCiudades() {
        super();
        servicioPaises = new ServicioPaises();
    }

    @Override
    public void cargar() {
        String sql = "SELECT * FROM Ciudades ORDER BY codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Ciudad ciudad = new Ciudad();
                ciudad.setCodigo(rs.getInt("codigo"));
                ciudad.setNombre(rs.getString("nombre"));
                ciudad.setCodigoPostal(rs.getInt("codigo_postal"));

                int codigoPais = rs.getInt("codigo_pais");
                Pais pais = servicioPaises.obtenerPorCodigo(codigoPais);
                ciudad.setPais(pais);

                ciudades.add(ciudad);
            }

            System.out.println("Cargados " + ciudades.size() + " ciudades");

        } catch (SQLException e) {
            System.err.println("Error al cargar ciudades: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Ciudad> obtenerTodos() {
        return new ArrayList<>(ciudades);
    }

    @Override
    public Ciudad obtenerPorCodigo(int codigo) {
        return ciudades.stream()
                .filter(c -> c.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }

    public List<Ciudad> obtenerPorPais(int codigoPais) {
        return ciudades.stream()
                .filter(c -> c.getPais().getCodigo() == codigoPais)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}