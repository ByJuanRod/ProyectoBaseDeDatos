package proyecto.com.proyectobasesdedatos.modelos;

public class Empleado {
    private int codigo;
    private String nombre;
    private PuestoTrabajo puesto;
    private String telefono;
    private String correo;
    private char sexo;
    private Sucursal sucursal;

    public Empleado(int codigo, String nombre, PuestoTrabajo puesto, String telefono, String correo, char sexo, Sucursal sucursal) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.puesto = puesto;
        this.telefono = telefono;
        this.correo = correo;
        this.sexo = sexo;
        this.sucursal = sucursal;
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

    public PuestoTrabajo getPuesto() {
        return puesto;
    }

    public void setPuesto(PuestoTrabajo puesto) {
        this.puesto = puesto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }
}
