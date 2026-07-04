package proyecto.com.proyectobasesdedatos.modelos;

import java.time.LocalDate;
import java.time.LocalTime;

public class Venta {
    private int codigo;
    private float precio_total;
    private LocalDate fecha;
    private LocalTime hora;
    private Cliente cliente;

    public Venta(int codigo, float precio_total, LocalDate fecha, LocalTime hora, Cliente cliente) {
        this.codigo = codigo;
        this.precio_total = precio_total;
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

    public float getPrecio_total() {
        return precio_total;
    }

    public void setPrecio_total(float precio_total) {
        this.precio_total = precio_total;
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
