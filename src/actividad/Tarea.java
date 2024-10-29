package actividad;

import usuario.Profesor;
import usuario.Estudiante;
import java.time.LocalDateTime;
import java.util.List;

public class Tarea extends Actividad {

    protected String submissionMethod;

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
    public void marcarEnviada(Estudiante estudiante, String submissionMethod) throws SecurityException {
        if (estudiante == null) {
            throw new SecurityException("Se requiere un estudiante para enviar la tarea.");
        }
        if (this.status == Status.Enviada) {
            throw new UnsupportedOperationException("La tarea ya ha sido enviada y no se puede reenviar.");
        }
        this.status = Status.Enviada;
        this.submissionMethod = submissionMethod;
        System.out.println("La tarea fue marcada como enviada por: " + estudiante.getNombre());
    }

    // Método para que el profesor evalúe la tarea
    public void evaluarTarea(Profesor profesor, boolean exitosa) {
        if (profesor == null || !profesor.equals(creador)) {
            throw new SecurityException("Solo el profesor creador puede evaluar la tarea.");
        }
        this.status = exitosa ? Status.Exitosa : Status.noExitosa;
        System.out.println("La tarea fue marcada como " + (exitosa ? "exitosa" : "fallida") + " por el profesor: " + profesor.getNombre());
    }

    // Sobrescribir setStatus para evitar cambios directos al estado
    @Override
    public void setStatus(Status status) {
        throw new UnsupportedOperationException("El estado de la tarea solo puede ser cambiado a través de acciones específicas (enviada, completada, evaluada).");
    }
}
