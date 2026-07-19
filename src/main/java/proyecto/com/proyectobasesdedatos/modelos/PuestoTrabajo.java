package proyecto.com.proyectobasesdedatos.modelos;

public class PuestoTrabajo {
    private int  codigo;
    private String nombre;
    private float salarioBase;

    public PuestoTrabajo(int codigo, String nombre, float salarioBase) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.salarioBase = salarioBase;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(float salarioBase) {
        this.salarioBase = salarioBase;
    }
}
