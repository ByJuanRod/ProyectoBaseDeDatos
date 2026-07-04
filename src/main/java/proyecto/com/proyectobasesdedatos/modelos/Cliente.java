package proyecto.com.proyectobasesdedatos.modelos;

import java.time.LocalDate;

public class Cliente {
    private int codigo;
    private int entradas;
    private String nombres;
    private String apellidos;
    private String telefonos;
    private LocalDate fecha;

    public Cliente(int codigo, int entradas, String nombres, String apellidos, String telefonos, LocalDate fecha) {
        this.codigo = codigo;
        this.entradas = entradas;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefonos = telefonos;
        this.fecha = fecha;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getEntradas() {
        return entradas;
    }

    public void setEntradas(int entradas) {
        this.entradas = entradas;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
