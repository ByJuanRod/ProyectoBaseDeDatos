package proyecto.com.proyectobasesdedatos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class MenuPrincipal extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuPrincipal.class.getResource("menu-principal.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), Toolkit.getDefaultToolkit().getScreenSize().width - 10, Toolkit.getDefaultToolkit().getScreenSize().height- 80);
        stage.setTitle("Absolute Cinema");
        stage.setScene(scene);
        stage.show();
    }
}
