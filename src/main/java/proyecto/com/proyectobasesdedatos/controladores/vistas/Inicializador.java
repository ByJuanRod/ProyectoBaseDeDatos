package proyecto.com.proyectobasesdedatos.controladores.vistas;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import proyecto.com.proyectobasesdedatos.controladores.Vista;
import proyecto.com.proyectobasesdedatos.utilidades.FormatearTabla;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

public class Inicializador {
    public static void inicializar(Vista<?> vista, TableView<?> tablaDatos, TextField filtro){
        try{
            vista.configurarColumnas();
            vista.cargar();
            vista.setPlaceholder();
            tablaDatos.setPlaceholder(vista.setPlaceholder());
            filtro.textProperty().addListener((observable, oldValue, newValue) -> vista.filtrar());
            tablaDatos.widthProperty().addListener((obs, oldWidth, newWidth) -> FormatearTabla.ajustarAnchoColumnas(tablaDatos));
        }
        catch(Exception e){
            AlertFactory.obtenerAlerta(TipoAlerta.ERROR).crearAlerta("No se ha podido iniciar el apartado").show();
            e.printStackTrace();
        }
    }
}
