package usuario;

import LPRS.LearningPath;
import actividad.Actividad;
import java.util.ArrayList;

public class Administrador extends Usuario{

    private static Administrador admin = new Administrador();
    private Administrador(){

        super("Valentina", "vale123", "admin", "administrador@lprs.com");

    }
    public static Administrador getAdmin() {

        if ((admin).equals(null)){

            admin = new Administrador();

        }
        return admin;
    }

    public void crearProfesor(String nombre, String contrasenia, String correo){

        Profesor profesor = new Profesor(nombre, contrasenia, correo, null, null); // Se crea un profesor con los datos ingresados

    }

    public void crearEstudiante(String nombre, String contrasenia, String correo){

        Estudiante estudiante = new Estudiante(nombre, contrasenia, correo); // Se crea un estudiante con los datos ingresados

    }

    public void eliminarUsuario(Usuario usuario){

        usuario = null;

    }

    public void eliminarLearningPath(LearningPath learningPath){

        learningPath = null;

    }
    
    public void eliminarActividad(Actividad actividad){

        actividad = null;

    }
    
    

    

}
