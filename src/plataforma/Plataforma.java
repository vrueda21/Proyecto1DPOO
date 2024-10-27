package plataforma;
import actividad.Actividad;
import actividad.Nivel;
import usuario.Administrador;
import java.time.*;

public class Plataforma {

    private static Plataforma plataforma = new Plataforma();
    private Administrador administrador;

    private Plataforma(){

        this.administrador = Administrador.getAdmin();

    }
     public static Plataforma getPlataforma() {

        if ((plataforma).equals(null)){

            plataforma = new Plataforma();

        }
        return plataforma;
    }

    public void crearActividad(){

       Actividad actividad = new Actividad("A", Nivel.Principiante, "hola", 300, 2.5, LocalDateTime().plus(2); ) {
        
       };


    }
}
