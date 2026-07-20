package proyecto.com.proyectobasesdedatos.modelos;

public class Ciudad {
    private int codigo;
    private String nombre;
    private String codigoPostal;
    private Pais pais;

    public Ciudad(int codigo, String nombre, String codigoPostal, Pais pais) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
    }

    public Ciudad() {

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

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getCiudadFormateada(){
        return codigo + " - " + nombre;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
