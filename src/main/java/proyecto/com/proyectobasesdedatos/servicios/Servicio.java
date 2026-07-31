package proyecto.com.proyectobasesdedatos.servicios;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import proyecto.com.proyectobasesdedatos.datos.ConexionBD;

public abstract class Servicio<T> {
    protected final Connection conexion;

    protected Servicio() {
        try {
            this.conexion = ConexionBD.obtenerConexion();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la base de datos", e);
        }
    }

    public abstract void cargar();
    public abstract List<T> obtenerTodos();
    public abstract T obtenerPorCodigo(int codigo);

    protected void cerrarRecursos(ResultSet rs, Statement stmt) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar recursos: " + e.getMessage());
        }
    }
}