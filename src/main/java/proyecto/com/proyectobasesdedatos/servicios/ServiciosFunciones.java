package proyecto.com.proyectobasesdedatos.servicios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import proyecto.com.proyectobasesdedatos.datos.ConexionBD;
import proyecto.com.proyectobasesdedatos.modelos.Funcion;
import proyecto.com.proyectobasesdedatos.modelos.Cine;

import java.sql.*;

public class ServiciosFunciones implements Servicio<Funcion>{
    public final ObservableList<Funcion> listaFunciones;

    public ServiciosFunciones(){
        listaFunciones = Cine.getInstance().getListaFunciones();
    }
    @Override
    public ObservableList<Funcion> consultar() {
        return this.listaFunciones;
    }
    @Override
    public boolean crear(Funcion entidad){
        String sql = "INSERT INTO Funciones (codigo_pelicula, codigo_sala, fecha, hora_inicio, hora_fin, precio_entrada) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pst.setInt(1, entidad.getPelicula().getCodigo());
            pst.setInt(2, entidad.getSala().getCodigo());
            pst.setDate(3, java.sql.Date.valueOf(entidad.getFecha()));
            pst.setTime(4, java.sql.Time.valueOf(entidad.getHoraInicio()));
            pst.setTime(5, java.sql.Time.valueOf(entidad.getHoraFinal()));
            pst.setFloat(6, entidad.getPrecioEntrada());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entidad.setCodigo(generatedKeys.getInt(1));
                        this.listaFunciones.add(entidad);
                    }
                }
            }

            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    @Override
    public Funcion buscar(int codigo){
        for(Funcion funcion: listaFunciones){
            if(funcion.getCodigo() == codigo){
                return funcion;
            }
        }
        return null;
    }
    @Override
    public boolean actualizar(Funcion entidad){
        String sql = "UPDATE Clientes SET codigo_pelicula = ?, codigo_sala = ?, fecha = ?, " +
                "hora_inicio = ?, hora_fin = ?, precio_entrada = ?" + " WHERE codigo = ?";

        try (Connection con = ConexionBD.obtenerConexion();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, entidad.getPelicula().getCodigo());
            pst.setInt(2, entidad.getSala().getCodigo());
            pst.setDate(3, java.sql.Date.valueOf(entidad.getFecha()));
            pst.setTime(4, java.sql.Time.valueOf(entidad.getHoraInicio()));
            pst.setTime(5, java.sql.Time.valueOf(entidad.getHoraFinal()));
            pst.setFloat(6, entidad.getPrecioEntrada());
            pst.setInt(7, entidad.getCodigo());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Funcion funcionVieja = buscar(entidad.getCodigo());
                if(funcionVieja != null){
                    int ind = this.listaFunciones.indexOf(funcionVieja);
                    this.listaFunciones.set(ind, entidad);
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            return false;
        }
    }
    @Override
    public boolean eliminar(Funcion entidad){
        String sqlEliminar = "DELETE FROM Funciones WHERE codigo = ?";
        try(Connection con = ConexionBD.obtenerConexion();
            PreparedStatement pst = con.prepareStatement(sqlEliminar)){

            pst.setInt(1, entidad.getCodigo());
            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                Funcion funcionVieja = buscar(entidad.getCodigo());
                if(funcionVieja != null){
                    this.listaFunciones.remove(funcionVieja);
                }
                return true;
            }
            return false;

        } catch (SQLException e) {
            return false;
        }
    }
}
