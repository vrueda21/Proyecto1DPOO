
// FALTA ASEGURAR LO DE QUE LOS PROFESORES NO PUEDEN MODIFICAR SI YA HAY ESTUDIANTE SINSCRITOS AL LEARNIGN PATH ASOCIADO AL LEARNING PATH DONDE ESTA LA ACTIVIDAD


package actividad;

import java.time.*;

import java.util.List;

import LPRS.LearningPath;

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

    public Nivel getNivelDificultad() {
        return nivelDificultad;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public int getDuracionEsperada() {
        return duracionEsperada;
    }

    public double getVersion() {
        return version;
    }

    public LocalDateTime getFechaLimite() {
        return fechaLimite;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
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

    public String getTipo() {
        return tipo;
    }

    public Profesor getCreador() {
        return creador;
    }

    // Metodos


    // Metodo de verificacion para todos los metodos relacionados con la modifiacion de actividades

    private boolean tienePermisoModificar(Usuario usuario) {
        return (usuario instanceof Profesor && usuario.equals(creador)) || usuario instanceof Administrador; // Solo profesores y administradores pueden modificar actividades
    }




    // Metodos para el sistema de completar y respuesta de estudiantes con respecto a las actividades

    // METODO 1 - RESPONDER (SOLO PARA ESTUDIANTES), la idea es que cada subclase de actividad tenga su propio metodo responder, a partir de esto obtendremoos las respuestas que se necesiten para las subclases, y estas se mandaran al siguiente metodo:

    // Declaramos el metodo responder, que sera implementado en las subclases de actividad

    public abstract void responder(Estudiante estudiante, String respuesta); // Habra un atributo respuesta que sera String pero se adaptara a las necesidades de cada subclase de actividad

    // METODO 2 - EXITOSA (SOLO PARA ESTUDIANTES), verificar si la actividad fue exitosa o no, a partir de la respuesta obtenida en el metodo responder, y se mandara al siguiente metodo en el caso de que no se pueda automatizar, como en el caso de las actividades que tengan preguntas de tipo abierta
    public abstract void esExitosa(Estudiante estudiante);
    // METODO 3 - EVALUAR (SOLO PARA PROFESORES), la  idea es que cada subclase de actividad tenga su propio metodo evaluar, a partir de esto se obtendra la calificacion final de la actividad, y se marcara como exitosa o no dependiendo de la calificacion obtenida. En caso de que la actividad no se completa exitosamente, siguiente metodo:
    public abstract void evaluar(Profesor profesor, Estudiante estudiante, double calificacionObtenida, boolean exitosa);
    // METODO 4 - REINTENTAR (SOLO PARA ESTUDIANTES), la idea es que cada subclase de actividad tenga su propio metodo reintentar, a partir de esto se podra reintentar la actividad, y se podra completar nuevamente, en caso de que se complete exitosamente, se podra evaluar nuevamente, para que haya un ciclo de reintentos hasta que se complete exitosamente para la misma actividad, antes de continuar con otra
    public abstract void reintentar(Estudiante estudiante);
    // METODO 5 FINAL - PERSISTENCIA - GUARDAR EN BASE DE DATOS, la idea es que cada subclase de actividad tenga su propio metodo guardar en base de datos, a partir de esto se guardara la actividad en la base de datos, y se podra recuperar en caso de que se necesite, para que se pueda continuar con la actividad en otro momento, o se pueda revisar en caso de que se necesite. Su funcion principal deberia permitir que los profesores puedan revisar las respuestas que son marcadas como enviadas, para que luego ellos las evaluen, y puedan marcarlas como exitosas o no exitosas, y que los estudiantes puedan reintentar las actividades que no completaron exitosamente, y que puedan completarlas exitosamente, y que puedan ver sus calificaciones obtenidas en las actividades completadas exitosamente.
    
    // Metodos para modificacion de las actividades en si
    // Método para agregar una actividad previa sugerida en la clase Actividad
    public void agregarActividadPreviaSugerida(Actividad actividad, Usuario usuario, LearningPath learningPath) {
        if (learningPath.verificarSiHayInscritos()) {
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

    public void eliminarActividadPreviaSugerida(Actividad actividad, Usuario usuario, LearningPath learningPath) {
        if (learningPath.verificarSiHayInscritos()) {
            throw new UnsupportedOperationException("No se pueden modificar actividades previas si hay estudiantes inscritos en el Learning Path.");
        }

        if (tienePermisoModificar(usuario)) {
            actividadesPreviasSugeridas.remove(actividad);
            System.out.println("Actividad previa sugerida eliminada por: " + usuario.getNombre());
        } else {
            throw new SecurityException("Solo el creador o un administrador pueden eliminar actividades previas sugeridas.");
        }
    }

    // Actividades de seguimiento recomendadas
    public void agregarActividadSeguimiento(Actividad actividad, Usuario usuario, LearningPath learningPath) {
        if (learningPath.verificarSiHayInscritos()) {
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

    public void eliminarActividadSeguimiento(Actividad actividad, Usuario usuario, LearningPath learningPath) {
        if (learningPath.verificarSiHayInscritos()) {
            throw new UnsupportedOperationException("No se pueden modificar actividades de seguimiento si hay estudiantes inscritos en el Learning Path.");
        }

        if (tienePermisoModificar(usuario)) {
            actividadesSeguimientoRecomendadas.remove(actividad);
            System.out.println("Actividad de seguimiento recomendada eliminada por: " + usuario.getNombre());
        } else {
            throw new SecurityException("Solo el creador o un administrador pueden eliminar actividades de seguimiento.");
        }
    }

    // Métodos de modificación de atributos clave (Setters con verificación de inscripciones en Learning Path)
    public void setDescripcion(String descripcion, LearningPath learningPath) {
        if (learningPath.verificarSiHayInscritos()) {
            throw new UnsupportedOperationException("No se puede modificar la descripción si hay estudiantes inscritos en el Learning Path.");
        }
        this.descripcion = descripcion;
    }

    public void setNivelDificultad(Nivel nivelDificultad, LearningPath learningPath) {
        if (learningPath.verificarSiHayInscritos()) {
            throw new UnsupportedOperationException("No se puede modificar el nivel de dificultad si hay estudiantes inscritos en el Learning Path.");
        }
        this.nivelDificultad = nivelDificultad;
    }

    public void setObjetivo(String objetivo, LearningPath learningPath) {
        if (learningPath.verificarSiHayInscritos()) {
            throw new UnsupportedOperationException("No se puede modificar el objetivo si hay estudiantes inscritos en el Learning Path.");
        }
        this.objetivo = objetivo;
    }

    public void setDuracionEsperada(int duracionEsperada, LearningPath learningPath) {
        if (learningPath.verificarSiHayInscritos()) {
            throw new UnsupportedOperationException("No se puede modificar la duración esperada si hay estudiantes inscritos en el Learning Path.");
        }
        this.duracionEsperada = duracionEsperada;
    }


    public void setFechaLimite(LocalDateTime fechaLimite, LearningPath learningPath) {
        if (learningPath.verificarSiHayInscritos()) {
            throw new UnsupportedOperationException("No se puede modificar la fecha límite si hay estudiantes inscritos en el Learning Path.");
        }
        if (fechaLimite.isBefore(this.fechaInicio)) {
            throw new IllegalArgumentException("La nueva fecha límite no puede ser anterior a la fecha de inicio.");
        }
        this.fechaLimite = fechaLimite;
    }

    public void setVersion(double version, LearningPath learningPath) {
        if (learningPath.verificarSiHayInscritos()) {
            throw new UnsupportedOperationException("No se puede modificar la versión si hay estudiantes inscritos en el Learning Path.");
        }
        this.version = version;
    }

    public void setObligatoria(Obligatoria obligatoria, LearningPath learningPath) {
        if (learningPath.verificarSiHayInscritos()) {
            throw new UnsupportedOperationException("No se puede modificar si la actividad es obligatoria u opcional si hay estudiantes inscritos en el Learning Path.");
        }
        this.obligatoria = obligatoria;
    }

    public void setFechaInicio(LocalDateTime fechaInicio, LearningPath learningPath) {
        if (learningPath.verificarSiHayInscritos()) {
            throw new UnsupportedOperationException("No se puede modificar la fecha de inicio si hay estudiantes inscritos en el Learning Path.");
        }
        this.fechaInicio = fechaInicio;
    }

    public void setTipo(String tipo, LearningPath learningPath) {
        if (learningPath.verificarSiHayInscritos()) {
            throw new UnsupportedOperationException("No se puede modificar el tipo de la actividad si hay estudiantes inscritos en el Learning Path.");
        }
        this.tipo = tipo;
    }

    public boolean esObligatoria(){
        return obligatoria.equals(Obligatoria.SI);
    }


}
