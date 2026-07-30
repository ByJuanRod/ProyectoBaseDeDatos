package proyecto.com.proyectobasesdedatos.modelos;

import java.util.Date;

public class Empleado extends Persona {
    private PuestoTrabajo puestoTrabajo ;
    private Date fechaContratacion;
    private double salario;
    private Sucursal sucursal;

    public Empleado() {
        super();
    }

    public Empleado(Persona persona, PuestoTrabajo puestoTrabajo, Date fechaContratacion,
                    double salario, Sucursal sucursal) {
        super(persona.getCodigo(), persona.getNombres(), persona.getApellidos(),
                persona.getFechaNacimiento(), persona.getSexo(), persona.getTelefono(),
                persona.getCorreo(), persona.getSectorResidencia());
        this.puestoTrabajo = puestoTrabajo;
        this.fechaContratacion = fechaContratacion;
        this.salario = salario;
        this.sucursal = sucursal;
    }

    public PuestoTrabajo getPuestoTrabajo() { return puestoTrabajo; }
    public void setPuestoTrabajo(PuestoTrabajo puestoTrabajo) { this.puestoTrabajo = puestoTrabajo; }
    public Date getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(Date fechaContratacion) { this.fechaContratacion = fechaContratacion; }
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }
}