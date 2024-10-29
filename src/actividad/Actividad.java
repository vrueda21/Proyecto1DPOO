
// FALTA ASEGURAR LO DE QUE LOS PROFESORES NO PUEDEN MODIFICAR SI YA HAY ESTUDIANTE SINSCRITOS AL LEARNIGN PATH ASOCIADO AL LEARNING PATH DONDE ESTA LA ACTIVIDAD


package actividad;

import java.time.*;

import java.util.List;

import java.util.ArrayList;

import usuario.Usuario;
import usuario.Administrador;
import usuario.Estudiante;
import usuario.Profesor;

public abstract class Actividad {

    protected String descripcion;
    protected Nivel nivelDificultad;
    protected String objetivo;
    //actividades previas
    protected int duracionEsperada;
    protected double version;
    protected LocalDateTime fechaLimite;
    protected LocalDateTime fechaInicio;
    protected Status status;
    protected Obligatoria obligatoria;
    protected String tipo;
    // Atributo de creador de la actividad
    protected Profesor creador;
    // Atributo para lista de actividades previas
    protected List<Actividad> actividadesPreviasSugeridas;

    // Lista de actividades de seguimiento recomendas
    protected List<Actividad> actividadesSeguimientoRecomendadas;

    public Actividad(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, double version, LocalDateTime fechaLimite, Status status, Obligatoria obligatoria, String tipo, Profesor creador, List<Actividad> actividadesPreviasSugeridas, List<Actividad> actividadesSeguimientoRecomendadas) throws IllegalArgumentException{

        this.fechaInicio=LocalDateTime.now();
        this.descripcion=descripcion;
        this.nivelDificultad=nivelDificultad;
        this.objetivo=objetivo;
        this.duracionEsperada = duracionEsperada;
        this.version = version;

        if (fechaLimite.isBefore(this.fechaInicio)) {
            throw new IllegalArgumentException("La fecha límite no puede ser anterior a la fecha de inicio.");
        }

        this.fechaLimite=fechaLimite;
        this.status=Status.Incompleto;
        this.obligatoria = obligatoria;
        this.tipo=tipo;
        this.creador = creador;

        this.actividadesPreviasSugeridas = (actividadesPreviasSugeridas != null) ? actividadesPreviasSugeridas : new ArrayList<>();
        this.actividadesSeguimientoRecomendadas = (actividadesSeguimientoRecomendadas != null) ? actividadesSeguimientoRecomendadas : new ArrayList<>();


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

    // Metodos


    // Metodo de verificacion para todos los metodos relacionados con la modifiacion de actividades

    private boolean tienePermisoModificar(Usuario usuario) {
        return (usuario instanceof Profesor && usuario.equals(creador)) || usuario instanceof Administrador; // Solo profesores y administradores pueden modificar actividades
    }


    // Primero empezamos con los metodos asociados al atributo que faltaba que eran las actividades previas y de seguimiento recomendadas

    // Metodos para agregar y eliminar actividades previas y de seguimiento recomendadas, estas solo pueden ser modificadas por profesores

    // Actividades previas sugeridas
    public void agregarActividadPreviaSugerida(Actividad actividad, Usuario usuario) {
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
        if (tienePermisoModificar(usuario)) {
            actividadesPreviasSugeridas.remove(actividad);
            System.out.println("Actividad previa sugerida eliminada por: " + usuario.getNombre());
        } else {
            throw new SecurityException("Solo el creador o un administrador pueden eliminar actividades previas sugeridas.");
        }
    }

    // Metodo para verificar si hay actividades previas sugeridas asociadas a esta actividad, su funcion es simplemente mostrar un mensaje de advertencia al estudiante
    public void verificarActividadesPrevias(Estudiante estudiante){

        if (!actividadesPreviasSugeridas.isEmpty()){
            System.out.println("Advertencia: Hay actividades previas sugeridas asociadas a esta actividad");
        }
    }


    // Actividades de seguimiento recomendadas
    public void agregarActividadSeguimiento(Actividad actividad, Usuario usuario) {
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
        if (tienePermisoModificar(usuario)) {
            actividadesSeguimientoRecomendadas.remove(actividad);
            System.out.println("Actividad de seguimiento recomendada eliminada por: " + usuario.getNombre());
        } else {
            throw new SecurityException("Solo el creador o un administrador pueden eliminar actividades de seguimiento.");
        }
    }

}
