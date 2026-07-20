package proyecto.com.proyectobasesdedatos.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import proyecto.com.proyectobasesdedatos.utilidades.CargadorFXML;
import proyecto.com.proyectobasesdedatos.utilidades.Vistas;

import java.util.Hashtable;

public class MenuPrincipalController{
    @FXML
    public Button btnGeneral, btnClientes, btnPeliculas, btnSalas, btnVentas, btnFunciones, btnSucursales, btnArtistas;

    @FXML
    public BorderPane pnlContenedor;

    public final Hashtable<Vistas,Button> menu = new Hashtable<>();

    private Vistas seleccionado = Vistas.GENERAL;

    @FXML
    public void initialize(){
        menu.put(Vistas.CLIENTES,btnClientes);
        menu.put(Vistas.PELICULAS,btnPeliculas);
        menu.put(Vistas.SALAS,btnSalas);
        menu.put(Vistas.VENTAS,btnVentas);
        menu.put(Vistas.FUNCIONES,btnFunciones);
        menu.put(Vistas.GENERAL,btnGeneral);
        menu.put(Vistas.SUCURSALES,btnSucursales);
        menu.put(Vistas.ARTISTAS,btnArtistas);
        btnGeneralClick();
    }

    public void btnGeneralClick(){
        cambiarOpcion(Vistas.GENERAL);
    }

    public void btnClientesClick(){
        cambiarOpcion(Vistas.CLIENTES);
    }

    public void btnPeliculasClick(){
        cambiarOpcion(Vistas.PELICULAS);
    }

    public void btnSalasClick(){
        cambiarOpcion(Vistas.SALAS);
    }

    public void btnVentasClick(){
        cambiarOpcion(Vistas.VENTAS);
    }

    public void btnFuncionesClick(){
        cambiarOpcion(Vistas.FUNCIONES);
    }

    public void btnSucursalesClick(){
        cambiarOpcion(Vistas.SUCURSALES);
    }

    public void btnArtistasClick(){
        cambiarOpcion(Vistas.ARTISTAS);
    }

    private void cambiarOpcion(Vistas opcion){
        menu.get(seleccionado).setStyle("-fx-background-color:  #111F35");
        menu.get(opcion).setStyle("-fx-background-color:  #D62828");
        seleccionado = opcion;
        pnlContenedor.getChildren().clear();
        pnlContenedor.setCenter(new CargadorFXML().cargarComponente(seleccionado.getArchivoVinculado()));
    }


}
