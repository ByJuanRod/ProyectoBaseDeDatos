package proyecto.com.proyectobasesdedatos.modelos;

public class Sector {
    private int idSector;
    private String nombreSector;
    private Municipio municipio;
    private String nombreMunicipio;

    public Sector() {}

    public Sector(int idSector, String nombreSector, Municipio municipio) {
        this.idSector = idSector;
        this.nombreSector = nombreSector;
        this.municipio = municipio;
        this.nombreMunicipio = municipio.getNombreMunicipio();
    }

    public int getIdSector() { return idSector; }
    public void setIdSector(int idSector) { this.idSector = idSector; }
    public String getNombreSector() { return nombreSector; }
    public void setNombreSector(String nombreSector) { this.nombreSector = nombreSector; }
    public Municipio getMunicipio() { return municipio; }
    public void setMunicipio(Municipio municipio) { this.municipio = municipio; }
    public String getNombreMunicipio(){
        return nombreMunicipio;
    }
}