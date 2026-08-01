package proyecto.com.proyectobasesdedatos.modelos;

import java.util.ArrayList;
import java.util.List;

public class Sala {
    private int codigo;
    private String nombre;
    private int capacidad;
    private Sucursal sucursal;
    private ArrayList<Asiento> asientos;

    public Sala() {}

    public Sala(int codigo, String nombre, int capacidad, Sucursal sucursal) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.sucursal = sucursal;
        asientos = new ArrayList<>();
    }

    // Getters y Setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }
    public void setAsientos(ArrayList<Asiento> asientos) { this.asientos = asientos; }
    public ArrayList<Asiento> getAsientos() { return asientos; }


}