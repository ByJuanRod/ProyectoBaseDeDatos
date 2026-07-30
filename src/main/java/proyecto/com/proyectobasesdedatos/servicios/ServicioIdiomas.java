package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Idioma;

public class ServicioIdiomas extends Servicio<Idioma> {
    private final static List<Idioma> idiomas = new ArrayList<>();

    @Override
    public void cargar() {
        String sql = "SELECT * FROM Idiomas ORDER BY codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Idioma idioma = new Idioma();
                idioma.setCodigo(rs.getInt("codigo"));
                idioma.setNombre(rs.getString("nombre"));
                idiomas.add(idioma);
            }

            System.out.println("Cargados " + idiomas.size() + " idiomas");

        } catch (SQLException e) {
            System.err.println("Error al cargar idiomas: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Idioma> obtenerTodos() {
        return new ArrayList<>(idiomas);
    }

    @Override
    public Idioma obtenerPorCodigo(int codigo) {
        return idiomas.stream()
                .filter(i -> i.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }
}