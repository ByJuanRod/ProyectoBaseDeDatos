package proyecto.com.proyectobasesdedatos.utilidades;

import javafx.scene.layout.AnchorPane;
import proyecto.com.proyectobasesdedatos.controladores.Controlador;

public record Componente(AnchorPane visual, Controlador controlador) { }
