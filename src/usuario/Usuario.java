package usuario;

public abstract class Usuario {

    protected String nombre;
    protected String contrasenia;
    protected String id;
    protected String correo;

    public Usuario(String nombre, String contrasenia, String id, String correo){

        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.id = id;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    

}
