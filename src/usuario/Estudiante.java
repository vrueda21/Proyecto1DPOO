package usuario;
import plataforma.*;
import actividad.*;

public class Estudiante extends Usuario{

    public Estudiante(String nombre, String contrasenia, String correo){

        super(nombre, contrasenia, "estudiante", correo);
    }

    public void prueba(){
        Administrador administrador = Administrador.getAdmin(); //LLamar a administrador


    }

    public static boolean registrarEstudiante(String nombre, String correo, String contrasenia) {
		
        Estudiante estudiante = new Estudiante(nombre, correo, contrasenia);
		Plataforma plataforma = Plataforma.getPlataforma();
		return plataforma.agregarEstudiante(estudiante);
	}

    public void marcarCompletada(Tarea tarea){

        tarea.marcarCompletada(this);

    }
    
}
