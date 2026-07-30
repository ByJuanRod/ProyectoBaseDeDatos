package proyecto.com.proyectobasesdedatos.modelos;

import java.sql.Time;
import java.util.Date;

public class Funcion {
    private int codigo;
    private Date fecha;
    private Time horaInicio;
    private Time horaFin;
    private double precioEntrada;
    private Pelicula pelicula;
    private Sala sala;
    private Idioma idiomaSubtitulo;

    public Funcion() {}

    public Funcion(int codigo, Date fecha, Time horaInicio, Time horaFin,
                   double precioEntrada, Pelicula pelicula, Sala sala, Idioma idiomaSubtitulo) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.precioEntrada = precioEntrada;
        this.pelicula = pelicula;
        this.sala = sala;
        this.idiomaSubtitulo = idiomaSubtitulo;
    }

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public Time getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Time horaInicio) { this.horaInicio = horaInicio; }
    public Time getHoraFin() { return horaFin; }
    public void setHoraFin(Time horaFin) { this.horaFin = horaFin; }
    public double getPrecioEntrada() { return precioEntrada; }
    public void setPrecioEntrada(double precioEntrada) { this.precioEntrada = precioEntrada; }
    public Pelicula getPelicula() { return pelicula; }
    public void setPelicula(Pelicula pelicula) { this.pelicula = pelicula; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }
    public Idioma getIdiomaSubtitulo() { return idiomaSubtitulo; }
    public void setIdiomaSubtitulo(Idioma idiomaSubtitulo) { this.idiomaSubtitulo = idiomaSubtitulo; }
}