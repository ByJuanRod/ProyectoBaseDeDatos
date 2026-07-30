package proyecto.com.proyectobasesdedatos.modelos;

import java.util.Date;

public class Persona {
    private int codigo;
    private String nombres;
    private String apellidos;
    private Date fechaNacimiento;
    private char sexo;
    private String telefono;
    private String correo;
    private Sector sectorResidencia;

    public Persona() {}

    public Persona(int codigo, String nombres, String apellidos, Date fechaNacimiento,
                   char sexo, String telefono, String correo, Sector sectorResidencia) {
        this.codigo = codigo;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.telefono = telefono;
        this.correo = correo;
        this.sectorResidencia = sectorResidencia;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public char getSexo() { return sexo; }
    public void setSexo(char sexo) { this.sexo = sexo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public Sector getSectorResidencia() { return sectorResidencia; }
    public void setSectorResidencia(Sector sectorResidencia) { this.sectorResidencia = sectorResidencia; }
}