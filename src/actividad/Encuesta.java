package actividad;

import pregunta.Pregunta;
import usuario.Usuario;
import usuario.Profesor;
import usuario.Estudiante;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Encuesta extends Actividad {

    private ArrayList<Pregunta> listaPreguntas; // Lista de preguntas de la encuesta
    private boolean completada; // Indica si la encuesta ha sido completada

    public Encuesta(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, 
                    double version, LocalDateTime fechaLimite, Status status, Obligatoria obligatoria, 
                    String tipo, ArrayList<Pregunta> listaPreguntas, Profesor creador) {
        super(descripcion, nivelDificultad, objetivo, duracionEsperada, version, 
              fechaLimite, status, obligatoria, tipo, creador, null, null);
        this.listaPreguntas = listaPreguntas;
        this.completada = false; // Inicialmente, la encuesta no está completada
    }

    // Método para obtener la lista de preguntas
    public ArrayList<Pregunta> getListaPreguntas() {
        return listaPreguntas;
    }

    // Método para agregar preguntas a la encuesta (solo profesores)
    public void agregarPregunta(Pregunta pregunta, Usuario usuario) {
        if (usuario instanceof Profesor && usuario.equals(creador)) {
            listaPreguntas.add(pregunta);
            System.out.println("Pregunta agregada a la encuesta por: " + usuario.getNombre());
        } else {
            throw new SecurityException("Solo el profesor creador puede agregar preguntas a la encuesta.");
        }
    }

    // Método para eliminar preguntas de la encuesta (solo profesores)
    public void eliminarPregunta(Pregunta pregunta, Usuario usuario) {
        if (usuario instanceof Profesor && usuario.equals(creador)) {
            listaPreguntas.remove(pregunta);
            System.out.println("Pregunta eliminada de la encuesta por: " + usuario.getNombre());
        } else {
            throw new SecurityException("Solo el profesor creador puede eliminar preguntas de la encuesta.");
        }
    }

    // Método para completar la encuesta por un estudiante
    public void completarEncuesta(Estudiante estudiante, ArrayList<String> respuestasEstudiante) {
        if (estudiante == null) {
            throw new SecurityException("Un estudiante debe completar la encuesta.");
        }

        if (completada) {
            throw new UnsupportedOperationException("La encuesta ya ha sido completada y no puede repetirse.");
        }

        if (respuestasEstudiante.size() != listaPreguntas.size()) {
            throw new IllegalArgumentException("Debe responder todas las preguntas para completar la encuesta.");
        }

        // Marcar la encuesta como completada
        completada = true;
        this.status = Status.Completado; // Cambiar el estado de la encuesta
        System.out.println("La encuesta fue completada por: " + estudiante.getNombre());
    }

    // Indica si la encuesta ha sido completada
    public boolean isCompletada() {
        return completada;
    }
}
