package proyecto.com.proyectobasesdedatos.modelos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Funcion {
    private int codigo;
    private Pelicula pelicula;
    private Sala sala;
    private LocalDateTime fecha;
    private LocalTime horaInicio;
    private LocalTime horaFinal;
    private float precioEntrada;

    public Funcion(int codigo, Pelicula pelicula, Sala sala,
                   LocalDateTime fecha, LocalTime horaInicio, LocalTime horaFinal, float precioEntrada) {
        this.codigo = codigo;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.precioEntrada = precioEntrada;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(LocalTime horaFinal) {
        this.horaFinal = horaFinal;
    }

    public float getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(float precioEntrada) {
        this.precioEntrada = precioEntrada;
    }
}
