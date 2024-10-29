package actividad;

import pregunta.Pregunta;
import pregunta.PreguntaCerrada;
import pregunta.PreguntaAbierta;
import usuario.Estudiante;
import usuario.Profesor;
import java.time.LocalDateTime;
import java.util.List;

public class Quiz extends Actividad {

    protected List<Pregunta> listaPreguntas;
    protected double calificacionMinima; // Calificación mínima para aprobar
    private boolean completadoUnaVez;
    private double calificacionObtenida; // Nota final obtenida por el estudiante
    private boolean evaluacionCompleta; // Indica si todas las preguntas han sido evaluadas

    public Quiz(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, 
                double version, LocalDateTime fechaLimite, Status status, Obligatoria obligatoria, 
                List<Pregunta> listaPreguntas, double calificacionMinima, Profesor creador, 
                List<Actividad> actividadesPreviasSugeridas, List<Actividad> actividadesSeguimientoRecomendadas) {
        super(descripcion, nivelDificultad, objetivo, duracionEsperada, version, 
              fechaLimite, status, obligatoria, "quiz", creador, 
              actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
        this.listaPreguntas = listaPreguntas;
        this.calificacionMinima = calificacionMinima;
        this.completadoUnaVez = false;
        this.calificacionObtenida = 0.0;
        this.evaluacionCompleta = false;
    }

    // Método para calcular la nota obtenida en el quiz
    private void calcularNotaObtenida() {
        int totalPreguntas = listaPreguntas.size(); // Total de preguntas en el quiz
        int preguntasCorrectas = 0; // Inicializar el contador de respuestas correctas

        for (Pregunta pregunta : listaPreguntas) {
            if (pregunta instanceof PreguntaCerrada) {
                PreguntaCerrada preguntaCerrada = (PreguntaCerrada) pregunta;
                if (preguntaCerrada.esCorrecta()) {
                    preguntasCorrectas++;
                }
            } else if (pregunta instanceof PreguntaAbierta) {
                PreguntaAbierta preguntaAbierta = (PreguntaAbierta) pregunta;
                if (preguntaAbierta.esEvaluada() && preguntaAbierta.esCorrecta()) {
                    preguntasCorrectas++;
                }
            }
        }

        // Calcular la nota final como porcentaje de preguntas correctas
        calificacionObtenida = ((double) preguntasCorrectas / totalPreguntas) * 100;
    }

    // Método para completar el quiz (inicialmente para preguntas cerradas)
    public void completarQuiz(Estudiante estudiante) {
        if (estudiante == null) {
            throw new SecurityException("Un estudiante debe completar el quiz.");
        }

        if (completadoUnaVez) {
            throw new UnsupportedOperationException("El quiz no se puede repetir una vez aprobado.");
        }

        // Verificar si hay preguntas abiertas sin evaluar
        if (hayPreguntasAbiertasPendientes()) {
            System.out.println("El quiz contiene preguntas abiertas que deben ser evaluadas por un profesor.");
            this.status = Status.Enviada; // Estado temporal hasta que el profesor complete la evaluación
        } else {
            // Calificar automáticamente si no hay preguntas abiertas pendientes
            calcularNotaObtenida(); // Calcula la nota obtenida por el estudiante
            calcularCalificacionFinal(estudiante); // Determina si el quiz fue aprobado o no
        }
    }

    // Método para calcular la calificación final del quiz
    private void calcularCalificacionFinal(Estudiante estudiante) {
        if (evaluacionCompleta || !hayPreguntasAbiertasPendientes()) {
            if (calificacionObtenida >= calificacionMinima) {
                this.status = Status.Exitosa;
                completadoUnaVez = true;
                if (estudiante != null) {
                    System.out.println("El quiz fue completado exitosamente por: " + estudiante.getNombre() + " con una nota de " + calificacionObtenida + "%.");
                }
            } else {
                this.status = Status.noExitosa;
                if (estudiante != null) {
                    System.out.println("El quiz no fue aprobado por: " + estudiante.getNombre() + ". Nota obtenida: " + calificacionObtenida + "%.");
                }
            }
        } else {
            throw new IllegalStateException("El quiz no puede ser calificado hasta que todas las preguntas abiertas sean evaluadas.");
        }
    }

    // Verificar si hay preguntas abiertas sin evaluar
    private boolean hayPreguntasAbiertasPendientes() {
        for (Pregunta pregunta : listaPreguntas) {
            if (pregunta instanceof PreguntaAbierta && !pregunta.esEvaluada()) {
                return true;
            }
        }
        return false;
    }
}
