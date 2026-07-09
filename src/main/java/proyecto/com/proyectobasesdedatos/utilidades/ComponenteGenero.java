package proyecto.com.proyectobasesdedatos.utilidades;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.input.MouseEvent;
import proyecto.com.proyectobasesdedatos.modelos.Genero;

public class ComponenteGenero extends AnchorPane {

    private final Label lblNombre = new Label("Ciencia Ficción");

    private final HBox hbox = new HBox();

    private boolean estaSeleccionado = false;

    public ComponenteGenero(Genero genero) {
        setPrefHeight(50.0);
        setPrefWidth(185.0);
        setPadding(new Insets(5.0));
        setCursor(Cursor.HAND);
        setOnMouseClicked(this::toogle);

        hbox.setAlignment(Pos.CENTER);
        hbox.setPrefHeight(48.0);
        hbox.setPrefWidth(166.0);
        hbox.setStyle("-fx-background-color: #111F35; -fx-background-radius: 30px;");

        AnchorPane.setLeftAnchor(hbox, 10.0);
        AnchorPane.setRightAnchor(hbox, 10.0);

        lblNombre.setTextFill(Color.WHITE);
        lblNombre.setFont(Font.font("Century Gothic", FontWeight.BOLD, 16.0));

        hbox.getChildren().add(lblNombre);
        getChildren().add(hbox);
    }

    public void toogle(MouseEvent e){
        if(estaSeleccionado){
            hbox.setStyle("-fx-background-color: #111F35; -fx-background-radius: 30px;");
            estaSeleccionado = false;
        }
        else{
            hbox.setStyle("-fx-background-color: #31694E; -fx-background-radius: 30px;");
            estaSeleccionado = true;
        }
    }
}