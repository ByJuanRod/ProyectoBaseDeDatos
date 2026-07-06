package proyecto.com.proyectobasesdedatos.utilidades;

import javafx.scene.image.Image;

import java.util.Hashtable;
import java.util.Objects;

public class RecursosVisuales {
    private static final Image icono = new Image(Objects.requireNonNull(RecursosVisuales.class.getResourceAsStream("/proyecto/com/proyectobasesdedatos/imagenes/cine.png")));
    public static Image getIcono(){
        return icono;
    }

    public static Image cargarImagen(String ruta){
        return new Image(Objects.requireNonNull(RecursosVisuales.class.getResourceAsStream("/proyecto/com/proyectobasesdedatos/imagenes/" + ruta)));
    }

    private static final Hashtable<OpcionMenu, Image> opciones = new Hashtable<>();

    public static Image getImagenOpciones(OpcionMenu opcion){
        if(opciones.containsKey(opcion)){
            return opciones.get(opcion);
        }
        else{
            for(OpcionMenu op: OpcionMenu.values()){
                opciones.put(op,cargarImagen(op.getArchivoImagen()));
            }
        }
        return opciones.get(opcion);
    }

}
