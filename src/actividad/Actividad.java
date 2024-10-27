package actividad;
import java.time.*;

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

    public Actividad(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, double version, LocalDateTime fechaLimite, Status status) throws IllegalArgumentException{



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
        this.status=status;


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

    

}
