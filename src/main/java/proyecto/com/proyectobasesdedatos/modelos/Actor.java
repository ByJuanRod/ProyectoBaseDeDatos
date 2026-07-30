package proyecto.com.proyectobasesdedatos.modelos;

public class Actor extends Persona {
    public Actor() {
        super();
    }

    public Actor(Persona persona) {
        super(persona.getCodigo(), persona.getNombres(), persona.getApellidos(),
                persona.getFechaNacimiento(), persona.getSexo(), persona.getTelefono(),
                persona.getCorreo(), persona.getSectorResidencia());
    }
}