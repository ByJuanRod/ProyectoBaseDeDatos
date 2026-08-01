package proyecto.com.proyectobasesdedatos.servicios;

import proyecto.com.proyectobasesdedatos.modelos.wrappers.BoletoWrapper;

import java.util.ArrayList;
import java.util.List;

public class ServicioBoletosTemporal {
    private static ServicioBoletosTemporal instance;
    private final List<BoletoWrapper> boletosSeleccionados = new ArrayList<>();
    private double total = 0;
    private int ventaIdTemporal = -1; // ID temporal para la venta actual

    private ServicioBoletosTemporal() {}

    public static ServicioBoletosTemporal getInstance() {
        if (instance == null) {
            instance = new ServicioBoletosTemporal();
        }
        return instance;
    }

    /**
     * Verifica si un boleto ya existe en la lista (misma función y mismo asiento)
     */
    private boolean boletoYaExiste(BoletoWrapper nuevoBoleto) {
        for (BoletoWrapper existente : boletosSeleccionados) {
            if (existente.getFuncion() != null && nuevoBoleto.getFuncion() != null) {
                if (existente.getFuncion().getCodigo() == nuevoBoleto.getFuncion().getCodigo()) {
                    if (existente.getAsiento() != null && nuevoBoleto.getAsiento() != null) {
                        if (existente.getAsiento().getCodigo() == nuevoBoleto.getAsiento().getCodigo()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean agregarBoleto(BoletoWrapper boleto) {
        if (boleto == null) {
            return false;
        }

        // Verificar si el boleto ya existe
        if (boletoYaExiste(boleto)) {
            return false; // No se agregó porque ya existe
        }

        boletosSeleccionados.add(boleto);
        actualizarTotal();
        return true;
    }

    public int agregarBoletos(List<BoletoWrapper> boletos) {
        if (boletos == null || boletos.isEmpty()) {
            return 0;
        }

        int agregados = 0;
        for (BoletoWrapper boleto : boletos) {
            if (agregarBoleto(boleto)) {
                agregados++;
            }
        }
        return agregados;
    }

    public void eliminarBoleto(BoletoWrapper boleto) {
        boletosSeleccionados.remove(boleto);
        actualizarTotal();
    }


    public void limpiar() {
        boletosSeleccionados.clear();
        total = 0;
        ventaIdTemporal = -1;
    }

    private void actualizarTotal() {
        total = 0;
        for (BoletoWrapper b : boletosSeleccionados) {
            total += b.getPrecio();
        }
    }

    public List<BoletoWrapper> getBoletosSeleccionados() {
        return new ArrayList<>(boletosSeleccionados);
    }

    public double getTotal() {
        return total;
    }

    public boolean isEmpty() {
        return boletosSeleccionados.isEmpty();
    }

    public int size() {
        return boletosSeleccionados.size();
    }

    public int getVentaIdTemporal() {
        return ventaIdTemporal;
    }

    public void setVentaIdTemporal(int ventaIdTemporal) {
        this.ventaIdTemporal = ventaIdTemporal;
    }

    /**
     * Obtiene los códigos de asientos seleccionados para una función específica
     */
    public List<Integer> getAsientosSeleccionadosParaFuncion(int codigoFuncion) {
        List<Integer> asientos = new ArrayList<>();
        for (BoletoWrapper boleto : boletosSeleccionados) {
            if (boleto.getFuncion() != null && boleto.getFuncion().getCodigo() == codigoFuncion) {
                if (boleto.getAsiento() != null) {
                    asientos.add(boleto.getAsiento().getCodigo());
                }
            }
        }
        return asientos;
    }
}