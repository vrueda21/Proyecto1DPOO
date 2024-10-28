package pregunta;

public class PreguntaAbierta extends Pregunta{

    private String respuesta; 

    public PreguntaAbierta(Tipo tipo, String respuesta){
        super(tipo);
        this.respuesta = respuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        if (respuesta == null || respuesta.trim().isEmpty()){
            throw new IllegalArgumentException("La respuesta no puede ser nula o vacía");
        } // Valida que la respuesta no sea nula o vacía

        this.respuesta = respuesta;
    }


    public void responder(String respuesta){
        setRespuesta(respuesta); //
    }


}
