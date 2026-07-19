package proyecto.com.proyectobasesdedatos.utilidades;

public enum Sexo {
    FEMENINO, MASCULINO;


    public String toString() {
        if(this == FEMENINO)
            return "F";
        else if(this == MASCULINO)
            return "M";
        return "";
    }

    public static Sexo getSexo(char sexo) {
        if(sexo == 'F'){
            return FEMENINO;
        }
        else if(sexo == 'M'){
            return MASCULINO;
        }
        return null;
    }
}
