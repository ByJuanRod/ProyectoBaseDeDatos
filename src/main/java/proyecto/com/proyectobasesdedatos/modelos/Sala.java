package proyecto.com.proyectobasesdedatos.modelos;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private int codigo;
    private String nombre;
    private int capacidad;
    private List<Asiento> asientos = new ArrayList<>();


    public Sala(int codigo, String nombre, int capacidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidad = capacidad;
    }
    public void agregarAsiento(Asiento asiento) {
        this.asientos.add(asiento);
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

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public List<Asiento> getAsientos() {
        return asientos;
    }

    public void setAsientos(List<Asiento> asientos) {
        this.asientos = asientos;
    }
}
