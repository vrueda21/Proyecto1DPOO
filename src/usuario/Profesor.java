package usuario;
import actividad.*;

public class Profesor extends Usuario{

    public Profesor(String nombre, String contrasenia, String correo){

        super(nombre, contrasenia, "profesor", correo);
    }


    public void marcarTareaExitosa(Tarea tarea){

        tarea.marcarExitosa(this);

    }  

}
