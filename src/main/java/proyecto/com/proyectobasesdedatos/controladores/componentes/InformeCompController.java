package proyecto.com.proyectobasesdedatos.controladores.componentes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;
import proyecto.com.proyectobasesdedatos.utilidades.Informe;
import proyecto.com.proyectobasesdedatos.utilidades.RecursosVisuales;

public class InformeCompController implements Controlador {

    private Informe informe;

    public void setEstilo(){
        pnlInf.setStyle("-fx-background-color: #1A2A4F; -fx-background-radius: 10px");
    }

    public void setInforme(Informe inf){
        informe=inf;
        lblNombreInforme.setText(inf.getTitulo());
        lblDescripcion.setText(inf.getDescripcion());
        imgInforme.setImage(RecursosVisuales.cargarImagen(inf.getImagen()));
    }

    @FXML
    public Label lblNombreInforme,lblDescripcion;

    public AnchorPane pnlInf;

    @FXML
    public ImageView imgInforme;

    public void btnInformeClick(){

    }

}
