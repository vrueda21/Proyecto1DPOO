package pregunta;
import java.util.*;

public class PreguntaCerrada extends Pregunta {

    private Dictionary<Opcion, String> opcionA;
    private Dictionary<Opcion, String> opcionB;
    private Dictionary<Opcion, String> opcionC;
    private Dictionary<Opcion, String> opcionD;
    private Dictionary<Opcion, String> respuesta; // La opción correcta
    private Dictionary<Opcion, String> escogida; // La opción elegida por el estudiante

    public PreguntaCerrada() {
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

    public void setOpcionB(Dictionary<Opcion, String> opcionB) {
        this.opcionB = opcionB;
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

    public void setOpcionD(Dictionary<Opcion, String> opcionD) {
        this.opcionD = opcionD;
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

    // Método modificado para aceptar un String como argumento
    public void elegirRespuesta(String opcion) {
        if (opcion.equals("A")) {
            setEscogida(this.opcionA);
        } else if (opcion.equals("B")) {
            setEscogida(this.opcionB);
        } else if (opcion.equals("C")) {
            setEscogida(this.opcionC);
        } else if (opcion.equals("D")) {
            setEscogida(this.opcionD);
        } else {
            throw new IllegalArgumentException("Opción no válida.");
        }
    }

    // Método original para seleccionar una opción utilizando Tipo
    public void escogerOpcion(Tipo respuesta) {
        if (respuesta.name().equals("A")) {
            setEscogida(this.opcionA);
        } else if (respuesta.name().equals("B")) {
            setEscogida(this.opcionB);
        } else if (respuesta.name().equals("C")) {
            setEscogida(this.opcionC);
        } else if (respuesta.name().equals("D")) {
            setEscogida(this.opcionD);
        }
    }

    public boolean esEvaluada() {
        return escogida != null; // Verifica si se ha escogido una opción
    }

    public boolean esCorrecta() {
        return escogida != null && escogida.equals(respuesta); // Compara si la opción escogida es la correcta
    }

    @Override
    public String getRetroalimentacion() {
        if (escogida == null) {
            return "No se ha seleccionado ninguna opción.";
        }
        return escogida.equals(respuesta) ? "Respuesta correcta." : "Respuesta incorrecta. La opción correcta era: " + respuesta;
    }
}
