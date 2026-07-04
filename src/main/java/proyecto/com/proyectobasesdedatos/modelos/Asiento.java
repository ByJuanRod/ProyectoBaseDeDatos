package proyecto.com.proyectobasesdedatos.modelos;

public class Asiento {
    private int codigoAsiento;
    private Sala sala;
    private String tipo;

    public Asiento(int codigoAsiento, Sala sala, String tipo) {
        this.codigoAsiento = codigoAsiento;
        this.sala = sala;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
