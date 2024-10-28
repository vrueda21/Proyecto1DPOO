package actividad;

import pregunta.PreguntaCerrada;
import usuario.Estudiante;

import java.time.LocalDateTime;
import java.util.List;

public class Quiz extends Actividad {

    protected List<PreguntaCerrada> listaPreguntas;
    protected double calificacionMinima;

    public Quiz(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, 
                double version, LocalDateTime fechaLimite, Status status, Obligatoria obligatoria, 
                List<PreguntaCerrada> listaPreguntas, double calificacionMinima) {
        super(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite, status, obligatoria, "quiz");
        this.listaPreguntas = listaPreguntas;
        this.calificacionMinima = calificacionMinima;
    }

    // Getter para listaPreguntas
    public List<PreguntaCerrada> getListaPreguntas() {
        return listaPreguntas;
    }

    // Método para completar el quiz
    public void completarQuiz(Estudiante estudiante, double calificacionObtenida) {
        if (estudiante != null) {
            if (calificacionObtenida >= calificacionMinima) {
                this.status = Status.Exitosa;
                System.out.println("El quiz fue completado exitosamente por: " + estudiante.getNombre());
            } else {
                this.status = Status.noExitosa;
                System.out.println("El quiz no fue aprobado por: " + estudiante.getNombre());
            }
        } else {
            throw new SecurityException("Un estudiante debe completar el quiz.");
        }
    }

    // Getter para calificacionMinima
    public double getCalificacionMinima() {
        return calificacionMinima;
    }
}
