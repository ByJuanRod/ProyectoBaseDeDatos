package proyecto.com.proyectobasesdedatos.controladores;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import proyecto.com.proyectobasesdedatos.utilidades.CargadorFXML;
import proyecto.com.proyectobasesdedatos.utilidades.OpcionMenu;

import java.util.Hashtable;

public class MenuPrincipalController{
    @FXML
    public Button btnGeneral, btnClientes, btnPeliculas, btnSalas, btnVentas, btnFunciones, btnSucursales, btnArtistas;

    @FXML
    public BorderPane pnlContenedor;

    public final Hashtable<OpcionMenu,Button> menu = new Hashtable<>();

    private OpcionMenu seleccionado = OpcionMenu.GENERAL;

    @FXML
    public void initialize(){
        menu.put(OpcionMenu.CLIENTES,btnClientes);
        menu.put(OpcionMenu.PELICULAS,btnPeliculas);
        menu.put(OpcionMenu.SALAS,btnSalas);
        menu.put(OpcionMenu.VENTAS,btnVentas);
        menu.put(OpcionMenu.FUNCIONES,btnFunciones);
        menu.put(OpcionMenu.GENERAL,btnGeneral);
        menu.put(OpcionMenu.SUCURSALES,btnSucursales);
        menu.put(OpcionMenu.ARTISTAS,btnArtistas);
        btnGeneralClick();
    }

    public void btnGeneralClick(){
        cambiarOpcion(OpcionMenu.GENERAL);
    }

    public void btnClientesClick(){
        cambiarOpcion(OpcionMenu.CLIENTES);
    }

    public void btnPeliculasClick(){
        cambiarOpcion(OpcionMenu.PELICULAS);
    }

    public void btnSalasClick(){
        cambiarOpcion(OpcionMenu.SALAS);
    }

    public void btnVentasClick(){
        cambiarOpcion(OpcionMenu.VENTAS);
    }

    public void btnFuncionesClick(){
        cambiarOpcion(OpcionMenu.FUNCIONES);
    }

    public void btnSucursalesClick(){
        cambiarOpcion(OpcionMenu.SUCURSALES);
    }

    public void btnArtistasClick(){
        cambiarOpcion(OpcionMenu.ARTISTAS);
    }

    private void cambiarOpcion(OpcionMenu opcion){
        menu.get(seleccionado).setStyle("-fx-background-color:  #111F35");
        menu.get(opcion).setStyle("-fx-background-color:  #D62828");
        seleccionado = opcion;
        pnlContenedor.getChildren().clear();
        pnlContenedor.setCenter(new CargadorFXML().cargarComponente(seleccionado.getArchivoVinculado()));
    }


}
