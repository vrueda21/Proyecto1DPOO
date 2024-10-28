package actividad;

import usuario.Estudiante;

import java.time.LocalDateTime;

public class RecursoEducativo extends Actividad {

    protected String tipoRecurso;
    protected Status completada;

    public RecursoEducativo(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, 
                            double version, LocalDateTime fechaLimite, Status status, Obligatoria obligatoria, String tipoRecurso) {
        super(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite, status, obligatoria, "recurso educativo");
        this.tipoRecurso = tipoRecurso;
        this.completada = Status.Incompleto;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public void marcarRevisado(Estudiante estudiante) {
        if (estudiante != null) {
            this.completada = Status.Completado;
            System.out.println("El recurso educativo fue marcado como revisado por: " + estudiante.getNombre());
        } else {
            throw new SecurityException("Un estudiante debe marcar el recurso educativo como revisado.");
        }
    }

    public Status getCompletada() {
        return completada;
    }
}
