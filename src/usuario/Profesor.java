package usuario;

import java.time.LocalDateTime;
import java.util.*;

import LPRS.LearningPath;
import actividad.*;
import pregunta.PreguntaAbierta;
import pregunta.PreguntaCerrada;
import pregunta.Pregunta;

public class Profesor extends Usuario {

    public Profesor(String nombre, String contrasenia, String correo) {
        super(nombre, contrasenia, "profesor", correo);
    }

    // Método para evaluar la tarea
    public void evaluarTarea(Tarea tarea, Estudiante estudiante, LearningPath learningPath, double calificacionObtenida, boolean exitosa) {
        tarea.evaluar(this, estudiante, learningPath, calificacionObtenida, exitosa);
    }

    // Método para crear un Learning Path
    public void crearLearningPath(String titulo, Nivel nivelDificultad, String descripcion, String objetivos, 
                                  int duracionMinutos, float rating, List<Actividad> listaActividades) throws IllegalStateException {
        if (titulo == null || nivelDificultad == null || descripcion == null || objetivos == null || duracionMinutos <= 0 || rating < 0 || rating > 5) {
            throw new IllegalStateException("Los datos ingresados no son válidos.");
        }

        boolean tieneActividadObligatoria = listaActividades.stream().anyMatch(Actividad::esObligatoria);
        if (!tieneActividadObligatoria) {
            throw new IllegalStateException("El Learning Path debe contener al menos una actividad obligatoria.");
        }

        LearningPath learningPath = new LearningPath(titulo, nivelDificultad, descripcion, objetivos, duracionMinutos, this, rating, listaActividades);
        System.out.println("Learning Path creado exitosamente: " + learningPath.getTitulo());
    }

    // Método para crear actividades en el Learning Path
    public void crearActividad(LearningPath learningPathActual, String descripcion, Nivel nivelDificultad, String objetivo, 
                               int duracionEsperada, double version, LocalDateTime fechaLimite, Status status, 
                               Obligatoria obligatoria, String tipo, Profesor creador, 
                               List<Actividad> actividadesPreviasSugeridas, List<Actividad> actividadesSeguimientoRecomendadas, 
                               List<PreguntaAbierta> listaPreguntasAbiertas, List<PreguntaCerrada> listaPreguntasCerradas, 
                               String submissionMethod, double calificacionMinima, String tipoRecurso, List<Pregunta> listaPreguntas) {

        Actividad nuevaActividad = null;

        switch (tipo.toUpperCase()) {
            case "TAREA":
                nuevaActividad = new Tarea(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite, 
                                           status, obligatoria, submissionMethod, creador, actividadesPreviasSugeridas, 
                                           actividadesSeguimientoRecomendadas);
                break;

            case "ENCUESTA":
                if (listaPreguntasAbiertas == null || listaPreguntasAbiertas.isEmpty()) {
                    throw new IllegalArgumentException("La encuesta debe tener preguntas abiertas.");
                }
                nuevaActividad = new Encuesta(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite, 
                                              status, obligatoria, creador, new ArrayList<>(listaPreguntasAbiertas));
                break;

                case "EXAMEN":
                    if (listaPreguntas == null || listaPreguntas.isEmpty()) {
                        throw new IllegalArgumentException("El examen debe tener al menos una pregunta.");
                    }
                    nuevaActividad = new Examen(descripcion, nivelDificultad, objetivo, duracionEsperada, version, 
                                                fechaLimite, status, obligatoria, new ArrayList<>(listaPreguntas), calificacionMinima, 
                                                creador, actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
                    break;

            case "QUIZ":
                if (listaPreguntasCerradas == null || listaPreguntasCerradas.isEmpty()) {
                    throw new IllegalArgumentException("El quiz debe tener preguntas cerradas.");
                }
                nuevaActividad = new Quiz(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite, 
                                          status, obligatoria, new ArrayList<>(listaPreguntasCerradas), calificacionMinima, creador, 
                                          actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
                break;

            case "RECURSO EDUCATIVO":
                nuevaActividad = new RecursoEducativo(descripcion, nivelDificultad, objetivo, duracionEsperada, version, 
                                                      fechaLimite, status, obligatoria, tipoRecurso, creador, 
                                                      actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
                break;

            default:
                throw new IllegalStateException("Tipo de actividad no válido.");
        }

        // Agregar la nueva actividad al Learning Path
        if (nuevaActividad != null) {
            learningPathActual.agregarActividad(nuevaActividad);
            System.out.println("Actividad " + tipo + " agregada al Learning Path: " + learningPathActual.getTitulo());
        }
    }

}
    // Método
