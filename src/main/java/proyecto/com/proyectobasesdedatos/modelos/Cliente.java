package proyecto.com.proyectobasesdedatos.modelos;

import java.util.Date;

public class Cliente extends Persona {
    private int cantidadEntradas;
    private Date fechaRegistro;

    public Cliente() {
        super();
    }

    public Cliente(Persona persona, int cantidadEntradas, Date fechaRegistro) {
        super(persona.getCodigo(), persona.getNombres(), persona.getApellidos(),
                persona.getFechaNacimiento(), persona.getSexo(), persona.getTelefono(),
                persona.getCorreo(), persona.getSectorResidencia());
        this.cantidadEntradas = cantidadEntradas;
        this.fechaRegistro = fechaRegistro;
    }

    public int getCantidadEntradas() { return cantidadEntradas; }
    public void setCantidadEntradas(int cantidadEntradas) { this.cantidadEntradas = cantidadEntradas; }
    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}