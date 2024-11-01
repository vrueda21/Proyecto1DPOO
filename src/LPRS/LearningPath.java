package LPRS;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors; // Importar la clase Collectors para utilizar el método joining, el metodo joining se usara para unir las descripciones de las actividades completadas y asi guardarlas en el archivo de texto

import actividad.Actividad;
import actividad.Encuesta;
import actividad.Examen;
import actividad.Nivel;
import actividad.Obligatoria;
import actividad.Quiz;
import actividad.RecursoEducativo;
import actividad.Status;
import actividad.Tarea;
import pregunta.Opcion;
import pregunta.Pregunta;
import pregunta.PreguntaAbierta;
import pregunta.PreguntaCerrada;
import usuario.Estudiante;
import usuario.Profesor;
import persistencia.*;

public class LearningPath {

    protected String titulo; // Titulo del Learning Path
    protected Nivel nivelDificultad; // Nivel de dificultad del Learning Path
    protected String descripcion;   // Descripcion del Learning Path
    protected String objetivos; // Objetivos del Learning Path
    protected int duracionMinutos; // Duracion en minutos del Learning Path
    protected LocalDateTime fechaCreacion; // Fecha de creacion del Learning Path
    protected LocalDateTime fechaModificacion; // Fecha de modificacion del Learning Path
    protected int version; // Version del Learning Path
    protected Status status; // Status del Learning Path
    protected List<Actividad> listaActividades; // Lista de actividades del Learning Path
    protected Profesor creador; // Creador del Learning Path
    protected float rating; // Rating del Learning Path
    protected List<Estudiante> estudiantesInscritos; // Lista de estudiantes inscritos en el Learning Path
    protected float progreso; // Progreso del Learning Path
    protected List<Actividad> listaActividadesCompletadasConDup; // Lista de actividades completadas con duplicados
    protected List<Actividad> listaActividadesCompletadas; // Lista de actividades completadas sin duplicados
    

    public LearningPath (String titulo, Nivel nivelDificultad, String descripcion, String objetivos, int duracionMinutos, Profesor creador, float rating, List<Actividad> listaActividades){

        this.titulo=titulo; 
        this.nivelDificultad=nivelDificultad;
        this.descripcion=descripcion;
        this.objetivos= objetivos;
        this.duracionMinutos=duracionMinutos;
        this.fechaCreacion=LocalDateTime.now();
        this.fechaModificacion=null;
        this.version=1;
        this.status=Status.Incompleto;
        this.listaActividades = listaActividades;
        this.creador=creador;
        this.rating = rating;
        this.estudiantesInscritos = new ArrayList<>();
        this.progreso = 0;
        this.listaActividadesCompletadasConDup = new ArrayList<>();
        this.listaActividadesCompletadas = new ArrayList<>();
    }

    // Getters y Setters
    public String getTitulo() { // Obtener el titulo del Learning Path
        return titulo;
    }


    public void setTitulo(String titulo) { // Establecer el titulo del Learning Path
        this.titulo = titulo;
    }


    public Nivel getNivelDificultad() { // Obtener el nivel de dificultad del Learning Path
        return nivelDificultad;
    }


    public void setNivelDificultad(Nivel nivelDificultad) { // Establecer el nivel de dificultad del Learning Path
        this.nivelDificultad = nivelDificultad;
    }


    public String getDescripcion() { // Obtener la descripcion del Learning Path
        return descripcion;
    }


    public void setDescripcion(String descripcion) { // Establecer la descripcion del Learning Path
        this.descripcion = descripcion;
    }


    public String getObjetivos() { // Obtener los objetivos del Learning Path
        return objetivos;
    }


    public void setObjetivos(String objetivos) { // Establecer los objetivos del Learning Path
        this.objetivos = objetivos;
    }


    public int getDuracionMinutos() { // Obtener la duracion en minutos del Learning Path
        return duracionMinutos;
    }


    public void setDuracionMinutos(int duracionMinutos) { // Establecer la duracion en minutos del Learning Path
        this.duracionMinutos = duracionMinutos;
    }


    public LocalDateTime getFechaCreacion() { // Obtener la fecha de creacion del Learning Path
        return fechaCreacion;
    }


    public void setFechaCreacion(LocalDateTime fechaCreacion) { // Establecer la fecha de creacion del Learning Path
        this.fechaCreacion = fechaCreacion;
    }


    public LocalDateTime getFechaModificacion() { // Obtener la fecha de modificacion del Learning Path
        return fechaModificacion;
    }


    public void setFechaModificacion(LocalDateTime fechaModificacion) { // Establecer la fecha de modificacion del Learning Path
        this.fechaModificacion = fechaModificacion;
    }


    public int getVersion() { // Obtener la version del Learning Path
        return version;
    }


    public void setVersion() { // Establecer la version del Learning Path
        this.version +=1;
    }


    public Status getStatus() { // Obtener el status del Learning Path
        return status;
    }


    public void setStatus(Status status) { // Establecer el status del Learning Path
        this.status = status;
    }


