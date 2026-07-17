package proyecto.com.proyectobasesdedatos.modelos;

public class Asiento {
    private int codigoAsiento;
    private Sala sala;
    private int numero;
    private String fila;

    public Asiento(int codigoAsiento, Sala sala, int numero, String fila) {
        this.codigoAsiento = codigoAsiento;
        this.sala = sala;
        this.numero = numero;
        this.fila = fila;
    }

    public int getCodigoAsiento() {
        return codigoAsiento;
    }

    public void setCodigoAsiento(int codigoAsiento) {
        this.codigoAsiento = codigoAsiento;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }
}
