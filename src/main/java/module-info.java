module proyecto.com.proyectobasesdedatos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;
    requires javafx.graphics;

    opens proyecto.com.proyectobasesdedatos to javafx.fxml;
    exports proyecto.com.proyectobasesdedatos;
    exports proyecto.com.proyectobasesdedatos.controladores;
    exports proyecto.com.proyectobasesdedatos.utilidades;
    exports proyecto.com.proyectobasesdedatos.modelos;
    opens proyecto.com.proyectobasesdedatos.controladores to javafx.fxml;
    exports proyecto.com.proyectobasesdedatos.controladores.formularios;
    opens proyecto.com.proyectobasesdedatos.controladores.formularios to javafx.fxml;
    exports proyecto.com.proyectobasesdedatos.controladores.vistas;
    opens proyecto.com.proyectobasesdedatos.controladores.vistas to javafx.fxml;
    exports proyecto.com.proyectobasesdedatos.controladores.componentes;
    opens proyecto.com.proyectobasesdedatos.controladores.componentes to javafx.fxml;
}