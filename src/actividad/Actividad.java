package actividad;

import java.time.*;
import java.util.List;
import java.util.ArrayList;

import usuario.Usuario;
import usuario.Administrador;
import usuario.Estudiante;
import usuario.Profesor;
import LPRS.LearningPath; // Asegúrate de tener esta clase en tu proyecto

public abstract class Actividad {

    protected String descripcion;
    protected Nivel nivelDificultad;
    protected String objetivo;
    protected int duracionEsperada;
    protected double version;
    protected LocalDateTime fechaLimite;
    protected LocalDateTime fechaInicio;
    protected Status status;
    protected Obligatoria obligatoria;
    protected String tipo;
    protected Profesor creador;
    protected List<Actividad> actividadesPreviasSugeridas;
    protected List<Actividad> actividadesSeguimientoRecomendadas;

    // Atributo para el Learning Path asociado
    protected LearningPath learningPathAsociado;

    public Actividad(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, double version, 
                     LocalDateTime fechaLimite, Status status, Obligatoria obligatoria, String tipo, Profesor creador, 
                     List<Actividad> actividadesPreviasSugeridas, List<Actividad> actividadesSeguimientoRecomendadas, 
                     LearningPath learningPathAsociado) {

        this.fechaInicio = LocalDateTime.now();
        this.descripcion = descripcion;
        this.nivelDificultad = nivelDificultad;
        this.objetivo = objetivo;
        this.duracionEsperada = duracionEsperada;
        this.version = version;

        if (fechaLimite.isBefore(this.fechaInicio)) {
            throw new IllegalArgumentException("La fecha límite no puede ser anterior a la fecha de inicio.");
        }

        this.fechaLimite = fechaLimite;
        this.status = Status.Incompleto;
        this.obligatoria = obligatoria;
        this.tipo = tipo;
        this.creador = creador;

        this.actividadesPreviasSugeridas = (actividadesPreviasSugeridas != null) ? actividadesPreviasSugeridas : new ArrayList<>();
        this.actividadesSeguimientoRecomendadas = (actividadesSeguimientoRecomendadas != null) ? actividadesSeguimientoRecomendadas : new ArrayList<>();
        
        // Asignar el Learning Path asociado
        this.learningPathAsociado = learningPathAsociado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Nivel getNivelDificultad() {
        return nivelDificultad;
    }

    public void setNivelDificultad(Nivel nivelDificultad) {
        this.nivelDificultad = nivelDificultad;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public int getDuracionEsperada() {
        return duracionEsperada;
    }

    public void setDuracionEsperada(int duracionEsperada) {
        this.duracionEsperada = duracionEsperada;
    }

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public LocalDateTime getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDateTime fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Obligatoria getObligatoria() {
        return obligatoria;
    }

    public void setObligatoria(Obligatoria obligatoria) {
        this.obligatoria = obligatoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Profesor getCreador() {
        return creador;
    }

    public LearningPath getLearningPathAsociado() {
        return learningPathAsociado;
    }

    // Verificar si hay estudiantes inscritos en el Learning Path asociado
    private boolean hayEstudiantesInscritosEnLP() {
        return learningPathAsociado != null && learningPathAsociado.hayEstudiantesInscritos();
    }

    // Método para verificar si el usuario tiene permiso para modificar la actividad
    private boolean tienePermisoModificar(Usuario usuario) {
        return (usuario instanceof Profesor && usuario.equals(creador)) || usuario instanceof Administrador;
    }

    // Metodos para actividades previas sugeridas

    public void agregarActividadPreviaSugerida(Actividad actividad, Usuario usuario) {
        if (hayEstudiantesInscritosEnLP()) {
            throw new UnsupportedOperationException("No se pueden modificar actividades previas si hay estudiantes inscritos en el Learning Path.");
        }

        if (tienePermisoModificar(usuario)) {
            if (actividad != null && !actividadesPreviasSugeridas.contains(actividad)) {
                actividadesPreviasSugeridas.add(actividad);
                System.out.println("Actividad previa sugerida agregada por: " + usuario.getNombre());
            }
        } else {
            throw new SecurityException("Solo el creador o un administrador pueden modificar actividades previas sugeridas.");
        }
    }

    public List<Actividad> getActividadesPreviasSugeridas() {
        return actividadesPreviasSugeridas;
    }

    public void eliminarActividadPreviaSugerida(Actividad actividad, Usuario usuario) {
        if (hayEstudiantesInscritosEnLP()) {
            throw new UnsupportedOperationException("No se pueden modificar actividades previas si hay estudiantes inscritos en el Learning Path.");
        }

        if (tienePermisoModificar(usuario)) {
            actividadesPreviasSugeridas.remove(actividad);
            System.out.println("Actividad previa sugerida eliminada por: " + usuario.getNombre());
        } else {
            throw new SecurityException("Solo el creador o un administrador pueden eliminar actividades previas sugeridas.");
        }
    }

    public void verificarActividadesPrevias(Estudiante estudiante) {
        if (!actividadesPreviasSugeridas.isEmpty()) {
            System.out.println("Advertencia: Hay actividades previas sugeridas asociadas a esta actividad");
        }
    }

    // Métodos para actividades de seguimiento recomendadas

    public void agregarActividadSeguimiento(Actividad actividad, Usuario usuario) {
        if (hayEstudiantesInscritosEnLP()) {
            throw new UnsupportedOperationException("No se pueden modificar actividades de seguimiento si hay estudiantes inscritos en el Learning Path.");
        }

        if (tienePermisoModificar(usuario)) {
            if (actividad != null && !actividadesSeguimientoRecomendadas.contains(actividad)) {
                actividadesSeguimientoRecomendadas.add(actividad);
                System.out.println("Actividad de seguimiento recomendada agregada por: " + usuario.getNombre());
            }
        } else {
            throw new SecurityException("Solo el creador o un administrador pueden modificar actividades de seguimiento.");
        }
    }

    public List<Actividad> getActividadesSeguimientoRecomendadas() {
        return actividadesSeguimientoRecomendadas;
    }

    public void eliminarActividadSeguimiento(Actividad actividad, Usuario usuario) {
        if (hayEstudiantesInscritosEnLP()) {
            throw new UnsupportedOperationException("No se pueden modificar actividades de seguimiento si hay estudiantes inscritos en el Learning Path.");
        }

        if (tienePermisoModificar(usuario)) {
            actividadesSeguimientoRecomendadas.remove(actividad);
            System.out.println("Actividad de seguimiento recomendada eliminada por: " + usuario.getNombre());
        } else {
            throw new SecurityException("Solo el creador o un administrador pueden eliminar actividades de seguimiento.");
        }
    }
}
