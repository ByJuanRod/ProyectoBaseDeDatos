module proyecto.com.proyectobasesdedatos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    opens proyecto.com.proyectobasesdedatos to javafx.fxml;
    exports proyecto.com.proyectobasesdedatos;
    exports proyecto.com.proyectobasesdedatos.controladores;
    opens proyecto.com.proyectobasesdedatos.controladores to javafx.fxml;
}