package proyecto.com.proyectobasesdedatos.modelos;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Venta {
    private int codigo;
    private Date fecha;
    private Time hora;
    private double precioTotal;
    private Cliente cliente;
    private Empleado empleado;
    private Sucursal sucursal;
    private List<Boleto> boletos;

    public Venta() {}

    public Venta(int codigo, Date fecha, Time hora, double precioTotal,
                 Cliente cliente, Empleado empleado, Sucursal sucursal) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.hora = hora;
        this.precioTotal = precioTotal;
        this.cliente = cliente;
        this.empleado = empleado;
        this.sucursal = sucursal;
    }

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public Time getHora() { return hora; }
    public void setHora(Time hora) { this.hora = hora; }
    public double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(double precioTotal) { this.precioTotal = precioTotal; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }
    public List<Boleto> getBoletos() { return boletos; }
    public void setBoletos(List<Boleto> boletos) { this.boletos = boletos; }
}