package usuario;
import plataforma.*;
import actividad.*;
import LPRS.LearningPath;
import java.util.*;

public class Estudiante extends Usuario{

    protected Actividad actividadActual;
    protected LearningPath learningPathActual;
    protected List<LearningPath> listaLearningPathsCompletados;
    protected List<Actividad> listaActividadesCompletadas;

    public Estudiante(String nombre, String contrasenia, String correo){

        super(nombre, contrasenia, "estudiante", correo);
        this.actividadActual=null;
        this.learningPathActual=null;
        this.listaActividadesCompletadas = new ArrayList<>();
    }

    public void prueba(){
        Administrador administrador = Administrador.getAdmin(); //LLamar a administrador

    }
    public Actividad getActividadActual() {
        return actividadActual;
    }

    public void setActividadActual(Actividad actividadActual) {
        this.actividadActual = actividadActual;
    }

    public List<Actividad> getListaActividadesCompletadas() {
        return listaActividadesCompletadas;
    }
    

    public LearningPath getLearningPathActual() {
        return learningPathActual;
    }

    public void setLearningPathActual(LearningPath learningPathActual) {
        this.learningPathActual = learningPathActual;
    }

    public static boolean registrarEstudiante(String nombre, String correo, String contrasenia) {
		
        Estudiante estudiante = new Estudiante(nombre, correo, contrasenia);
		Plataforma plataforma = Plataforma.getPlataforma();
		return plataforma.agregarEstudiante(estudiante);
	}

    public void marcarCompletada(Tarea tarea){

        tarea.marcarCompletada(this);
        listaActividadesCompletadas.add(this.actividadActual);
        this.actividadActual=null;

    }

    public Actividad comenzarActividad(Actividad actividad) throws IllegalStateException{
        if (actividadActual==null){
            setActividadActual(actividad);
            return actividadActual;
        }

        else{
            throw new IllegalStateException("No se puede comenzar una actividad nueva porque hay una en progreso.");
        }
        
    }

    public void agregarLearningPathCompletado(LearningPath learningPath){


    }
    
}
