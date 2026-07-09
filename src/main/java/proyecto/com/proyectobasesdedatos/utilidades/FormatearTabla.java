package proyecto.com.proyectobasesdedatos.utilidades;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FormatearTabla {
    public static void ajustarAnchoColumnas(TableView<?> tabla) {
        double anchoTabla = tabla.getWidth();
        if (anchoTabla > 0) {
            int numColumnas = tabla.getColumns().size();
            double anchoPorColumna;
            anchoPorColumna = (anchoTabla / numColumnas) - cantidadDecrecer(numColumnas);

            for (TableColumn<?, ?> columna : tabla.getColumns()) {
                columna.setPrefWidth(anchoPorColumna);
            }
        }

    }
    private static int cantidadDecrecer(int cantCols){
        switch (cantCols){
            case 5: return 2;

        }
        return 0;
    }
}
