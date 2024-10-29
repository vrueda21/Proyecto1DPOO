package actividad;

import pregunta.Pregunta;
import pregunta.PreguntaCerrada;
import pregunta.PreguntaAbierta;
import usuario.Estudiante;
import usuario.Profesor;
import java.time.LocalDateTime;
import java.util.List;

public class Examen extends Actividad {

    protected List<Pregunta> listaPreguntas;
    protected double calificacionMinima; // Calificación mínima para aprobar
    private double calificacionObtenida; // Nota final obtenida por el estudiante
    private boolean evaluacionCompleta; // Indica si todas las preguntas han sido evaluadas

    public Examen(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, 
                  double version, LocalDateTime fechaLimite, Status status, Obligatoria obligatoria, 
                  List<Pregunta> listaPreguntas, double calificacionMinima, Profesor creador, 
                  List<Actividad> actividadesPreviasSugeridas, List<Actividad> actividadesSeguimientoRecomendadas) {
        super(descripcion, nivelDificultad, objetivo, duracionEsperada, version, 
              fechaLimite, status, obligatoria, "examen", creador, 
              actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
        this.listaPreguntas = listaPreguntas;
        this.calificacionMinima = calificacionMinima;
        this.calificacionObtenida = 0.0;
        this.evaluacionCompleta = false; // Inicialmente, la evaluación no está completa
    }

    // Método para que el profesor evalúe una pregunta (cerrada o abierta)
    public void evaluarPregunta(Profesor profesor, Pregunta pregunta, boolean correcta, String comentario) {
        if (profesor == null || !profesor.equals(creador)) {
            throw new SecurityException("Solo el profesor creador puede evaluar las preguntas del examen.");
        }

        if (pregunta instanceof PreguntaCerrada) {
            PreguntaCerrada preguntaCerrada = (PreguntaCerrada) pregunta;
            if (correcta) {
                preguntaCerrada.elegirRespuesta("A"); // Marcar la opción correcta (simulación)
            } else {
                preguntaCerrada.elegirRespuesta(null); // Simula una respuesta incorrecta
            }
        } else if (pregunta instanceof PreguntaAbierta) {
            PreguntaAbierta preguntaAbierta = (PreguntaAbierta) pregunta;
            preguntaAbierta.evaluarPorProfesor(correcta, comentario); // Evaluar la pregunta abierta
        }

        // Verificar si todas las preguntas han sido evaluadas
        if (!hayPreguntasSinEvaluar()) {
            evaluacionCompleta = true; // Marcar la evaluación como completa
            calcularCalificacionFinal(); // Calcular la calificación final ahora que todo ha sido evaluado
        }
    }

    // Método para calcular la nota obtenida en el examen
    private void calcularNotaObtenida() {
        int totalPreguntas = listaPreguntas.size(); // Total de preguntas en el examen
        int preguntasCorrectas = 0; // Contador de preguntas correctas

        // Contar las preguntas correctas después de la evaluación
        for (Pregunta pregunta : listaPreguntas) {
            if (pregunta instanceof PreguntaCerrada) {
                PreguntaCerrada preguntaCerrada = (PreguntaCerrada) pregunta;
                if (preguntaCerrada.esEvaluada() && preguntaCerrada.esCorrecta()) {
                    preguntasCorrectas++;
                }
            } else if (pregunta instanceof PreguntaAbierta) {
                PreguntaAbierta preguntaAbierta = (PreguntaAbierta) pregunta;
                if (preguntaAbierta.esEvaluada() && preguntaAbierta.esCorrecta()) {
                    preguntasCorrectas++;
                }
            }
        }

        // Calcular la nota obtenida como porcentaje de preguntas correctas
        calificacionObtenida = ((double) preguntasCorrectas / totalPreguntas) * 100;
    }

    // Método para calcular la calificación final del examen
    private void calcularCalificacionFinal() {
        calcularNotaObtenida(); // Calcula la nota obtenida

        if (calificacionObtenida >= calificacionMinima) {
            this.status = Status.Exitosa;
            System.out.println("El examen ha sido aprobado con una nota de " + calificacionObtenida + "%.");
        } else {
            this.status = Status.noExitosa;
            System.out.println("El examen no ha sido aprobado. Nota obtenida: " + calificacionObtenida + "%.");
        }
    }

    // Verificar si hay preguntas sin evaluar
    private boolean hayPreguntasSinEvaluar() {
        for (Pregunta pregunta : listaPreguntas) {
            if (!pregunta.esEvaluada()) {
                return true;
            }
        }
        return false;
    }

    // Método para completar el examen
    public void completarExamen(Estudiante estudiante) {
        if (estudiante == null) {
            throw new SecurityException("Un estudiante debe completar el examen.");
        }

        // Verificar si hay preguntas sin evaluar
        if (hayPreguntasSinEvaluar()) {
            System.out.println("El examen contiene preguntas que deben ser evaluadas por un profesor.");
            this.status = Status.Enviada; // Estado temporal hasta que el profesor complete la evaluación
        } else if (evaluacionCompleta) {
            calcularCalificacionFinal(); // Determina si el examen fue aprobado o no
        }
    }
}
