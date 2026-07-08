package proyecto.com.proyectobasesdedatos.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import proyecto.com.proyectobasesdedatos.controladores.componentes.InformeCompController;
import proyecto.com.proyectobasesdedatos.utilidades.CargadorFXML;
import proyecto.com.proyectobasesdedatos.utilidades.Componente;
import proyecto.com.proyectobasesdedatos.utilidades.Informe;

public class InformesController {

    @FXML
    public VBox pnlContenedor;

    @FXML
    public ScrollPane srpContenedor;

    @FXML
    public void initialize(){
        srpContenedor.fitToWidthProperty().set(true);
        agregarOpcionesInformes();
    }
    private void agregarOpcionesInformes(){
        int num = 0;
        for(Informe inf : Informe.values()){
            Componente comp = new CargadorFXML().cargarComponenteConControlador("componentes/informe-comp.fxml");
            InformeCompController controlador = (InformeCompController) comp.controlador();
            controlador.setInforme(inf);

            if(num % 2 == 1){
                controlador.setEstilo();
            }
            pnlContenedor.getChildren().add(comp.visual());
            num++;
        }
    }


}
