package proyecto.com.proyectobasesdedatos.modelos;

public class Asiento {
    private int codigo;
    private int numero;
    private String fila;
    private Sala sala;

    public Asiento() {}

    public Asiento(int codigo, int numero, String fila, Sala sala) {
        this.codigo = codigo;
        this.numero = numero;
        this.fila = fila;
        this.sala = sala;
    }

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public String getFila() { return fila; }
    public void setFila(String fila) { this.fila = fila; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }


    @Override
    public String toString() {
        return this.fila + this.numero;
    }
}