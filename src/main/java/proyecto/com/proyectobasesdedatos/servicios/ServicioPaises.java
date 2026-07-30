package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Pais;

public class ServicioPaises extends Servicio<Pais> {
    private static final List<Pais> paises = new ArrayList<>();

    @Override
    public void cargar() {
        String sql = "SELECT * FROM Paises ORDER BY codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Pais pais = new Pais();
                pais.setCodigo(rs.getInt("codigo"));
                pais.setNombre(rs.getString("nombre"));
                paises.add(pais);
            }

            System.out.println("Cargados " + paises.size() + " países");

        } catch (SQLException e) {
            System.err.println("Error al cargar países: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Pais> obtenerTodos() {
        return new ArrayList<>(paises);
    }

    @Override
    public Pais obtenerPorCodigo(int codigo) {
        return paises.stream()
                .filter(p -> p.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }
}