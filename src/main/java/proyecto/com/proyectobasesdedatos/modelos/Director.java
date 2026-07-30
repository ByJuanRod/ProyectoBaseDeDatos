package proyecto.com.proyectobasesdedatos.modelos;

public class Director extends Persona {
    public Director() {
        super();
    }

    public Director(Persona persona) {
        super(persona.getCodigo(), persona.getNombres(), persona.getApellidos(),
                persona.getFechaNacimiento(), persona.getSexo(), persona.getTelefono(),
                persona.getCorreo(), persona.getSectorResidencia());
    }
}