    public List<Actividad> getListaActividades() { // Obtener la lista de actividades del Learning Path
        return listaActividades;
    }

    public void agregarActividad(Actividad actividad) { // Agregar una actividad a la lista de actividades del Learning Path
        if (listaActividades.contains(actividad)) {
            throw new IllegalArgumentException("La actividad ya está en la lista de actividades del Learning Path.");
        } else {
            listaActividades.add(actividad);
            this.fechaModificacion=LocalDateTime.now();
            setVersion();
        }
    }

    public void eliminarActividad(Actividad actividad) { // Eliminar una actividad de la lista de actividades del Learning Path 
        if (!listaActividades.contains(actividad)) {
            throw new IllegalArgumentException("La actividad no está en la lista de actividades del Learning Path.");
        } 
        else {
            listaActividades.remove(actividad);
            if (listaActividades.stream().noneMatch(Actividad::esObligatoria)) {
                listaActividades.add(actividad);
                throw new IllegalStateException("El Learning Path debe contener al menos una actividad obligatoria.");
            }
            this.fechaModificacion=LocalDateTime.now();
            setVersion();
        }
    }


    public boolean tieneActividadObligatoria() { // Verificar si el Learning Path tiene al menos una actividad obligatoria
        return listaActividades.stream().anyMatch(actividad -> actividad.esObligatoria());
    }

    public void validarActividadesObligatorias() {
        if (listaActividades.stream().noneMatch(Actividad::esObligatoria)) { // Verificar si el Learning Path tiene al menos una actividad obligatoria
            throw new IllegalStateException("El Learning Path debe contener al menos una actividad obligatoria.");
        }
    }
    public void registrarLearningPath() { 
        validarActividadesObligatorias();
        System.out.println("Learning Path validado y listo para su uso.");
    } // registrarLearningPath se debe utilizar cada vez que se cree un learning path de manera que se garantice que haya al menos una actividad obligatoria
    // EJEMPLO: 
    // LearningPath learningPath = new LearningPath("Título", Nivel.MEDIO, "Descripción", "Objetivos", 120, profesor, 4.5f);
    
    // learningPath.agregarActividad(new Actividad("Descripción Actividad", Nivel.FACIL, "Objetivo", 30, 1.0, LocalDateTime.of(2024, 12, 31, 23, 59), Status.INCOMPLETO, Obligatoria.SI, "Tipo de Actividad"));
    
    // learningPath.registrarLearningPath();

    public void inscripcionEstudiante(Estudiante estudiante) {        
        this.estudiantesInscritos.add(estudiante);

    } // inscripcionEstudiante se debe utilizar cada vez que un estudiante se inscriba a un learning path
    // si esta variable es verdadera, no se puede crear, modificar, ni eliminar actividades del learning path

    public boolean verificarSiInscrito(Estudiante estudiante) {
        return this.estudiantesInscritos.contains(estudiante);
    } // verificarSiInscrito se debe utilizar para verificar si un estudiante ya está inscrito en un learning path

    public boolean verificarSiHayInscritos() {
        return !this.estudiantesInscritos.isEmpty();
    } // verificarSiHayInscritos se debe utilizar para verificar si hay estudiantes inscritos en un learning path

    public boolean verificarActividad(Actividad actividad) {
        return this.listaActividades.contains(actividad);
        
    } // verificarActividad se debe utilizar para verificar si una actividad está asociada a un learning path
    public void eliminarInscripcion(Estudiante estudiante) {
        if (!this.estudiantesInscritos.contains(estudiante)) {
            throw new IllegalArgumentException("El estudiante no está inscrito en este Learning Path.");
        }
        this.estudiantesInscritos.remove(estudiante);
    } // eliminarInscripcion se debe utilizar para eliminar la inscripción de un estudiante en un learning path
    // se debe utilizar cada vez que un estudiante finalice el learning path 

    public void actividadObligatoriaCompletada(Actividad actividad) {
        this.listaActividadesCompletadasConDup.add(actividad);
        this.listaActividadesCompletadas = new ArrayList<>(new HashSet<>(this.listaActividadesCompletadasConDup));
    }
    
    public float calcularProgreso(Estudiante estudiante) {
        int totalObligatorias = (int) listaActividades.stream()
            .filter(Actividad::esObligatoria) // Filtrar solo las obligatorias
            .count();
    
        // Contar el número de actividades obligatorias que el estudiante ha completado con éxito
        int completadasObligatorias = (int) listaActividadesCompletadas.stream()
            .filter(a -> a.esObligatoria() && a.esExitosa(estudiante)) // Verificar el éxito para el estudiante
            .count();
    
        this.progreso = totalObligatorias == 0 ? 0 : (float) completadasObligatorias / totalObligatorias * 100;
        if (this.progreso == 100) {
            this.status = Status.Completado;
        }
        
        return progreso;
    }
    

    public float getRating(){ // Obtener el rating del Learning Path
        return rating;
    }

    public void setRating(float rating){ // Establecer el rating del Learning Path
        this.rating = rating;
    }
    
