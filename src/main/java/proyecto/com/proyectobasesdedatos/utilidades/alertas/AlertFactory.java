package proyecto.com.proyectobasesdedatos.utilidades.alertas;

public class AlertFactory {
    public static Alerta obtenerAlerta(TipoAlerta tipoAlerta){
        return switch (tipoAlerta){
            case INFORMACION -> new AlertaInformacion();
            case ADVERTENCIA -> new AlertaAdvertencia();
            case ERROR -> new AlertaError();
            case PREGUNTA ->  new AlertaPregunta();
        };
    }
}
