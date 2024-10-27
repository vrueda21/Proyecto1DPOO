package pregunta;
import java.util.*;


public class PreguntaCerrada extends Pregunta{

    private Dictionary <Opcion, String> opcionA;
    private Dictionary <Opcion, String> opcionB;
    private Dictionary <Opcion, String> opcionC;
    private Dictionary <Opcion, String> opcionD;
    private Dictionary <Opcion, String> respuesta;
    private Dictionary <Opcion, String> escogida;

    public PreguntaCerrada(){

        super(Tipo.Cerrada);
    }

    public Dictionary<Opcion, String> getOpcionA() {
        return opcionA;
    }

    public void setOpcionA(Dictionary<Opcion, String> opcionA) {
        this.opcionA = opcionA;
    }

    public Dictionary<Opcion, String> getOpcionB() {
        return opcionB;
    }

    public void setOpcionB(Dictionary<Opcion, String> opcionBD) {
        this.opcionB = opcionBD;
    }

    public Dictionary<Opcion, String> getOpcionC() {
        return opcionC;
    }

    public void setOpcionC(Dictionary<Opcion, String> opcionC) {
        this.opcionC = opcionC;
    }

    public Dictionary<Opcion, String> getOpcionD() {
        return opcionD;
    }

    public void setOpcionD(Dictionary<Opcion, String> opcion) {
        this.opcionD = opcion;
    }

    public Dictionary<Opcion, String> getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Dictionary<Opcion, String> respuesta) {
        this.respuesta = respuesta;
    }

    
    
    public Dictionary<Opcion, String> getEscogida() {
        return escogida;
    }

    public void setEscogida(Dictionary<Opcion, String> escogida) {
        this.escogida = escogida;
    }

    public void elegirRespuesta(Tipo respuesta){

        if (respuesta.name().equals("A")){

            setRespuesta(this.opcionA);
        }

        else if (respuesta.name().equals("B")){

            setRespuesta(this.opcionB);
        }

        else if (respuesta.name().equals("C")){

            setRespuesta(this.opcionC);
        }
        
        else {
            setRespuesta(this.opcionD);
        }

    }

    public void escogerOpcion(Tipo respuesta){

        if (respuesta.name().equals("A")){

            setEscogida(this.opcionA);
        }

        else if (respuesta.name().equals("B")){

            setEscogida(this.opcionB);
        }

        else if (respuesta.name().equals("C")){

            setEscogida(this.opcionC);
        }
        
        else {
            setEscogida(this.opcionD);
        }

    }

}
