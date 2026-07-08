package proyecto.com.proyectobasesdedatos.modelos;

public class GeneroPelicula {
    private int codigoPelicula;
    private int codigoGeneros;

    public GeneroPelicula(int codigoPelicula, int codigoGeneros) {
        this.codigoPelicula = codigoPelicula;
        this.codigoGeneros = codigoGeneros;
    }

    public int getCodigoPelicula() {
        return codigoPelicula;
    }

    public void setCodigoPelicula(int codigoPelicula) {
        this.codigoPelicula = codigoPelicula;
    }

    public int getCodigoGeneros() {
        return codigoGeneros;
    }

    public void setCodigoGeneros(int codigoGeneros) {
        this.codigoGeneros = codigoGeneros;
    }
}
