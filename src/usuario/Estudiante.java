package usuario;

public class Estudiante extends Usuario{

    public Estudiante(String nombre, String contrasenia, String id, String correo){

        super(nombre, contrasenia, id, correo);
    }

    public void prueba(){
        Administrador administrador = Administrador.getAdmin(); //LLamar a administrador


    }
}
