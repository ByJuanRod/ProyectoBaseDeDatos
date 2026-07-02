package proyecto.com.proyectobasesdedatos.datos;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class ConexionBD {

    private static final String ARCHIVO_PROPIEDADES = "/db.properties";
    private static Properties propiedades;

    private ConexionBD() {}

    private static synchronized Properties cargarPropiedades() {
        if (propiedades == null) {
            propiedades = new Properties();
            try (InputStream in = ConexionBD.class.getResourceAsStream(ARCHIVO_PROPIEDADES)) {
                if (in == null) {
                    throw new IllegalStateException(
                            "No se encontró el archivo de configuración " + ARCHIVO_PROPIEDADES +
                                    " en el classpath.");
                }
                propiedades.load(in);
                Class.forName(propiedades.getProperty("db.driver"));
            } catch (IOException e) {
                throw new IllegalStateException("Error leyendo db.properties", e);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("No se encontró el driver de MariaDB en el classpath. " +
                        "Agrega la dependencia mariadb-java-client.", e);
            }
        }
        return propiedades;
    }

    public static Connection obtenerConexion() throws SQLException {
        Properties props = cargarPropiedades();
        return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.usuario"),
                props.getProperty("db.password"));
    }
}
