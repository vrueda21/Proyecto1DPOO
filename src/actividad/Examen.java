package actividad;

import pregunta.Pregunta;
import usuario.Estudiante;
import usuario.Profesor;

import java.time.LocalDateTime;
import java.util.List;

public class Examen extends Actividad {

    protected List<Pregunta> listaPreguntas;
    protected double calificacionMinima;

    public Examen(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, 
                  double version, LocalDateTime fechaLimite, Status status, Obligatoria obligatoria, 
                  List<Pregunta> listaPreguntas, double calificacionMinima) {
        super(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite, status, obligatoria, "examen");
        this.listaPreguntas = listaPreguntas;
        this.calificacionMinima = calificacionMinima;
    }

    // Getter para listaPreguntas
    public List<Pregunta> getListaPreguntas() {
        return listaPreguntas;
    }

    // Getter para calificacionMinima
    public double getCalificacionMinima() {
        return calificacionMinima;
    }

    // Método para enviar el examen
    public void enviarExamen(Estudiante estudiante) {
        if (estudiante != null) {
            this.status = Status.Enviada;
            System.out.println("El examen fue enviado por: " + estudiante.getNombre());
        } else {
            throw new SecurityException("Un estudiante debe enviar el examen.");
        }
    }

    // Método para evaluar el examen
    public void evaluarExamen(Profesor profesor, double calificacion) {
        if (profesor != null) {
            if (calificacion >= calificacionMinima) {
                this.status = Status.Exitosa;
                System.out.println("El examen fue aprobado por el profesor: " + profesor.getNombre());
            } else {
                this.status = Status.noExitosa;
                System.out.println("El examen fue reprobado por el profesor: " + profesor.getNombre());
            }
        } else {
            throw new SecurityException("Un profesor debe evaluar el examen.");
        }
    }
}
