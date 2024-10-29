package pregunta;

public abstract class Pregunta {

    protected Tipo tipo;

    protected String enunciado;

    protected String retroalimentacion; // Retroalimentación de la pregunta
    
    public Pregunta(Tipo tipo){
        this.tipo = tipo;
        this.enunciado = "Enunciado de la pregunta";
    }

    public Tipo getTipo() {
        return tipo;
    }

    public String getEnunciado(){
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        if (enunciado == null || enunciado.isEmpty()){
            throw new IllegalArgumentException("El enunciado de la pregunta no puede ser nulo o vacío");
        } // Valida que el enunciado no sea nulo o vacío

        this.enunciado = enunciado;
    }

    public void setTipo(Tipo tipo) {
        if (tipo == null){
            throw new IllegalArgumentException("El tipo de pregunta no puede ser nulo");
        } // Valida que el tipo no sea nulo

        this.tipo = tipo;
    }

// Metodo abstracto para verificar si la pregunta ha sido evaluda por el profesor
public abstract boolean esEvaluada();

// Metodo abstracto para obtener la retroalimentacion de la pregunta
public abstract String getRetroalimentacion();

public abstract boolean esCorrecta();

}
