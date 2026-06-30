module proyecto.com.proyectobasesdedatos {
    requires javafx.controls;
    requires javafx.fxml;


    opens proyecto.com.proyectobasesdedatos to javafx.fxml;
    exports proyecto.com.proyectobasesdedatos;
}