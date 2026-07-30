package proyecto.com.proyectobasesdedatos.modelos;

public class Sucursal {
    private int codigo;
    private String nombre;
    private String calle;
    private String numero;
    private String telefono;
    private String correo;
    private Sector sector;

    public Sucursal() {}

    public Sucursal(int codigo, String nombre, String calle, String numero,
                    String telefono, String correo, Sector sector) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.calle = calle;
        this.numero = numero;
        this.telefono = telefono;
        this.correo = correo;
        this.sector = sector;
    }

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public Sector getSector() { return sector; }
    public void setSector(Sector sector) { this.sector = sector; }
}