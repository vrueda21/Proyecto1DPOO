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
                guardarActividad(actividad, writer);
            }
    
            // Guardar lista de actividades completadas con duplicados
            writer.write("COMPLETADAS_CON_DUP:");
            writer.newLine();
            for (Actividad actividad : listaActividadesCompletadasConDup) {
                guardarActividad(actividad, writer);
            }
    
            // Guardar lista de actividades completadas sin duplicados
            writer.write("COMPLETADAS_SIN_DUP:");
            writer.newLine();
            for (Actividad actividad : listaActividadesCompletadas) {
                guardarActividad(actividad, writer);
            }
        }
    }
    

// Metodo auxiliar guardar actividades 

private void guardarActividad(Actividad actividad, BufferedWriter writer) throws IOException {

    if (actividad instanceof Tarea)
    {
        Tarea tarea = (Tarea) actividad;
        writer.write("Tarea,"+ actividad.getDescripcion()+","+ 
        tarea.getNivelDificultad()+","+ 
        tarea.getObjetivo()+","+ 
        tarea.getDuracionEsperada()+","+ 
        tarea.getVersion()+","+ 
        (tarea.getFechaLimite() != null ? actividad.getFechaLimite().format(formatter):"")+","+ 
        (tarea.getFechaInicio() != null ? actividad.getFechaInicio().format(formatter):"")+","+
        tarea.getStatus()+","+ 
        (tarea.esObligatoria()? "SI" : "NO")+","+ 
        tarea.getActividadesPreviasSugeridas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(","))+","+
        tarea.getActividadesSeguimientoRecomendadas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(","))+","+
        tarea.getSubmissionMethod()+","+ 
        tarea.getCreador().getNombre());
    } 
    else if (actividad instanceof Quiz)
    {
        Quiz quiz = (Quiz) actividad;   

        writer.write("Quiz,"+ actividad.getDescripcion()+","+
        quiz.getNivelDificultad()+","+
        quiz.getObjetivo()+","+
        quiz.getDuracionEsperada()+","+
        quiz.getVersion()+","+
        (quiz.getFechaLimite() != null ? actividad.getFechaLimite().format(formatter):"")+","+
        (quiz.getFechaInicio() != null ? actividad.getFechaInicio().format(formatter):"")+","+
        quiz.getStatus()+","+
        (quiz.esObligatoria()? "SI" : "NO")+","+
        quiz.getActividadesPreviasSugeridas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(","))+","+
        quiz.getActividadesSeguimientoRecomendadas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(","))+","+
        quiz.getListaPreguntas().stream().map(Pregunta::getEnunciado).collect(Collectors.joining(","))+","+
        quiz.getCalificacionMinima()+","+
        quiz.getCalificacionObtenida()+","+
        quiz.getCreador().getNombre());
    } 
    else if (actividad instanceof RecursoEducativo)
    {
        RecursoEducativo recurso = (RecursoEducativo) actividad;
        writer.write("Recurso,"+ actividad.getDescripcion()+","+
        recurso.getNivelDificultad()+","+
        recurso.getObjetivo()+","+
        recurso.getDuracionEsperada()+","+
        recurso.getVersion()+","+
        (recurso.getFechaLimite() != null ? actividad.getFechaLimite().format(formatter):"")+","+
        (recurso.getFechaInicio() != null ? actividad.getFechaInicio().format(formatter):"")+","+
        recurso.getStatus()+","+
        (recurso.esObligatoria()? "SI" : "NO")+","+
        recurso.getActividadesPreviasSugeridas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(","))+","+
        recurso.getActividadesSeguimientoRecomendadas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(","))+","+
        recurso.getTipoRecurso()+","+
        recurso.getCreador().getNombre());
    } 
    else if (actividad instanceof Examen) {
        Examen examen = (Examen) actividad;
    
        writer.write("Examen," + actividad.getDescripcion() + "," +
                examen.getNivelDificultad() + "," +
                examen.getObjetivo() + "," +
                examen.getDuracionEsperada() + "," +
                examen.getVersion() + "," +
                (examen.getFechaLimite() != null ? actividad.getFechaLimite().format(formatter) : "") + "," +
                (examen.getFechaInicio() != null ? actividad.getFechaInicio().format(formatter) : "") + "," +
                examen.getStatus() + "," +
                (examen.esObligatoria() ? "SI" : "NO") + "," +
                examen.getActividadesPreviasSugeridas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(",")) + "," +
                examen.getActividadesSeguimientoRecomendadas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(",")) + "," +
                examen.getListaPreguntas().stream()
                        .map(pregunta -> {
                            if (pregunta instanceof PreguntaCerrada) {
                                return "Cerrada|" + pregunta.getEnunciado();
                            } else if (pregunta instanceof PreguntaAbierta) {
                                return "Abierta|" + pregunta.getEnunciado();
                            }
                            return "";
                        })
                        .collect(Collectors.joining(";")) + "," +
                examen.getCalificacionMinima() + "," +
                examen.getRespuestasCorrectas() + "," +
                examen.getCalificacionObtenida() + "," +
                examen.getRespuestasAbiertas().stream().collect(Collectors.joining(",")) + "," +
                examen.getCreador().getNombre());
    
        writer.newLine();
    }
    
    
    else if (actividad instanceof Encuesta)
    {
        Encuesta encuesta = (Encuesta) actividad;
        writer.write("Encuesta,"+ actividad.getDescripcion()+","+
        encuesta.getNivelDificultad()+","+
        encuesta.getObjetivo()+","+
        encuesta.getDuracionEsperada()+","+
        encuesta.getVersion()+","+
        (encuesta.getFechaLimite() != null ? actividad.getFechaLimite().format(formatter):"")+","+
        (encuesta.getFechaInicio() != null ? actividad.getFechaInicio().format(formatter):"")+","+
        encuesta.getStatus()+","+
        (encuesta.esObligatoria()? "SI" : "NO")+","+
        encuesta.getTipo()+","+
        encuesta.getActividadesPreviasSugeridas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(","))+","+
        encuesta.getActividadesSeguimientoRecomendadas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(","))+","+
        encuesta.getListaPreguntas().stream().map(PreguntaAbierta::getEnunciado).collect(Collectors.joining(","))+","+
        encuesta.getCreador().getNombre());
    }

    writer.newLine();
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
                            Actividad actividad = cargarActividad(linea, creador, formatter);
                            if (actividad != null) {
                                learningPath.listaActividades.add(actividad);
                                System.out.println("Actividad cargada: " + actividad.getDescripcion());
                            }
                        }
                    } else if (linea.equals("COMPLETADAS_CON_DUP:")) {
                        System.out.println("Cargando COMPLETADAS_CON_DUP...");
                        while ((linea = reader.readLine()) != null && !linea.equals("COMPLETADAS_SIN_DUP:")) {
                            Actividad actividad = cargarActividad(linea, creador, formatter);
                            if (actividad != null) {
                                learningPath.listaActividadesCompletadasConDup.add(actividad);
                                System.out.println("Actividad completada (con duplicado) cargada: " + actividad.getDescripcion());
                            }
                        }
                    } else if (linea.equals("COMPLETADAS_SIN_DUP:")) {
                        System.out.println("Cargando COMPLETADAS_SIN_DUP...");
                        while ((linea = reader.readLine()) != null) {
                            Actividad actividad = cargarActividad(linea, creador, formatter);
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

    // Método auxiliar para determinar el tipo de actividad y cargarla
    private static Actividad cargarActividad(String linea, Profesor creador, DateTimeFormatter formatter) {
        String[] datos = linea.split(",");
        System.out.println("Datos desglosados para la línea: " + linea);
        for (int i = 0; i < datos.length; i++) {
            System.out.println("datos[" + i + "]: " + datos[i]);
        }
        String tipoActividad = datos[0];

        switch (tipoActividad) {
            case "Tarea":
                return cargarTarea(datos, creador, formatter);
            case "Quiz":
                return cargarQuiz(datos, creador, formatter);
            case "Recurso":
                return cargarRecurso(datos, creador, formatter);
            case "Examen":
                return cargarExamen(datos, creador, formatter);
            case "Encuesta":
                return cargarEncuesta(datos, creador, formatter);
            default:
                System.out.println("Tipo de actividad desconocido: " + tipoActividad);
                return null;
        }
    }

    // Métodos auxiliares específicos para cada tipo de actividad (incluyen todos los atributos)

    private static Tarea cargarTarea(String[] datos, Profesor creador, DateTimeFormatter formatter) {
        String descripcion = datos[1];
        Nivel nivel = Nivel.valueOf(datos[2]);
        String objetivo = datos[3];
        int duracion = Integer.parseInt(datos[4]);
        double version = Double.parseDouble(datos[5]);
        LocalDateTime fechaLimite = LocalDateTime.parse(datos[6], formatter);
        LocalDateTime fechaInicio = LocalDateTime.parse(datos[7], formatter);
        Status status = Status.valueOf(datos[8]);
        Obligatoria obligatoria = datos[9].equals("SI") ? Obligatoria.SI : Obligatoria.NO;
        List<Actividad> actividadesPreviasSugeridas = cargarActividades(datos[10], creador, formatter);
        List<Actividad> actividadesSeguimientoRecomendadas = cargarActividades(datos[11], creador, formatter);
        String submissionMethod = datos[12];
        String nombreCreador = datos[13]; // No se usa en la creación de la tarea porque ya se tiene el profesor

       // Crear la tarea con los datos cargados
        Tarea tarea = new Tarea(descripcion, nivel, objetivo, duracion, version, fechaLimite, status, obligatoria, submissionMethod, creador, actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
        
        // Asignar los valores que no entran al constructor directamente como fecha de inicio
        tarea.setFechaInicio(fechaInicio);

        return tarea;
    }

    private static Quiz cargarQuiz(String[] datos, Profesor creador, DateTimeFormatter formatter) {
        String descripcion = datos[1];
        Nivel nivel = Nivel.valueOf(datos[2]);
        String objetivo = datos[3];
        int duracion = Integer.parseInt(datos[4]);
        double version = Double.parseDouble(datos[5]);
        LocalDateTime fechaLimite = LocalDateTime.parse(datos[6], formatter);
        LocalDateTime fechaInicio = LocalDateTime.parse(datos[7], formatter);
        Status status = Status.valueOf(datos[8]);
        Obligatoria obligatoria = datos[9].equals("SI") ? Obligatoria.SI : Obligatoria.NO;
        List<Actividad> actividadesPreviasSugeridas = cargarActividades(datos[10], creador, formatter);
        List<Actividad> actividadesSeguimientoRecomendadas = cargarActividades(datos[11], creador, formatter);
        List<PreguntaCerrada> listaPreguntas = cargarPreguntasCerradas(datos[12]);
        double calificacionMinima = Double.parseDouble(datos[13]);
        double calificacionObtenida = Double.parseDouble(datos[14]);
        String nombreCreador = datos[15]; // No se usa en la creación del quiz porque ya se tiene el profesor

        Quiz quiz= new Quiz(descripcion, nivel, objetivo, duracion, version, fechaLimite, status, obligatoria, listaPreguntas, calificacionMinima, creador, actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
        
        // Asignar los valores que no entran al constructor directamente como calificacion obtenida y fehca de inicio
        
        quiz.setFechaInicio(fechaInicio);
        quiz.setCalificacionObtenida(calificacionObtenida);
        quiz.setListaPreguntas(listaPreguntas);


        return quiz;
    }

    private static RecursoEducativo cargarRecurso(String[] datos, Profesor creador, DateTimeFormatter formatter) {
        String descripcion = datos[1];
        Nivel nivel = Nivel.valueOf(datos[2]);
        String objetivo = datos[3];
        int duracion = Integer.parseInt(datos[4]);
        double version = Double.parseDouble(datos[5]);
        LocalDateTime fechaLimite = LocalDateTime.parse(datos[6], formatter);
        LocalDateTime fechaInicio = LocalDateTime.parse(datos[7], formatter);
        Status status = Status.valueOf(datos[8]);
        Obligatoria obligatoria = datos[9].equals("SI") ? Obligatoria.SI : Obligatoria.NO;
        List<Actividad> actividadesPreviasSugeridas = cargarActividades(datos[10], creador, formatter);
        List<Actividad> actividadesSeguimientoRecomendadas = cargarActividades(datos[11], creador, formatter);
        String tipoRecurso = datos[12];
        String nombreCreador = datos[13]; // No se usa en la creación del recurso porque ya se tiene el profesor
        // Crear el recurso educativo con los datos cargados

        RecursoEducativo recurso = new RecursoEducativo(descripcion, nivel, objetivo, duracion, version, fechaLimite, status, obligatoria, tipoRecurso, creador, actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);

        // Asignar los valores que no entran al constructor directamente como fecha de inicio

        recurso.setFechaInicio(fechaInicio);

        return recurso;

    }

    private static Examen cargarExamen(String[] datos, Profesor creador, DateTimeFormatter formatter) { // Cambie lo de cargar examen porque no estaba cargando las preguntas bien el formato estaba rarisimo ahora lo que hace es que detecta si es cerrada o abierta y las carga en una lista de preguntas con un metodo para abiertas o cerradas dependiendo del tipo
        String descripcion = datos[1];
        Nivel nivel = Nivel.valueOf(datos[2]);
        String objetivo = datos[3];
        int duracion = Integer.parseInt(datos[4]);
        double version = Double.parseDouble(datos[5]);
        LocalDateTime fechaLimite = LocalDateTime.parse(datos[6], formatter);
        LocalDateTime fechaInicio = LocalDateTime.parse(datos[7], formatter);
        Status status = Status.valueOf(datos[8]);
        Obligatoria obligatoria = datos[9].equals("SI") ? Obligatoria.SI : Obligatoria.NO;
        List<Actividad> actividadesPreviasSugeridas = cargarActividades(datos[10], creador, formatter);
        List<Actividad> actividadesSeguimientoRecomendadas = cargarActividades(datos[11], creador, formatter);
    
        // Cargar lista de preguntas de distintos tipos
        List<Pregunta> listaPreguntas = new ArrayList<>();
        String[] preguntasData = datos[12].split(";"); // Asume que cada pregunta está separada por ;
        for (String preguntaData : preguntasData) {
            if (preguntaData.startsWith("Cerrada|")) {
                String preguntaCerradaStr = preguntaData.substring("Cerrada|".length());
                List<PreguntaCerrada> preguntasCerradas = cargarPreguntasCerradas(preguntaCerradaStr);
                listaPreguntas.addAll(preguntasCerradas);
            } else if (preguntaData.startsWith("Abierta|")) {
                String preguntaAbiertaStr = preguntaData.substring("Abierta|".length());
                List<PreguntaAbierta> preguntasAbiertas = cargarPreguntasAbiertas(preguntaAbiertaStr);
                listaPreguntas.addAll(preguntasAbiertas);
            }
        }
    
        double calificacionMinima = Double.parseDouble(datos[13]);
        int respuestasCorrectas = Integer.parseInt(datos[14]);
        double calificacionObtenida = Double.parseDouble(datos[15]);
        List<String> respuestasAbiertas = Arrays.asList(datos[16].split(","));

    
        Examen examen = new Examen(descripcion, nivel, objetivo, duracion, version, fechaLimite, status, obligatoria, listaPreguntas, calificacionMinima, creador, actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
    
        // Asignar valores adicionales
        examen.setFechaInicio(fechaInicio);
        examen.setCalificacionObtenida(calificacionObtenida);
        examen.setRespuestasCorrectas(respuestasCorrectas);
        examen.setRespuestasAbiertas(respuestasAbiertas);
    
        return examen;
    }
    

    private static Encuesta cargarEncuesta(String[] datos, Profesor creador, DateTimeFormatter formatter) {
        String descripcion = datos[1];
        Nivel nivel = Nivel.valueOf(datos[2]);
        String objetivo = datos[3];
        int duracion = Integer.parseInt(datos[4]);
        double version = Double.parseDouble(datos[5]);
        LocalDateTime fechaLimite = LocalDateTime.parse(datos[6], formatter);
        LocalDateTime fechaInicio = LocalDateTime.parse(datos[7], formatter);
        Status status = Status.valueOf(datos[8]);
        Obligatoria obligatoria = datos[9].equals("SI") ? Obligatoria.SI : Obligatoria.NO;
        List<Actividad> actividadesPreviasSugeridas = cargarActividades(datos[10], creador, formatter);
        List<Actividad> actividadesSeguimientoRecomendadas = cargarActividades(datos[11], creador, formatter);
        ArrayList<PreguntaAbierta> listaPreguntas = new ArrayList<>();
       
       
        Encuesta encuesta = new Encuesta(descripcion, nivel, objetivo, duracion, version, fechaLimite, status, obligatoria, actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas, creador, listaPreguntas);
   
        // Asignar los valores que no entran al constructor directamente como fecha de inicio

        encuesta.setFechaInicio(fechaInicio);

        return encuesta;
   
    }

    // Método auxiliar para cargar las actividades previas y de seguimiento
    private static List<Actividad> cargarActividades(String actividadesStr, Profesor creador, DateTimeFormatter formatter) {
        List<Actividad> actividades = new ArrayList<>();
        String[] actividadesData = actividadesStr.split(";");
        for (String actividadData : actividadesData) {
            String[] datos = actividadData.split(",");
            String tipo = datos[0];
            
            // Validación adicional basada en el tipo de actividad
            if (tipo.equals("Tarea") || tipo.equals("Quiz") || tipo.equals("Recurso") || tipo.equals("Examen") || tipo.equals("Encuesta")) {
                Actividad actividad = cargarActividad(actividadData, creador, formatter);
                if (actividad != null) {
                    actividades.add(actividad);
                }
            } else {
                System.out.println("Tipo de actividad desconocido: " + tipo);
            }
        }
        return actividades;
    }

    // Método auxiliar para cargar las preguntas de un quiz o examen

    public static List<Pregunta> cargarListaPreguntas(String preguntasStr) {
        List<Pregunta> listaPreguntas = new ArrayList<>();
    
        if (preguntasStr == null || preguntasStr.isEmpty()) {
            return listaPreguntas;
        }
    
        String[] preguntasData = preguntasStr.split(";");
        for (String preguntaStr : preguntasData) {
            if (preguntaStr.startsWith("Cerrada:")) {
                String preguntaCerradaStr = preguntaStr.substring("Cerrada:".length());
                listaPreguntas.addAll(cargarPreguntasCerradas(preguntaCerradaStr));
            } else if (preguntaStr.startsWith("Abierta:")) {
                String preguntaAbiertaStr = preguntaStr.substring("Abierta:".length());
                listaPreguntas.addAll(cargarPreguntasAbiertas(preguntaAbiertaStr));
            } else {
                System.out.println("Tipo de pregunta desconocido o formato incorrecto: " + preguntaStr);
            }
        }
    
        return listaPreguntas;
    }
    
    
    // Método para cargar una lista de preguntas cerradas a partir de una cadena de texto
    public static List<PreguntaCerrada> cargarPreguntasCerradas(String preguntasStr) {
        List<PreguntaCerrada> listaPreguntas = new ArrayList<>();
    
        if (preguntasStr == null || preguntasStr.isEmpty()) {
            return listaPreguntas; // Retorna una lista vacía si no hay preguntas
        }
    
        // Dividir cada pregunta usando ';'
        String[] preguntasData = preguntasStr.split(";");
        for (String preguntaStr : preguntasData) {
            // Divide cada componente de la pregunta usando '|'
            String[] partesPregunta = preguntaStr.split("\\|");
    
            // El primer elemento es el enunciado de la pregunta
            String enunciado = partesPregunta[0];
    
            // Crear una nueva pregunta cerrada con el enunciado
            PreguntaCerrada pregunta = new PreguntaCerrada(enunciado);
    
            // Diccionarios para almacenar las opciones
            Dictionary<Opcion, String> opcionA = new Hashtable<>();
            Dictionary<Opcion, String> opcionB = new Hashtable<>();
            Dictionary<Opcion, String> opcionC = new Hashtable<>();
            Dictionary<Opcion, String> opcionD = new Hashtable<>();
            Dictionary<Opcion, String> respuestaCorrecta = new Hashtable<>();
    
            // Iterar sobre las partes de la pregunta para asignar opciones y respuesta
            for (int i = 1; i < partesPregunta.length; i++) {
                String parte = partesPregunta[i];
    
                if (parte.startsWith("A:")) {
                    opcionA.put(Opcion.A, parte.substring(2));
                    pregunta.setOpcionA(opcionA);
                } else if (parte.startsWith("B:")) {
                    opcionB.put(Opcion.B, parte.substring(2));
                    pregunta.setOpcionB(opcionB);
                } else if (parte.startsWith("C:")) {
                    opcionC.put(Opcion.C, parte.substring(2));
                    pregunta.setOpcionC(opcionC);
                } else if (parte.startsWith("D:")) {
                    opcionD.put(Opcion.D, parte.substring(2));
                    pregunta.setOpcionD(opcionD);
                } else if (parte.startsWith("Respuesta:")) {
                    String respuesta = parte.substring(10);
                    if (respuesta.equals("A")) {
                        respuestaCorrecta.put(Opcion.A, opcionA.get(Opcion.A));
                    } else if (respuesta.equals("B")) {
                        respuestaCorrecta.put(Opcion.B, opcionB.get(Opcion.B));
                    } else if (respuesta.equals("C")) {
                        respuestaCorrecta.put(Opcion.C, opcionC.get(Opcion.C));
                    } else if (respuesta.equals("D")) {
                        respuestaCorrecta.put(Opcion.D, opcionD.get(Opcion.D));
                    }
                    pregunta.setRespuesta(respuestaCorrecta);
                }
            }
    
            // Añadir la pregunta configurada a la lista
            listaPreguntas.add(pregunta);
        }
    
        return listaPreguntas; // Retorna la lista de preguntas cerradas
    }
    

    public static List<PreguntaAbierta> cargarPreguntasAbiertas(String preguntasStr) {
        List<PreguntaAbierta> listaPreguntas = new ArrayList<>();
    
        if (preguntasStr == null || preguntasStr.isEmpty()) {
            return listaPreguntas; // Retorna una lista vacía si no hay preguntas
        }
    
        // Dividir cada pregunta usando ';'
        String[] preguntasData = preguntasStr.split(";");
        for (String preguntaStr : preguntasData) {
            // Cada pregunta tiene el formato "Abierta:enunciado"
            String enunciado = preguntaStr.trim();
    
            // Crear una nueva pregunta abierta con el enunciado
            PreguntaAbierta pregunta = new PreguntaAbierta(enunciado);
    
            // Añadir la pregunta configurada a la lista
            listaPreguntas.add(pregunta);
        }
    
        return listaPreguntas; // Retorna la lista de preguntas abiertas
    }


}
    

