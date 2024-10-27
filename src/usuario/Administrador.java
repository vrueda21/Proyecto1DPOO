package usuario;

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

}
