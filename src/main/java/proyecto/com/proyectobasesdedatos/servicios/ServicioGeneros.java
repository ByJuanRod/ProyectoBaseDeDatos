package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proyecto.com.proyectobasesdedatos.modelos.Genero;

public class ServicioGeneros extends Servicio<Genero> {
    private static final List<Genero> generos = new ArrayList<>();

    @Override
    public void cargar() {
        String sql = "SELECT * FROM Generos ORDER BY codigo";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Genero genero = new Genero();
                genero.setCodigo(rs.getInt("codigo"));
                genero.setNombre(rs.getString("nombre"));
                generos.add(genero);
            }

            System.out.println("Cargados " + generos.size() + " géneros");

        } catch (SQLException e) {
            System.err.println("Error al cargar géneros: " + e.getMessage());
        } finally {
            cerrarRecursos(rs, ps);
        }
    }

    @Override
    public List<Genero> obtenerTodos() {
        return new ArrayList<>(generos);
    }

    @Override
    public Genero obtenerPorCodigo(int codigo) {
        return generos.stream()
                .filter(g -> g.getCodigo() == codigo)
                .findFirst()
                .orElse(null);
    }
}