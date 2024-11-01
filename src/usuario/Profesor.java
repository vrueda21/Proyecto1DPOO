package usuario;

import java.time.LocalDateTime;
import java.util.*;

import LPRS.LearningPath;
import actividad.*;
import pregunta.PreguntaAbierta;
import pregunta.PreguntaCerrada;
import pregunta.Pregunta;

public class Profesor extends Usuario {
    private List<LearningPath> learningPathCreado;
    private List<Estudiante> estudiantes;

    public Profesor(String nombre, String contrasena, String correo, List<LearningPath> learningPathCreado, List<Estudiante> estudiantes) {
        super(nombre, contrasena, "profesor",correo);
        this.learningPathCreado = learningPathCreado;
        this.estudiantes = estudiantes;
    }

    // Getters y Setters

    public List<LearningPath> getLearningPathCreado() {
        return learningPathCreado;
    }

    public void setLearningPathCreado(List<LearningPath> learningPathCreado) {
        // Verificar que la lista de Learning Paths no sea nula

        if (learningPathCreado == null) {
            throw new IllegalArgumentException("La lista de Learning Paths no puede ser nula.");
        }
        this.learningPathCreado = learningPathCreado;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        // Verificar que la lista de estudiantes no sea nula

        if (estudiantes == null) {
            throw new IllegalArgumentException("La lista de estudiantes no puede ser nula.");
        }
        this.estudiantes = estudiantes;
    }

    
    // Método para evaluar la tarea
    public void evaluarTarea(Tarea tarea, Estudiante estudiante, LearningPath learningPath, double calificacionObtenida, boolean exitosa) {
        tarea.evaluar(this, estudiante, learningPath, calificacionObtenida, exitosa);
        System.out.println("La tarea fue evaluada por el profesor: " + this.getNombre());
    }

    // Método para evaluar el examen
    public void evaluarExamen(Examen examen, Estudiante estudiante, LearningPath learningPath, double calificacionObtenida, boolean exitosa) {
        examen.evaluar(this, estudiante, learningPath, calificacionObtenida, exitosa);
        System.out.println("El examen fue evaluado por el profesor: " + this.getNombre());
    }

    // Método para evaluar el quiz (aunque no requiere evaluación directa, podemos verificar su estado)
    public void revisarQuiz(Quiz quiz, Estudiante estudiante) {
        if (quiz.esExitosa(estudiante)) {
            System.out.println("El quiz ha sido completado exitosamente.");
        } else {
            System.out.println("El quiz no ha sido aprobado.");
        }
    }

    // Método para revisar la encuesta (aunque no se evalúa directamente)
    public void revisarEncuesta(Encuesta encuesta, Estudiante estudiante) {
        if (encuesta.esExitosa(estudiante)) {
            System.out.println("La encuesta ha sido completada por el estudiante: " + estudiante.getNombre());
        } else {
            System.out.println("La encuesta no ha sido completada.");
        }
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

        // Agregar el Learning Path a la lista de Learning Paths creados por el profesor
        this.learningPathCreado.add(learningPath);
    }

// Método para crear actividades en el Learning Path (Se adapto del que ya se hizo para la persistencia)

public void crearActividad(LearningPath learningPath, String descripcion, Nivel nivelDificultad, String objetivo,
                           int duracionEsperada, double version, LocalDateTime fechaLimite, Status status,
                           Obligatoria obligatoria, String tipo, List<Actividad> actividadesPreviasSugeridas,
                           List<Actividad> actividadesSeguimientoRecomendadas, ArrayList<PreguntaAbierta> listaPreguntasAbiertas,
                           List<PreguntaCerrada> listaPreguntasCerradas, String submissionMethod,
                           double calificacionMinima, String tipoRecurso, List<Pregunta> listaPreguntas) {

    Actividad nuevaActividad;

    Profesor creador = this;

    switch (tipo.toUpperCase()) {
        case "TAREA":
            // Creación de la Tarea
            nuevaActividad = new Tarea(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite,
                                       status, obligatoria, submissionMethod, creador, actividadesPreviasSugeridas,
                                       actividadesSeguimientoRecomendadas);
            break;

        case "ENCUESTA":
            // Verificación de preguntas abiertas para Encuesta
            if (listaPreguntasAbiertas == null || listaPreguntasAbiertas.isEmpty()) {
                throw new IllegalArgumentException("La encuesta debe tener preguntas abiertas.");
            }
            nuevaActividad = new Encuesta(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite,
                                          status, obligatoria, actividadesPreviasSugeridas,
                                          actividadesSeguimientoRecomendadas, creador, listaPreguntasAbiertas);
            break;

        case "EXAMEN":
            // Verificación de preguntas para Examen
            if (listaPreguntas == null || listaPreguntas.isEmpty()) {
                throw new IllegalArgumentException("El examen debe tener al menos una pregunta.");
            }
            nuevaActividad = new Examen(descripcion, nivelDificultad, objetivo, duracionEsperada, version,
                                        fechaLimite, status, obligatoria, listaPreguntas, calificacionMinima,
                                        creador, actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
            break;

        case "QUIZ":
            // Verificación de preguntas cerradas para Quiz
            if (listaPreguntasCerradas == null || listaPreguntasCerradas.isEmpty()) {
                throw new IllegalArgumentException("El quiz debe tener preguntas cerradas.");
            }
            nuevaActividad = new Quiz(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite,
                                      status, obligatoria, listaPreguntasCerradas, calificacionMinima, creador,
                                      actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
            break;

        case "RECURSO EDUCATIVO":
            // Verificación de tipoRecurso para Recurso Educativo
            if (tipoRecurso == null || tipoRecurso.isEmpty()) {
                throw new IllegalArgumentException("El tipo de recurso no puede estar vacío.");
            }
            nuevaActividad = new RecursoEducativo(descripcion, nivelDificultad, objetivo, duracionEsperada, version,
                                                  fechaLimite, status, obligatoria, tipoRecurso, creador,
                                                  actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
            break;

        default:
            throw new IllegalStateException("Tipo de actividad no válido: " + tipo);
    }

    // Agregar la nueva actividad al Learning Path
    learningPath.agregarActividad(nuevaActividad);
    System.out.println("Actividad " + tipo + " agregada al Learning Path: " + learningPath.getTitulo());
}


// Metodo para agregar estudiantes a la lista del profesor si estan inscritos en el learning path creado por el profesor o actividad

public void agregarEstudianteSiCorresponde(LearningPath learningPath, Estudiante estudiante, Profesor profesor) {
    if (learningPath == null) {
        throw new IllegalArgumentException("El Learning Path no puede ser nulo.");
    }
    if (estudiante == null) {
        throw new IllegalArgumentException("El estudiante no puede ser nulo.");
    }
    if (!learningPath.getCreador().equals(profesor)) {
        throw new IllegalStateException("El profesor no es el creador del Learning Path.");
    }
    if (!learningPath.verificarSiInscrito(estudiante)) {
        throw new IllegalStateException("El estudiante no está inscrito en el Learning Path.");
    }
    agregarEstudiante(estudiante);
}

public void agregarEstudiante(Estudiante estudiante) {
    if (estudiante == null) {
        throw new IllegalArgumentException("El estudiante no puede ser nulo.");
    }
    if (estudiantes.contains(estudiante)) {
        throw new IllegalStateException("El estudiante ya está inscrito en la lista.");
    }
    estudiantes.add(estudiante);
}




}
