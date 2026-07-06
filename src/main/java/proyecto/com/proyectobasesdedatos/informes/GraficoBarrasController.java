package proyecto.com.proyectobasesdedatos.informes;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.servicios.ServicioInformes;
import proyecto.com.proyectobasesdedatos.utilidades.Informe;

public class GraficoBarrasController {
    ServicioInformes si = new ServicioInformes();

    private Stage stg =  new Stage();

    public void setStage(Stage stage) {
        stg = stage;
    }

    @FXML
    public Label lblTitulo;

    @FXML
    public BarChart<String,Number> graficoBarras;

    public void btnCerrarClick(){
        stg.close();
    }

    public void setGrafico(Informe informe){
        graficoBarras.getData().clear();
        if(informe.equals(Informe.BOLETOS_POR_DIA_SEMANA)){
            graficoBarras.getData().add(si.ventasPorDiaSemana());
        }
        graficoBarras.setTitle(informe.getNombreGrafico());
        lblTitulo.setText(informe.getTitulo());

    }

}
