package proyecto.com.proyectobasesdedatos.modelos.wrappers;

import javafx.beans.property.*;
import proyecto.com.proyectobasesdedatos.modelos.Asiento;
import proyecto.com.proyectobasesdedatos.modelos.Boleto;
import proyecto.com.proyectobasesdedatos.modelos.Funcion;

public class BoletoWrapper {
    private final IntegerProperty codigo;
    private final StringProperty asientoInfo;
    private final FloatProperty precio;
    private final IntegerProperty funcionCodigo;
    private final StringProperty peliculaNombre;

    private Boleto boletoOriginal;
    private Funcion funcion;
    private Asiento asiento;

    public BoletoWrapper() {
        this.codigo = new SimpleIntegerProperty(0);
        this.asientoInfo = new SimpleStringProperty("");
        this.precio = new SimpleFloatProperty(0);
        this.funcionCodigo = new SimpleIntegerProperty(0);
        this.peliculaNombre = new SimpleStringProperty("");
    }

    public BoletoWrapper(Funcion funcion, Asiento asiento, float precio) {
        this();
        this.funcion = funcion;
        this.asiento = asiento;

        if (asiento != null) {
            this.asientoInfo.set(asiento.getFila() + asiento.getNumero());
        }
        this.precio.set(precio);
        if (funcion != null) {
            this.funcionCodigo.set(funcion.getCodigo());
            if (funcion.getPelicula() != null) {
                this.peliculaNombre.set(funcion.getPelicula().getNombre());
            }
        }
    }

    public BoletoWrapper(Boleto boleto) {
        this();
        this.boletoOriginal = boleto;
        if (boleto != null) {
            this.codigo.set(boleto.getCodigo());
            if (boleto.getAsiento() != null) {
                this.asientoInfo.set(boleto.getAsiento().getFila() + boleto.getAsiento().getNumero());
            }
            this.precio.set((float) boleto.getPrecioAplicado());
            if (boleto.getFuncion() != null) {
                this.funcionCodigo.set(boleto.getFuncion().getCodigo());
                if (boleto.getFuncion().getPelicula() != null) {
                    this.peliculaNombre.set(boleto.getFuncion().getPelicula().getNombre());
                }
            }
        }
    }

    // Getters y property methods
    public int getCodigo() { return codigo.get(); }
    public IntegerProperty codigoProperty() { return codigo; }
    public void setCodigo(int codigo) { this.codigo.set(codigo); }

    public String getAsientoInfo() { return asientoInfo.get(); }
    public StringProperty asientoInfoProperty() { return asientoInfo; }
    public void setAsientoInfo(String asientoInfo) { this.asientoInfo.set(asientoInfo); }

    public float getPrecio() { return precio.get(); }
    public FloatProperty precioProperty() { return precio; }
    public void setPrecio(float precio) { this.precio.set(precio); }

    public int getFuncionCodigo() { return funcionCodigo.get(); }
    public IntegerProperty funcionCodigoProperty() { return funcionCodigo; }
    public void setFuncionCodigo(int funcionCodigo) { this.funcionCodigo.set(funcionCodigo); }

    public String getPeliculaNombre() { return peliculaNombre.get(); }
    public StringProperty peliculaNombreProperty() { return peliculaNombre; }
    public void setPeliculaNombre(String peliculaNombre) { this.peliculaNombre.set(peliculaNombre); }

    public Boleto getBoletoOriginal() { return boletoOriginal; }
    public void setBoletoOriginal(Boleto boletoOriginal) { this.boletoOriginal = boletoOriginal; }

    public Funcion getFuncion() { return funcion; }
    public Asiento getAsiento() { return asiento; }

    // Crear un Boleto real a partir de este wrapper
    public Boleto crearBoleto() {
        Boleto boleto = new Boleto();
        boleto.setCodigo(this.codigo.get());
        boleto.setPrecioAplicado(this.precio.get());
        boleto.setFuncion(this.funcion);
        boleto.setAsiento(this.asiento);
        return boleto;
    }
}