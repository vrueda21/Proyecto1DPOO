package LPRS;
import actividad.*;
import usuario.*;
import java.time.LocalDateTime;
import java.util.*;


public class LearningPath {

    protected String titulo;
    protected Nivel nivelDificultad;
    protected String descripcion;
    protected String objetivos;
    protected int duracionMinutos;
    protected LocalDateTime fechaCreacion;
    protected LocalDateTime fechaModificacion;
    protected int version;
    protected Status status;
    protected List<Actividad> listaActividades;
    protected Profesor creador;


    public LearningPath(String titulo, Nivel nivelDificultad, String descripcion, String objetivos, int duracionMinutos, Profesor creador){

        this.titulo=titulo;
        this.nivelDificultad=nivelDificultad;
        this.descripcion=descripcion;
        this.objetivos= objetivos;
        this.duracionMinutos=duracionMinutos;
        this.fechaCreacion=LocalDateTime.now();
        this.fechaModificacion=null;
        this.version=1;
        this.status=Status.Incompleto;
        this.listaActividades = new ArrayList<>();
        this.creador=creador;

    }


    public String getTitulo() {
        return titulo;
    }


    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public Nivel getNivelDificultad() {
        return nivelDificultad;
    }


    public void setNivelDificultad(Nivel nivelDificultad) {
        this.nivelDificultad = nivelDificultad;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getObjetivos() {
        return objetivos;
    }


    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }


    public int getDuracionMinutos() {
        return duracionMinutos;
    }


    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }


    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }


    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }


    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }


    public int getVersion() {
        return version;
    }


    public void setVersion() {
        this.version +=1;
    }


    public Status getStatus() {
        return status;
    }


    public void setStatus(Status status) {
        this.status = status;
    }


    public List<Actividad> getListaActividades() {
        return listaActividades;
    }

    public void agregarActividad(Actividad actividad) {
        if (listaActividades.contains(actividad)) {
            throw new IllegalArgumentException("La actividad ya está en la lista de actividades del Learning Path.");
        } else {
            listaActividades.add(actividad);
            this.fechaModificacion=LocalDateTime.now();
            setVersion();
        }
    }
}
