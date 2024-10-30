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
    protected float rating;
    protected List<Estudiante> estudiantesInscritos;
    protected float progreso;
    protected List<Actividad> listaActividadesCompletadasConDup;
    protected List<Actividad> listaActividadesCompletadas;


    public LearningPath(String titulo, Nivel nivelDificultad, String descripcion, String objetivos, int duracionMinutos, Profesor creador, float rating, List<Actividad> listaActividades){

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
        this.rating = rating;
        this.estudiantesInscritos = new ArrayList<>();
        this.progreso = 0;
        this.listaActividadesCompletadasConDup = new ArrayList<>();
        this.listaActividadesCompletadas = new ArrayList<>();
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

    public void eliminarActividad(Actividad actividad) {
        if (!listaActividades.contains(actividad)) {
            throw new IllegalArgumentException("La actividad no está en la lista de actividades del Learning Path.");
        } 
        else {
            listaActividades.remove(actividad);
            if (listaActividades.stream().noneMatch(Actividad::esObligatoria)) {
                listaActividades.add(actividad);
                throw new IllegalStateException("El Learning Path debe contener al menos una actividad obligatoria.");
            }
            this.fechaModificacion=LocalDateTime.now();
            setVersion();
        }
    }


    public boolean tieneActividadObligatoria() {
        return listaActividades.stream().anyMatch(actividad -> actividad.esObligatoria());
    }

    public void validarActividadesObligatorias() {
        if (listaActividades.stream().noneMatch(Actividad::esObligatoria)) {
            throw new IllegalStateException("El Learning Path debe contener al menos una actividad obligatoria.");
        }
    }
    public void registrarLearningPath() {
        validarActividadesObligatorias();
        System.out.println("Learning Path validado y listo para su uso.");
    } // registrarLearningPath se debe utilizar cada vez que se cree un learning path de manera que se garantice que haya al menos una actividad obligatoria
    // EJEMPLO: 
    // LearningPath learningPath = new LearningPath("Título", Nivel.MEDIO, "Descripción", "Objetivos", 120, profesor, 4.5f);
    
    // learningPath.agregarActividad(new Actividad("Descripción Actividad", Nivel.FACIL, "Objetivo", 30, 1.0, LocalDateTime.of(2024, 12, 31, 23, 59), Status.INCOMPLETO, Obligatoria.SI, "Tipo de Actividad"));
    
    // learningPath.registrarLearningPath();

    public void inscripcionEstudiante(Estudiante estudiante) {        
        this.estudiantesInscritos.add(estudiante);

    } // inscripcionEstudiante se debe utilizar cada vez que un estudiante se inscriba a un learning path
    // si esta variable es verdadera, no se puede crear, modificar, ni eliminar actividades del learning path

    public boolean verificarSiInscrito(Estudiante estudiante) {
        return this.estudiantesInscritos.contains(estudiante);
    } // verificarSiInscrito se debe utilizar para verificar si un estudiante ya está inscrito en un learning path

    public boolean verificarSiHayInscritos() {
        return !this.estudiantesInscritos.isEmpty();
    } // verificarSiHayInscritos se debe utilizar para verificar si hay estudiantes inscritos en un learning path

    public boolean verificarActividad(Actividad actividad) {
        return this.listaActividades.contains(actividad);
        
    } // verificarActividad se debe utilizar para verificar si una actividad está asociada a un learning path
    public void eliminarInscripcion(Estudiante estudiante) {
        if (!this.estudiantesInscritos.contains(estudiante)) {
            throw new IllegalArgumentException("El estudiante no está inscrito en este Learning Path.");
        }
        this.estudiantesInscritos.remove(estudiante);
    } // eliminarInscripcion se debe utilizar para eliminar la inscripción de un estudiante en un learning path
    // se debe utilizar cada vez que un estudiante finalice el learning path 

    public void actividadObligatoriaCompletada(Actividad actividad) {
        this.listaActividadesCompletadasConDup.add(actividad);
        this.listaActividadesCompletadas = new ArrayList<>(new HashSet<>(this.listaActividadesCompletadasConDup));
    }
    
    public float calcularProgreso(Estudiante estudiante) {
        int totalObligatorias = (int) listaActividades.stream()
            .filter(Actividad::esObligatoria) // Filtrar solo las obligatorias
            .count();
    
        // Contar el número de actividades obligatorias que el estudiante ha completado con éxito
        int completadasObligatorias = (int) listaActividadesCompletadas.stream()
            .filter(a -> a.esObligatoria() && a.esExitosa(estudiante)) // Verificar el éxito para el estudiante
            .count();
    
        this.progreso = totalObligatorias == 0 ? 0 : (float) completadasObligatorias / totalObligatorias * 100;
        if (this.progreso == 100) {
            this.status = Status.Completado;
        }
        
        return progreso;
    }
    

    public float getRating(){
        return rating;
    }

    public void setRating(float rating){
        this.rating = rating;
    }
    
    public List<Estudiante> getEstudiantesInscritos(){
        return estudiantesInscritos;
    }
    
    
}