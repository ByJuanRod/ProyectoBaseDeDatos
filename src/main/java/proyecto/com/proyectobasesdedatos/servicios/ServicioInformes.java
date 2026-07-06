package proyecto.com.proyectobasesdedatos.servicios;

import javafx.scene.chart.XYChart;
import proyecto.com.proyectobasesdedatos.modelos.Cine;

public class ServicioInformes {
    public ServicioInformes(){}

    public XYChart.Series<String, Number> ventasPorDiaSemana(){
        XYChart.Series<String, Number> datos = new XYChart.Series<>();

        // Crear el codigo para conocer la cantidad de boletos que se emiten por dias de la semana
        return null;
    }

    public int getCantClientes() {
        return Cine.getInstance().getListaClientes().size();
    }

    public int getCantFunciones() {
        return Cine.getInstance().getListaFunciones().size();
    }

    public int getCantPeliculas() {
        return Cine.getInstance().getListaPeliculas().size();
    }
}
