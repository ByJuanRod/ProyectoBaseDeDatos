package proyecto.com.proyectobasesdedatos.utilidades;

import javafx.scene.image.Image;

import java.util.Objects;

public class RecursosVisuales {
    private static final Image icono = new Image(Objects.requireNonNull(RecursosVisuales.class.getResourceAsStream("/proyecto/com/proyectobasesdedatos/imagenes/cine.png")));

    public static Image getIcono(){
        return icono;
    }

}
