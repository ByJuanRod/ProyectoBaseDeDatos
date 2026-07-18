package proyecto.com.proyectobasesdedatos.modelos;

public class Ciudad {
    private int codigo;
    private String nombre;
    private String codigoPostal;

    public Ciudad(int codigo, String nombre, String codigoPostal) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.codigoPostal = codigoPostal;
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

}
