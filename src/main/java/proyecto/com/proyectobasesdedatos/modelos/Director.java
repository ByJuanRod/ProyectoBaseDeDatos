package proyecto.com.proyectobasesdedatos.modelos;

import java.time.LocalDate;

public class Director extends Artista{

    public Director(int codigo, String nombre, String apellido, LocalDate fechaNacimiento, char sexo) {
        super(codigo, nombre, apellido, fechaNacimiento, sexo);
    }
}
