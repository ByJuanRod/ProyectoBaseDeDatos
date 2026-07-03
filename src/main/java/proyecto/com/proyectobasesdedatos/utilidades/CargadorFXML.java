package proyecto.com.proyectobasesdedatos.utilidades;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;

import java.util.Objects;

public class CargadorFXML {
    public CargadorFXML() {}

    public AnchorPane cargarComponente(String archivo){
        try{
            String URL = String.valueOf(getClass().getResource("/proyecto/com/proyectobasesdedatos/" + archivo));
            return FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/proyecto/com/proyectobasesdedatos/" + archivo)));

        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Componente cargarComponenteConControlador(String archivo){
        FXMLLoader fxmlLoader = new FXMLLoader();
        try{
            AnchorPane componente = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(archivo)));
            Controlador controlador = fxmlLoader.getController();
            return new Componente(componente, controlador);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}