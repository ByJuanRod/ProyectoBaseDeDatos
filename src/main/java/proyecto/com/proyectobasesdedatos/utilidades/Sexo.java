package proyecto.com.proyectobasesdedatos.utilidades;

public enum Sexo {
    MASCULINO('M'),
    FEMENINO('F');

    private final char valor;

    Sexo(char valor) {
        this.valor = valor;
    }

    public char getValor() {
        return valor;
    }

    public static Sexo getSexo(char valor) {
        for (Sexo sexo : Sexo.values()) {
            if (sexo.getValor() == valor) {
                return sexo;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }
}