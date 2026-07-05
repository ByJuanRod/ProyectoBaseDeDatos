package proyecto.com.proyectobasesdedatos.utilidades;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;

import java.util.Objects;

public class CargadorFXML {
    public CargadorFXML() {}

    public AnchorPane cargarComponente(String archivo){
        try{
            return FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/proyecto/com/proyectobasesdedatos/" + archivo)));
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Componente cargarComponenteConControlador(String archivo){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/proyecto/com/proyectobasesdedatos/" + archivo));
            AnchorPane componente = fxmlLoader.load();
            Controlador controlador = fxmlLoader.getController();
            return new Componente(componente, controlador);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}