    public List<Estudiante> getEstudiantesInscritos(){ // Obtener la lista de estudiantes inscritos en el Learning Path
        return estudiantesInscritos;
    }


    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSSSSSSSS]");

    // Persistencia de LearningPath en archivos de texto plano

    
    public void guardarEnArchivo(File archivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            // Guardar atributos básicos del LearningPath
            writer.write(this.titulo + "," +
                         this.nivelDificultad + "," +
                         this.descripcion + "," +
                         this.objetivos + "," +
                         this.duracionMinutos + "," +
                         (this.fechaCreacion != null ? this.fechaCreacion.format(formatter) : "") + "," +
                         (this.fechaModificacion != null ? this.fechaModificacion.format(formatter) : "") + "," +
                         this.version + "," +
                         this.status + "," +
                         this.rating + "," +
                         this.creador.getNombre() + "," +
                         this.progreso);
            writer.newLine();
    
            
            writer.write("ACTIVIDADES:");
            writer.newLine();
    
            // Guardar cada actividad en la lista de actividades del LearningPath
            for (Actividad actividad : listaActividades) {
                PersistenciaActividad.guardarActividad(actividad, writer);
            }
    
            // Guardar lista de actividades completadas con duplicados
            writer.write("COMPLETADAS_CON_DUP:");
            writer.newLine();
            for (Actividad actividad : listaActividadesCompletadasConDup) {
                PersistenciaActividad.guardarActividad(actividad, writer);
            }
    
            // Guardar lista de actividades completadas sin duplicados
            writer.write("COMPLETADAS_SIN_DUP:");
            writer.newLine();
            for (Actividad actividad : listaActividadesCompletadas) {
                PersistenciaActividad.guardarActividad(actividad, writer);
            }
        }
    }
    



    // Método principal para cargar el LearningPath
    public static LearningPath cargarDeArchivo(File archivo, Profesor creador) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea = reader.readLine();
            if (linea != null) {
                String[] datos = linea.split(",");
                
                // Leer atributos en el mismo orden en que se guardaron
                String titulo = datos[0];
                Nivel nivelDificultad = Nivel.valueOf(datos[1]);
                String descripcion = datos[2];
                String objetivos = datos[3];
                int duracionMinutos = Integer.parseInt(datos[4]);
                LocalDateTime fechaCreacion = LocalDateTime.parse(datos[5], formatter);
                LocalDateTime fechaModificacion = datos[6].isEmpty() ? null : LocalDateTime.parse(datos[6], formatter);
                int version = Integer.parseInt(datos[7]);
                Status status = Status.valueOf(datos[8]);
                float rating = Float.parseFloat(datos[9]);
                String nombreCreador = datos[10];
                float progreso = Float.parseFloat(datos[11]);

                // Crear el LearningPath con los valores cargados
                LearningPath learningPath = new LearningPath(titulo, nivelDificultad, descripcion, objetivos, duracionMinutos, creador, rating, new ArrayList<>());
                learningPath.fechaCreacion = fechaCreacion;
                learningPath.fechaModificacion = fechaModificacion;
                learningPath.version = version;
                learningPath.status = status;
                learningPath.progreso = progreso;

                System.out.println("Learning Path cargado correctamente. Título: " + titulo);

                // Leer y cargar las listas adicionales
                while ((linea = reader.readLine()) != null) {
                    if (linea.equals("ACTIVIDADES:")) {
                        System.out.println("Cargando ACTIVIDADES...");
                        while ((linea = reader.readLine()) != null && !linea.equals("COMPLETADAS_CON_DUP:")) {
                            Actividad actividad = PersistenciaActividad.cargarActividad(linea, creador, formatter);
                            if (actividad != null) {
                                learningPath.listaActividades.add(actividad);
                                System.out.println("Actividad cargada: " + actividad.getDescripcion());
                            }
                        }
                    } else if (linea.equals("COMPLETADAS_CON_DUP:")) {
                        System.out.println("Cargando COMPLETADAS_CON_DUP...");
                        while ((linea = reader.readLine()) != null && !linea.equals("COMPLETADAS_SIN_DUP:")) {
                            Actividad actividad = PersistenciaActividad.cargarActividad(linea, creador, formatter);
                            if (actividad != null) {
                                learningPath.listaActividadesCompletadasConDup.add(actividad);
                                System.out.println("Actividad completada (con duplicado) cargada: " + actividad.getDescripcion());
                            }
                        }
                    } else if (linea.equals("COMPLETADAS_SIN_DUP:")) {
                        System.out.println("Cargando COMPLETADAS_SIN_DUP...");
                        while ((linea = reader.readLine()) != null) {
                            Actividad actividad = PersistenciaActividad.cargarActividad(linea, creador, formatter);
                            if (actividad != null) {
                                learningPath.listaActividadesCompletadas.add(actividad);
                                System.out.println("Actividad completada (sin duplicado) cargada: " + actividad.getDescripcion());
                            }
                        }
                    }
                }

                return learningPath;
            }
        }
        return null;
    }


    


}
    

