package proyecto.com.proyectobasesdedatos.modelos;

public class PuestoTrabajo {
    private int codigo;
    private String nombre;
    private double salarioBase;

    public PuestoTrabajo() {}

    public PuestoTrabajo(int codigo, String nombre, double salarioBase) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.salarioBase = salarioBase;
    }

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public double getSalarioBase() { return salarioBase; }
    public void setSalarioBase(double salarioBase) { this.salarioBase = salarioBase; }
}