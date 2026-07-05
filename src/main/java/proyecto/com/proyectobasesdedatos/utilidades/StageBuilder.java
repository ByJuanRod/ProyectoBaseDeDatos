package proyecto.com.proyectobasesdedatos.utilidades;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyecto.com.proyectobasesdedatos.modelos.Builder;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.AlertFactory;
import proyecto.com.proyectobasesdedatos.utilidades.alertas.TipoAlerta;

import java.awt.*;

public class StageBuilder implements Builder<Stage> {
    private final Stage stage;
    private Componente componente;

    public StageBuilder() {
        stage = new Stage();
        stage.setResizable(false);
        stage.getIcons().add(RecursosVisuales.getIcono());
    }

    public StageBuilder setTitulo(String titulo){
        stage.setTitle(titulo);
        return this;
    }

    public StageBuilder setSize(Dimension size){
        stage.setMinHeight(size.getHeight());
        stage.setMinWidth(size.getWidth());
        return this;
    }

    public StageBuilder setModalidad(Modality modalidad){
        stage.initModality(modalidad);
        return this;
    }

    public StageBuilder setContenido(String nombreRecurso){
        CargadorFXML cargador = new  CargadorFXML();
        componente = cargador.cargarComponenteConControlador(nombreRecurso);

        try{
            Scene contenido = new Scene(componente.visual());
            stage.setScene(contenido);
        }
        catch(Exception e){
            AlertFactory.obtenerAlerta(TipoAlerta.ERROR).crearAlerta("El contenido de este apartado no esta disponible en este momento.").show();
        }
        return this;
    }

    @Override
    public Stage construir(){
        return stage;
    }

    public Pantalla construirPantalla(){
        return new Pantalla(stage,componente);
    }
}