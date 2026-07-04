package proyecto.com.proyectobasesdedatos.modelos;

public class Boleto {
    private int codigo;
    private Venta venta;
    private Funcion funcion;
    private Asiento asiento;
    private float precio;

    public Boleto(int codigo, Venta venta, Funcion funcion, Asiento asiento, float precio) {
        this.codigo = codigo;
        this.venta = venta;
        this.funcion = funcion;
        this.asiento = asiento;
        this.precio = precio;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public void setFuncion(Funcion funcion) {
        this.funcion = funcion;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public void setAsiento(Asiento asiento) {
        this.asiento = asiento;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
}
