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

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; // Importar la clase Collectors para utilizar el método joining, el metodo joining se usara para unir las descripciones de las actividades completadas y asi guardarlas en el archivo de texto

import actividad.Actividad;

import actividad.Nivel;

import actividad.Status;

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
    protected Map<Estudiante, List<Actividad>> listaActividadesCompletadasConDup; // Lista de actividades completadas con duplicados
    protected Map<Estudiante, List<Actividad>> listaActividadesCompletadas; // Lista de actividades completadas sin duplicados
    

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
        this.listaActividadesCompletadasConDup = new HashMap<>();
        this.listaActividadesCompletadas = new HashMap<>();
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

    public Profesor getCreador() { // Obtener el creador del Learning Path
        return creador;
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
        if (this.estudiantesInscritos.contains(estudiante)) {
            throw new IllegalArgumentException("El estudiante ya está inscrito en este Learning Path.");
        }
        // No permitir que se incriban si ya estan inscritos en otro Learning Path
        if (estudiante.getLearningPathActual() != null) {
            throw new IllegalArgumentException("El estudiante ya está inscrito en otro Learning Path.");
        }   
        this.estudiantesInscritos.add(estudiante);

        // Asignar el Learning Path al estudiante

        estudiante.setLearningPathActual(this);

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

    // Método para marcar una actividad obligatoria como completada para un estudiante específico
    public void actividadObligatoriaCompletada(Actividad actividad, Estudiante estudiante) {
        // Asegurarse de que las listas existen para el estudiante
        listaActividadesCompletadasConDup.computeIfAbsent(estudiante, k -> new ArrayList<>());
        listaActividadesCompletadas.computeIfAbsent(estudiante, k -> new ArrayList<>());

        // Agregar la actividad a la lista con duplicados
        listaActividadesCompletadasConDup.get(estudiante).add(actividad);

        // Agregar la actividad a la lista sin duplicados solo si aún no está presente
        if (!listaActividadesCompletadas.get(estudiante).contains(actividad)) {
            listaActividadesCompletadas.get(estudiante).add(actividad);
        }
    }

    // Método para calcular el progreso de un estudiante
    public float calcularProgreso(Estudiante estudiante) {
        // Contar todas las actividades obligatorias en el Learning Path
        int totalObligatorias = (int) listaActividades.stream()
            .filter(Actividad::esObligatoria)
            .count();

        // Verificar que existen actividades obligatorias para evitar divisiones por cero
        if (totalObligatorias == 0) {
            System.out.println("No hay actividades obligatorias en el Learning Path.");
            return 0;
        }

        // Obtener la lista de actividades completadas por el estudiante
        List<Actividad> actividadesCompletadasPorEstudiante = listaActividadesCompletadas.get(estudiante);

        // Contar cuántas actividades obligatorias ha completado exitosamente el estudiante
        int completadasObligatorias = (int) actividadesCompletadasPorEstudiante.stream()
            .filter(a -> a.esObligatoria() && a.esExitosa(estudiante))
            .count();
            System.out.println("Actividades obligatorias completadas por el estudiante: " + completadasObligatorias);

        // Calcular el progreso como un porcentaje
        float progreso = (float) completadasObligatorias / totalObligatorias * 100;

        // Si el progreso alcanza el 100%, marcar todas las actividades del Learning Path como completadas para el estudiante
        if (progreso == 100) {
            listaActividades.forEach(a -> a.setStatusParaEstudiante(estudiante, Status.Completado));
        } else {
            System.out.println("El progreso no ha alcanzado el 100% para el estudiante: " + estudiante.getNombre());
        }

        System.out.println("Progreso calculado para " + estudiante.getNombre() + ": " + progreso + "%");
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

    // Método para guardar el LearningPath en un archivo de texto plano
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
    
            // Guardar lista de actividades completadas con duplicados para cada estudiante
            writer.write("COMPLETADAS_CON_DUP:");
            writer.newLine();
            for (Map.Entry<Estudiante, List<Actividad>> entry : listaActividadesCompletadasConDup.entrySet()) {
                Estudiante estudiante = entry.getKey();
                writer.write(estudiante.getCorreo() + ":"); // Identificación del estudiante
                for (Actividad actividad : entry.getValue()) {
                    PersistenciaActividad.guardarActividad(actividad, writer);
                }
                writer.newLine(); // Nueva línea entre estudiantes
            }
    
            // Guardar lista de actividades completadas sin duplicados para cada estudiante
            writer.write("COMPLETADAS_SIN_DUP:");
            writer.newLine();
            for (Map.Entry<Estudiante, List<Actividad>> entry : listaActividadesCompletadas.entrySet()) {
                Estudiante estudiante = entry.getKey();
                writer.write(estudiante.getCorreo() + ":"); // Identificación del estudiante
                for (Actividad actividad : entry.getValue()) {
                    PersistenciaActividad.guardarActividad(actividad, writer);
                }
                writer.newLine(); // Nueva línea entre estudiantes
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
                            String[] partes = linea.split(":");
                            String correoEstudiante = partes[0]; // Identificador del estudiante
                            Estudiante estudiante = buscarEstudiantePorCorreo(learningPath.getEstudiantesInscritos(), correoEstudiante);
                            
                            if (estudiante != null && partes.length > 1) {
                                List<Actividad> actividades = Arrays.stream(partes[1].split(","))
                                    .map(desc -> PersistenciaActividad.cargarActividad(desc, creador, formatter))
                                    .collect(Collectors.toList());
                                learningPath.listaActividadesCompletadasConDup.put(estudiante, actividades);
                                System.out.println("Actividades completadas (con duplicados) cargadas para: " + estudiante.getNombre());
                            }
                        }
                    } else if (linea.equals("COMPLETADAS_SIN_DUP:")) {
                        System.out.println("Cargando COMPLETADAS_SIN_DUP...");
                        while ((linea = reader.readLine()) != null) {
                            String[] partes = linea.split(":");
                            String correoEstudiante = partes[0]; // Identificador del estudiante
                            Estudiante estudiante = buscarEstudiantePorCorreo(learningPath.getEstudiantesInscritos(), correoEstudiante);
                            
                            if (estudiante != null && partes.length > 1) {
                                List<Actividad> actividades = Arrays.stream(partes[1].split(","))
                                    .map(desc -> PersistenciaActividad.cargarActividad(desc, creador, formatter))
                                    .distinct() // Eliminar duplicados
                                    .collect(Collectors.toList());
                                learningPath.listaActividadesCompletadas.put(estudiante, actividades);
                                System.out.println("Actividades completadas (sin duplicados) cargadas para: " + estudiante.getNombre());
                            }
                        }
                    }
                }
    
                return learningPath;
            }
        }
        return null;
    }
    
    // Método auxiliar para buscar estudiantes por correo en la lista de inscritos
    private static Estudiante buscarEstudiantePorCorreo(List<Estudiante> estudiantes, String correo) {
        return estudiantes.stream()
                          .filter(e -> e.getCorreo().equals(correo))
                          .findFirst()
                          .orElse(null);
    }
    


    


}
    

