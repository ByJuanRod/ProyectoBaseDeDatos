package proyecto.com.proyectobasesdedatos.modelos;

import java.time.LocalDate;
import java.time.LocalTime;

public class Venta {
    private int codigo;
    private float precioTotal;
    private LocalDate fecha;
    private LocalTime hora;
    private Cliente cliente;

    public Venta(int codigo, float precioTotal, LocalDate fecha, LocalTime hora, Cliente cliente) {
        this.codigo = codigo;
        this.precioTotal = precioTotal;
        this.fecha = fecha;
        this.hora = hora;
        this.cliente = cliente;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
