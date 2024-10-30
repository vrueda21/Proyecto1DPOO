package usuario;
import java.time.LocalDateTime;
import java.util.*;

import LPRS.LearningPath;
import actividad.*;
import pregunta.Pregunta; // Replace 'somePackage' with the actual package name where Pregunta is located

public class Profesor extends Usuario{

    public Profesor(String nombre, String contrasenia, String correo){

        super(nombre, contrasenia, "profesor", correo);
    }


    public void marcarTareaExitosa(Tarea tarea){

        //tarea.marcarExitosa(this);

    }  

    public void marcarTareaFallida(Tarea tarea){

        //tarea.marcarFallida(this);

    }

    public void evaluarTarea(Tarea tarea, boolean exitosa){

        //tarea.evaluarTarea(this, exitosa);

    }

    public void crearLearningPath(String titulo, Nivel nivelDificultad, String descripcion, String objetivos, int duracionMinutos, float rating, List<Actividad> listaActividades) throws IllegalStateException {
        if (titulo == null || nivelDificultad == null || descripcion == null || objetivos == null || duracionMinutos <= 0 || rating < 0 || rating > 5) {
            throw new IllegalStateException("Los datos ingresados no son válidos.");
        }
        boolean tieneActividadObligatoria = false;
        for (Actividad i: listaActividades){
            if (i.esObligatoria()) {  
                tieneActividadObligatoria = true;
                break;
            }
        }
            if (!tieneActividadObligatoria) {
                throw new IllegalStateException("El Learning Path debe contener al menos una actividad obligatoria.");
            }
        
        LearningPath learningPath = new LearningPath(titulo, nivelDificultad, descripcion, objetivos, duracionMinutos, this, rating);

    }

    public void crearActividad(LearningPath learningPathActual, String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, double version, LocalDateTime fechaLimite, Status status, Obligatoria obligatoria, String tipo, Profesor creador, List<Actividad> actividadesPreviasSugeridas, List<Actividad> actividadesSeguimientoRecomendadas, List<Pregunta> listaPreguntas, String submissionMethod, double calificacionMinima, String tipoRecurso){
        
        if(tipo.toUpperCase() == "TAREA"){
            Tarea tarea = new Tarea(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite, status, obligatoria, submissionMethod, creador, actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
            learningPathActual.agregarActividad(tarea);
        }
        else if(tipo.toUpperCase() == "ENCUESTA"){
            Encuesta encuesta = new Encuesta (descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite, status, obligatoria, tipo, listaPreguntas, this);
            learningPathActual.agregarActividad(encuesta);
        }
        else if(tipo.toUpperCase() == "EXAMEN"){
            Examen examen = new Examen(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite, status, obligatoria, tipo, listaPreguntas, this);
            learningPathActual.agregarActividad(examen);
        }
        else if(tipo.toUpperCase() == "Quiz"){
            Quiz quiz = new Quiz(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite, status, obligatoria, listaPreguntas, calificacionMinima, creador, actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
            learningPathActual.agregarActividad(quiz);
        }
        else if(tipo.toUpperCase() == "RECURSO EDUCATIVO"){
            RecursoEducativo recursoEducativo = new RecursoEducativo(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite, status, obligatoria, tipoRecurso, creador, actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
            learningPathActual.agregarActividad(recursoEducativo);
        }
        else{
            throw new IllegalStateException("Tipo de actividad no válido.");
        }
        
    }

    public void eliminarActividad(LearningPath learningPathActual, Actividad actividad){
        learningPathActual.eliminarActividad(actividad);
    }

    public Actividad clonarActividad(Actividad actividad, Profesor creador){
        return actividad.clonarActividad();
    }
    

}
