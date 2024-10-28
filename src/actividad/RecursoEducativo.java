package actividad;

import java.time.LocalDateTime;

public class RecursoEducativo extends Actividad{

    protected String tipoRecurso;

    public RecursoEducativo(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, double version, LocalDateTime fechaLimite, Status status, String tipoRecurso, Obligatoria obligatoria){

        super(descripcion, nivelDificultad, objetivo, duracionEsperada, version, fechaLimite, status, obligatoria, "recursoEducativo");
        this.tipoRecurso = tipoRecurso;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

}
