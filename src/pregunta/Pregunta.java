package pregunta;

public abstract class Pregunta {

    protected Tipo tipo;
    protected String enunciado; // Enunciado de la pregunta
    protected String retroalimentacion; // Retroalimentación de la pregunta

    // Constructor modificado para aceptar el enunciado
    public Pregunta(Tipo tipo, String enunciado) {
        if (enunciado == null || enunciado.isEmpty()) {
            throw new IllegalArgumentException("El enunciado de la pregunta no puede ser nulo o vacío");
        }
        this.tipo = tipo;
        this.enunciado = enunciado;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        if (enunciado == null || enunciado.isEmpty()) {
            throw new IllegalArgumentException("El enunciado de la pregunta no puede ser nulo o vacío");
        }
        this.enunciado = enunciado;
    }

    public void setTipo(Tipo tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de pregunta no puede ser nulo");
        }
        this.tipo = tipo;
    }

    // Métodos abstractos
    public abstract boolean esEvaluada();
    public abstract String getRetroalimentacion();
    public abstract boolean esCorrecta();
}
