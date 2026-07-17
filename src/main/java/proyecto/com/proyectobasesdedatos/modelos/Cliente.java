package proyecto.com.proyectobasesdedatos.modelos;

import java.time.LocalDate;

public class Cliente {
    private int codigo;
    private int entradas;
    private String nombres;
    private String apellidos;
    private String telefono;
    private LocalDate fechaNacimiento;
    private char sexo;
    private String correo;
    private Ciudad ciudad;

    public Cliente(int codigo, int entradas, String nombres, String apellidos, String telefonos, LocalDate fechaNacimiento, Ciudad ciudad) {
        this.codigo = codigo;
        this.entradas = entradas;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefonos;
        this.fechaNacimiento = fechaNacimiento;
        this.ciudad = ciudad;
    }

    public Cliente() {}

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefonos) {
        this.telefono = telefonos;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }
}
