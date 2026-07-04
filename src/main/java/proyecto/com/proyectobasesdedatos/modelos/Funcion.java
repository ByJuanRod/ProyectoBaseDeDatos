package proyecto.com.proyectobasesdedatos.modelos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Funcion {
    private int codigo;
    private Pelicula pelicula;
    private Sala sala;
    private LocalDateTime fecha;
    private LocalTime hora_inicio;
    private LocalTime hora_final;
    private float precio_entrada;

    public Funcion(int codigo, Pelicula pelicula, Sala sala,
                   LocalDateTime fecha, LocalTime hora_inicio, LocalTime hora_final, float precio_entrada) {
        this.codigo = codigo;
        this.pelicula = pelicula;
        this.sala = sala;
        this.fecha = fecha;
        this.hora_inicio = hora_inicio;
        this.hora_final = hora_final;
        this.precio_entrada = precio_entrada;
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

    public LocalTime getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(LocalTime hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public LocalTime getHora_final() {
        return hora_final;
    }

    public void setHora_final(LocalTime hora_final) {
        this.hora_final = hora_final;
    }

    public float getPrecio_entrada() {
        return precio_entrada;
    }

    public void setPrecio_entrada(float precio_entrada) {
        this.precio_entrada = precio_entrada;
    }
}
