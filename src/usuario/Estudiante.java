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
    protected List<Actividad> listaActividadesPorCompletar;

    public Estudiante(String nombre, String contrasenia, String correo){

        super(nombre, contrasenia, "estudiante", correo);
        this.actividadActual=null;
        this.learningPathActual=null;
        this.listaActividadesCompletadas = new ArrayList<>();
        this.listaLearningPathsCompletados = new ArrayList<>();
        this.listaActividadesPorCompletar = null;
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


    public void marcarTareaCompletada(Tarea tarea, String submissionMethod){

        tarea.setStatus(Status.Enviada);
        tarea.marcarEnviada(this, submissionMethod);
        listaActividadesCompletadas.add(this.actividadActual);
        listaActividadesPorCompletar.removeFirst();
        this.actividadActual=null;

        if (listaActividadesPorCompletar.isEmpty()){
            agregarLearningPathCompletado(this.learningPathActual);
        }
        else{
            new IllegalStateException("No se han completado todas las actividades para acabar el Learning Path.");
        }


    }

    public Actividad comenzarActividad() throws IllegalStateException{
        
        if (actividadActual==null){
            if (learningPathActual!=null){
                if (!listaActividadesPorCompletar.isEmpty()){
                setActividadActual(listaActividadesPorCompletar.getFirst());
                return actividadActual;
                }
            else{
                throw new IllegalStateException("No hay actividades que quedan por completar.");
            }
            }
            else{
                throw new IllegalStateException("No hay un Learning Path que se está siguiendo actualmente; escoja uno y vuelva a intentar.");
            }
        }

        else{
            throw new IllegalStateException("No se puede comenzar una actividad nueva porque hay una en progreso.");
        }
    }
    
    
    public void comenzarLearningPath(LearningPath learningPath){
        if (learningPathActual==null){
            setLearningPathActual(learningPath);
            this.listaActividadesPorCompletar = new ArrayList<>();
            listaActividadesPorCompletar.addAll(learningPath.getListaActividades());

        }
        else{
            throw new IllegalStateException("No se puede comenzar un Learning Path cuando ya hay uno en curso.");
        }

    }

    public void agregarLearningPathCompletado(LearningPath learningPathActual){

        if (listaActividadesPorCompletar.isEmpty()){
            listaLearningPathsCompletados.add(learningPathActual);
            this.listaActividadesPorCompletar=null;
            this.learningPathActual=null;
        }
        else{
            throw new IllegalStateException("No se puede completar un Learning Path que aún tiene actividades por completar.");
        }

    }
    
}
