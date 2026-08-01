package proyecto.com.proyectobasesdedatos.modelos;

public class Municipio {
    private int idMunicipio;
    private String nombreMunicipio;
    private Ciudad ciudad;
    private  String nombreCiudad;

    public Municipio() {}

    public Municipio(int idMunicipio, String nombreMunicipio, Ciudad ciudad) {
        this.idMunicipio = idMunicipio;
        this.nombreMunicipio = nombreMunicipio;
        this.ciudad = ciudad;
        this.nombreCiudad = ciudad.getNombre();
    }

    public int getIdMunicipio() { return idMunicipio; }
    public void setIdMunicipio(int idMunicipio) { this.idMunicipio = idMunicipio; }
    public String getNombreMunicipio() { return nombreMunicipio; }
    public void setNombreMunicipio(String nombreMunicipio) { this.nombreMunicipio = nombreMunicipio; }
    public Ciudad getCiudad() { return ciudad; }
    public void setCiudad(Ciudad ciudad) { this.ciudad = ciudad; }
    public String getNombreCiudad() {
        return nombreCiudad;
    }
}