package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import proyecto.com.proyectobasesdedatos.datos.ConexionBD;

public abstract class Servicio<T> {

    protected Connection conexion;

    public Servicio() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la base de datos", e);
        }
    }

    public abstract void cargar();
    public abstract List<T> obtenerTodos();
    public abstract T obtenerPorCodigo(int codigo);

    protected void cerrarRecursos(ResultSet rs, PreparedStatement ps) {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}