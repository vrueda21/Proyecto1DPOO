package actividad;
import usuario.Profesor;
import usuario.Estudiante;

import java.time.LocalDateTime;

public class Tarea extends Actividad{

    protected String submissionMethod;
    protected Status completada;

    public Tarea(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, double version, LocalDateTime fechaLimite, Status status, String submissionMethod, Obligatoria obligatoria){

        super(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite, status, obligatoria, "tarea");
        this.submissionMethod="NO ENVIADA";
        this.completada=Status.Incompleto;

    }

    public String getSubmissionMethod() {
        return submissionMethod;
    }

    public void setSubmissionMethod(String submissionMethod) {
        this.submissionMethod = submissionMethod;
    }
    public void marcarCompletada(Estudiante estudiante) throws SecurityException{
        if (estudiante != null) {  
            this.completada = Status.Completado;
            System.out.println("La tarea fue marcada como completada por: " + estudiante.getNombre());
        } else {
            throw new SecurityException("Un estudiante debe marcar la tarea como completada.");
        }
    }


    public void marcarEnviada(Estudiante estudiante, String submissionMethod) throws SecurityException{
        if (estudiante != null) {  
            this.status = Status.Enviada;
            setSubmissionMethod(submissionMethod);
            System.out.println("La tarea fue marcada como enviada por: " + estudiante.getNombre());
        } else {
            throw new SecurityException("Un estudiante debe marcar la tarea como enviada.");
        }
    }

    public void marcarExitosa(Profesor profesor){
        if (profesor != null) {  // Ensure the teacher is not null
            this.status = Status.Exitosa;
            System.out.println("La tarea fue marcada como exitosa por: " + profesor.getNombre());
        } else {
            throw new SecurityException("Un profesor debe marcar la tarea como exitosa.");
        }

    }

    public Status getCompletada() {
        return completada;
    }
    

}
