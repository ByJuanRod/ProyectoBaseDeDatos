package proyecto.com.proyectobasesdedatos.utilidades;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class ConfiguradorSpinners {
    public static void configurarSpinnerNumerico(Spinner<Integer> spinner, int minimo, int maximo, int inicial){
        spinner.setEditable(true);
        SpinnerValueFactory.IntegerSpinnerValueFactory factory =  new SpinnerValueFactory.IntegerSpinnerValueFactory(minimo,maximo ,inicial);
        spinner.setValueFactory(factory);
    }

    public static void configurarSpinnerDecimal(Spinner<Double> spinner, int minimo, int maximo, int inicial){
        spinner.setEditable(true);
        SpinnerValueFactory.DoubleSpinnerValueFactory factory =  new SpinnerValueFactory.DoubleSpinnerValueFactory(minimo,maximo ,inicial);
        spinner.setValueFactory(factory);
    }
}
