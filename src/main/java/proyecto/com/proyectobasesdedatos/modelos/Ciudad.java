package proyecto.com.proyectobasesdedatos.modelos;

public class Ciudad {
    private int codigo;
    private String nombre;
    private int codigo_postal;

    public Ciudad(int codigo, String nombre, int codigo_postal) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.codigo_postal = codigo_postal;
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

    public int getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(int codigo_postal) {
        this.codigo_postal = codigo_postal;
    }
}
