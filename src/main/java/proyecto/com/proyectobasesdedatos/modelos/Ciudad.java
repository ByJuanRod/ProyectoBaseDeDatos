package proyecto.com.proyectobasesdedatos.modelos;

public class Ciudad {
    private int codigo;
    private String nombre;
    private int codigoPostal;
    private Pais pais;

    public Ciudad() {}

    public Ciudad(int codigo, String nombre, int codigoPostal, Pais pais) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(int codigoPostal) { this.codigoPostal = codigoPostal; }
    public Pais getPais() { return pais; }
    public void setPais(Pais pais) { this.pais = pais; }
}