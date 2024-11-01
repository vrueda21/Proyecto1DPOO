package actividad;

import LPRS.LearningPath;
import usuario.Profesor;
import usuario.Estudiante;
import java.time.LocalDateTime;
import java.util.List;

public class Tarea extends Actividad {

    protected String submissionMethod; // Método de entrega (e.g., LMS, correo electrónico, etc.)

    public Tarea(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, 
                 double version, LocalDateTime fechaLimite, Status status, Obligatoria obligatoria, 
                 String submissionMethod, Profesor creador, List<Actividad> actividadesPreviasSugeridas, 
                 List<Actividad> actividadesSeguimientoRecomendadas) {
        super(descripcion, nivelDificultad, objetivo, duracionEsperada, version, 
              fechaLimite, status, obligatoria, "tarea", creador, 
              actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
        this.submissionMethod = submissionMethod;
    }

    public String getSubmissionMethod() {
        return submissionMethod;
    }

    // Método para que el estudiante marque la tarea como enviada
    @Override
    public void responder(Estudiante estudiante, String respuesta) {
        if (estudiante == null) {
            throw new SecurityException("Se requiere un estudiante para enviar la tarea.");
        }

        if (this.status == Status.Enviada) {
            throw new UnsupportedOperationException("La tarea ya ha sido enviada y no se puede reenviar.");
        }

        // La "respuesta" en este caso es el método de entrega (e.g., LMS, correo)
        this.submissionMethod = respuesta;
        this.status = Status.Enviada;
        System.out.println("La tarea fue enviada por: " + estudiante.getNombre() + " usando el método: " + submissionMethod);
    }

    // Verificar si la tarea es exitosa
    @Override
    public boolean esExitosa(Estudiante estudiante) {
        if (this.status == Status.Exitosa) {
            System.out.println("La tarea fue completada exitosamente por: " + estudiante.getNombre());
            return true;
        } else {
            System.out.println("La tarea no ha sido completada exitosamente por: " + estudiante.getNombre());
            return false;
        }
    }

    // **Método para que el profesor evalúe la tarea
    @Override
    public void evaluar(Profesor profesor, Estudiante estudiante, LearningPath learningPath, 
                        double calificacionObtenida, boolean exitosa) {

        // Verificar si el estudiante está inscrito en el LearningPath
        if (!learningPath.verificarSiInscrito(estudiante)) {
            throw new SecurityException("El estudiante no está inscrito en el Learning Path asociado a esta tarea.");
        }

        // Verificar si la tarea está asociada al LearningPath
        if (!learningPath.verificarActividad(this)) {
            throw new IllegalArgumentException("La tarea no está asociada al Learning Path actual.");
        }

        // Verificar si el profesor es el creador de la tarea
        if (profesor == null || !profesor.equals(creador)) {
            throw new SecurityException("Solo el profesor creador puede evaluar la tarea.");
        }

        // Asignar la calificación y el estado según la evaluación
        this.status = exitosa ? Status.Exitosa : Status.noExitosa;
        System.out.println("La tarea fue marcada como " + (exitosa ? "exitosa" : "fallida") + " por el profesor: " + profesor.getNombre());
    }

    // Método para reintentar la tarea
    @Override
    public void reintentar(Estudiante estudiante) {
        if (this.status == Status.Exitosa) {
            throw new UnsupportedOperationException("No se puede reintentar una tarea que ya ha sido completada exitosamente.");
        }

        System.out.println("El estudiante " + estudiante.getNombre() + " está reintentando la tarea.");
        this.status = Status.Incompleto; // Se reinicia el estado de la tarea
    }

}
