package plataforma;
import actividad.Actividad;
import actividad.Nivel;
import usuario.*;
import java.time.*;
import java.util.*;

public class Plataforma {

    private static Plataforma plataforma = new Plataforma();
    private Administrador administrador;
    private Map<String, Estudiante> estudiantes;

    private Plataforma(){

        if (plataforma == null){
            plataforma = this;
        }

        this.administrador = Administrador.getAdmin();
        this.estudiantes = new HashMap<>();

    }
    public static Plataforma getPlataforma() {

        if ((plataforma).equals(null)){

            plataforma = new Plataforma();

        }
        return plataforma;
    }

    public Administrador getAdministrador() {
        return administrador;
    }
    public Map<String, Estudiante> getListaEstudiantes() {
        return estudiantes;
    }
    
    public boolean agregarEstudiante(Estudiante estudiante){

        String correo = estudiante.getCorreo();
        if (estudiantes.containsKey(correo)){
            return false;
        }
        else{
            estudiantes.put(correo, estudiante);
            return true;
        }

    }

    /**
     
   public void crearActividad(){

       Actividad actividad = new Actividad("A", Nivel.Principiante, "hola", 300, 2.5, LocalDateTime.plusHours(2));


    }
    */


}
