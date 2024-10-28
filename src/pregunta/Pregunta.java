package pregunta;

public abstract class Pregunta {

    protected Tipo tipo;
    
    public Pregunta(Tipo tipo){
        this.tipo = tipo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        if (tipo == null){
            throw new IllegalArgumentException("El tipo de pregunta no puede ser nulo");
        } // Valida que el tipo no sea nulo

        this.tipo = tipo;
    }

}
