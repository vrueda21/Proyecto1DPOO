
package actividad;

import pregunta.Pregunta;
import java.util.ArrayList;
import usuario.Usuario;
import usuario.Profesor;
import java.time.LocalDateTime;

public class Encuesta extends Actividad{

    ArrayList<Pregunta> listaPreguntas;

    public Encuesta(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, double version, LocalDateTime fechaLimite, Status status, Obligatoria obligatoria, String tipo, ArrayList<Pregunta> listaPreguntas) throws IllegalArgumentException{
        super(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite, status, obligatoria, tipo);
        this.listaPreguntas = listaPreguntas;
    }

    public ArrayList<Pregunta> getListaPreguntas() {
        return listaPreguntas;
    }

    public void setListaPreguntas(ArrayList<Pregunta> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    public void agregarPregunta(Pregunta pregunta, Usuario usuario){
        if (usuario instanceof Profesor){
            listaPreguntas.add(pregunta);
        } else {
            throw new IllegalArgumentException("Solo los profesores pueden agregar preguntas a la encuesta");
        }
    }

    public void eliminarPregunta(Pregunta pregunta, Usuario usuario){
        if (usuario instanceof Profesor){
            listaPreguntas.remove(pregunta);
        } else {
            throw new IllegalArgumentException("Solo los profesores pueden eliminar preguntas de la encuesta");
        }
    }

}