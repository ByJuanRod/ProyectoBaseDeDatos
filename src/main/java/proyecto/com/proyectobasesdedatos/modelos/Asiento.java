package proyecto.com.proyectobasesdedatos.modelos;

public class Asiento {
    private int codigo_asiento;
    private Sala sala;
    private String tipo;

    public Asiento(int codigo_asiento, Sala sala, String tipo) {
        this.codigo_asiento = codigo_asiento;
        this.sala = sala;
        this.tipo = tipo;
    }

    public int getCodigo_asiento() {
        return codigo_asiento;
    }

    public void setCodigo_asiento(int codigo_asiento) {
        this.codigo_asiento = codigo_asiento;
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
