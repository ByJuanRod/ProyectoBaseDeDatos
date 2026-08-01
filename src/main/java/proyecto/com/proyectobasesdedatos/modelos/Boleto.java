package proyecto.com.proyectobasesdedatos.modelos;

public class Boleto {
    private int codigo;
    private double precioAplicado;
    private Venta venta;
    private Funcion funcion;
    private Asiento asiento;

    public Boleto() {}

    public Boleto(int codigo, double precioAplicado, Venta venta, Funcion funcion, Asiento asiento) {
        this.codigo = codigo;
        this.precioAplicado = precioAplicado;
        this.venta = venta;
        this.funcion = funcion;
        this.asiento = asiento;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public double getPrecioAplicado() { return precioAplicado; }
    public void setPrecioAplicado(double precioAplicado) { this.precioAplicado = precioAplicado; }
    public Venta getVenta() { return venta; }
    public void setVenta(Venta venta) { this.venta = venta; }
    public Funcion getFuncion() { return funcion; }
    public void setFuncion(Funcion funcion) { this.funcion = funcion; }
    public Asiento getAsiento() { return asiento; }
    public void setAsiento(Asiento asiento) { this.asiento = asiento; }
}