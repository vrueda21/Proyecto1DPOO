package actividad;

import usuario.Estudiante;
import usuario.Profesor;
import java.time.LocalDateTime;
import java.util.List;


public class RecursoEducativo extends Actividad {

    protected String tipoRecurso;

    public RecursoEducativo(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, 
                            double version, LocalDateTime fechaLimite, Status status, Obligatoria obligatoria, 
                            String tipoRecurso, Profesor creador, List<Actividad> actividadesPreviasSugeridas, 
                            List<Actividad> actividadesSeguimientoRecomendadas) {
        super(descripcion, nivelDificultad, objetivo, duracionEsperada, version, 
              fechaLimite, status, obligatoria, "recurso educativo", creador, 
              actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
        this.tipoRecurso = tipoRecurso;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    // Método para marcar el recurso como revisado por un estudiante
    public void marcarRevisado(Estudiante estudiante) {
        if (estudiante == null) {
            throw new SecurityException("Se requiere un estudiante para marcar el recurso educativo como revisado.");
        }
        if (this.status == Status.Completado) {
            throw new UnsupportedOperationException("El recurso educativo ya ha sido marcado como revisado y no se puede repetir.");
        }
        this.status = Status.Completado;
        System.out.println("El recurso educativo fue marcado como revisado por: " + estudiante.getNombre());
    }

    @Override
    public void setStatus(Status status) {
        throw new UnsupportedOperationException("El estado del recurso educativo solo puede cambiar a Completado al marcarlo como revisado.");
    }
}
