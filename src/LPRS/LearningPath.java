package LPRS;
import actividad.*;
import pregunta.Pregunta;
import pregunta.PreguntaAbierta;
import pregunta.PreguntaCerrada;
import usuario.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors; // Importar la clase Collectors para utilizar el método joining, el metodo joining se usara para unir las descripciones de las actividades completadas y asi guardarlas en el archivo de texto

public class LearningPath {

    protected String titulo;
    protected Nivel nivelDificultad;
    protected String descripcion;
    protected String objetivos;
    protected int duracionMinutos;
    protected LocalDateTime fechaCreacion;
    protected LocalDateTime fechaModificacion;
    protected int version;
    protected Status status;
    protected List<Actividad> listaActividades;
    protected Profesor creador;
    protected float rating;
    protected List<Estudiante> estudiantesInscritos;
    protected float progreso;
    protected List<Actividad> listaActividadesCompletadasConDup;
    protected List<Actividad> listaActividadesCompletadas;
    

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
        this.listaActividades = new ArrayList<>();
        this.creador=creador;
        this.rating = rating;
        this.estudiantesInscritos = new ArrayList<>();
        this.progreso = 0;
        this.listaActividadesCompletadasConDup = new ArrayList<>();
        this.listaActividadesCompletadas = new ArrayList<>();
    }


    public String getTitulo() {
        return titulo;
    }


    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public Nivel getNivelDificultad() {
        return nivelDificultad;
    }


    public void setNivelDificultad(Nivel nivelDificultad) {
        this.nivelDificultad = nivelDificultad;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getObjetivos() {
        return objetivos;
    }


    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }


    public int getDuracionMinutos() {
        return duracionMinutos;
    }


    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }


    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }


    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }


    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }


    public int getVersion() {
        return version;
    }


    public void setVersion() {
        this.version +=1;
    }


    public Status getStatus() {
        return status;
    }


    public void setStatus(Status status) {
        this.status = status;
    }


    public List<Actividad> getListaActividades() {
        return listaActividades;
    }

    public void agregarActividad(Actividad actividad) {
        if (listaActividades.contains(actividad)) {
            throw new IllegalArgumentException("La actividad ya está en la lista de actividades del Learning Path.");
        } else {
            listaActividades.add(actividad);
            this.fechaModificacion=LocalDateTime.now();
            setVersion();
        }
    }

    public void eliminarActividad(Actividad actividad) {
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


    public boolean tieneActividadObligatoria() {
        return listaActividades.stream().anyMatch(actividad -> actividad.esObligatoria());
    }

    public void validarActividadesObligatorias() {
        if (listaActividades.stream().noneMatch(Actividad::esObligatoria)) {
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
    

    public float getRating(){
        return rating;
    }

    public void setRating(float rating){
        this.rating = rating;
    }
    
    public List<Estudiante> getEstudiantesInscritos(){
        return estudiantesInscritos;
    }


    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME; // Formato de fecha y hora para persistencia
    
    // Persistencia de LearningPath en archivos de texto plano

    public void guardarEnArchivo (File archivo) throws IOException{ // Guardar LearningPath en archivo de texto plano
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))){ // Crear un BufferedWriter para escribir en el archivo, BufferedWriter es una clase que permite escribir texto en un archivo de texto plano y se diferencia de FileWriter en que BufferedWriter es más eficiente para escribir grandes cantidades de texto como en este caso
            // Guardar atributos basicos
            writer.write(this.titulo + ","+ // Escribir el titulo del LearningPath
            this.nivelDificultad + ","+ // Escribir el nivel de dificultad del LearningPath
            this.descripcion + ","+ // Escribir la descripcion del LearningPath
            this.objetivos + ","+ // Escribir los objetivos del LearningPath
            this.duracionMinutos + ","+ // Escribir la duracion en minutos del LearningPath
            (this.fechaCreacion != null ? this.fechaCreacion.format(formatter):"")+","+ // Escribir la fecha de creacion del LearningPath
            (this.fechaModificacion != null ? this.fechaModificacion.format(formatter):"")+","+ // Escribir la fecha de modificacion del LearningPath
            this.version + ","+ // Escribir la version del LearningPath
            this.status + ","+ // Escribir el status del LearningPath
            this.rating + ","+ // Escribir el rating del LearningPath
            this.creador.getNombre() + ","+ // Escribir el nombre del creador del LearningPath
            this.progreso); // Escribir el progreso del LearningPath
        writer.newLine(); // Salto de linea

        // Guardar cada actividad en la lista de actividades del LearningPath
        for (Actividad actividad : listaActividades) {
            guardarActividad(actividad, writer);
        }
        
        // Guardar listaActividadesCompletadasConDup
        writer.write("COMPLETADAS_CON_DUP:");
        writer.newLine();
        for (Actividad actividad : listaActividadesCompletadasConDup) {
            guardarActividad(actividad, writer);
        }

        // Guardar listaActividadesCompletadas (sin duplicados)
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
        writer.write("Tarea"+ actividad.getDescripcion()+","+ 
        tarea.getNivelDificultad()+","+ 
        tarea.getObjetivo()+","+ 
        tarea.getDuracionEsperada()+","+ 
        tarea.getVersion()+","+ 
        (tarea.getFechaLimite() != null ? actividad.getFechaLimite().format(formatter):"")+","+ 
        (tarea.getFechaInicio() != null ? actividad.getFechaInicio().format(formatter):"")+","+
        tarea.getStatus()+","+ 
        tarea.esObligatoria()+","+ 
        tarea.getTipo()+","+
        tarea.getActividadesPreviasSugeridas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(","))+","+
        tarea.getActividadesSeguimientoRecomendadas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(","))+","+
        tarea.getSubmissionMethod()+","+ 
        tarea.getCreador().getNombre());
    } 
    else if (actividad instanceof Quiz)
    {
        Quiz quiz = (Quiz) actividad;   

        writer.write("Quiz"+ actividad.getDescripcion()+","+
        quiz.getNivelDificultad()+","+
        quiz.getObjetivo()+","+
        quiz.getDuracionEsperada()+","+
        quiz.getVersion()+","+
        (quiz.getFechaLimite() != null ? actividad.getFechaLimite().format(formatter):"")+","+
        (quiz.getFechaInicio() != null ? actividad.getFechaInicio().format(formatter):"")+","+
        quiz.getStatus()+","+
        quiz.esObligatoria()+","+
        quiz.getTipo()+","+
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
        writer.write("Recurso"+ actividad.getDescripcion()+","+
        recurso.getNivelDificultad()+","+
        recurso.getObjetivo()+","+
        recurso.getDuracionEsperada()+","+
        recurso.getVersion()+","+
        (recurso.getFechaLimite() != null ? actividad.getFechaLimite().format(formatter):"")+","+
        (recurso.getFechaInicio() != null ? actividad.getFechaInicio().format(formatter):"")+","+
        recurso.getStatus()+","+
        recurso.esObligatoria()+","+
        recurso.getTipo()+","+
        recurso.getActividadesPreviasSugeridas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(","))+","+
        recurso.getActividadesSeguimientoRecomendadas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(","))+","+
        recurso.getTipoRecurso()+","+
        recurso.getCreador().getNombre());
    } 
    else if (actividad instanceof Examen)
    {
        Examen examen = (Examen) actividad;
        writer.write("Examen"+ actividad.getDescripcion()+","+
        examen.getNivelDificultad()+","+
        examen.getObjetivo()+","+
        examen.getDuracionEsperada()+","+
        examen.getVersion()+","+
        (examen.getFechaLimite() != null ? actividad.getFechaLimite().format(formatter):"")+","+
        (examen.getFechaInicio() != null ? actividad.getFechaInicio().format(formatter):"")+","+
        examen.getStatus()+","+
        examen.esObligatoria()+","+
        examen.getTipo()+","+
        examen.getActividadesPreviasSugeridas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(","))+","+
        examen.getActividadesSeguimientoRecomendadas().stream().map(Actividad::getDescripcion).collect(Collectors.joining(","))+","+
        examen.getListaPreguntas().stream().map(Pregunta::getEnunciado).collect(Collectors.joining(","))+","+
        examen.getCalificacionMinima()+","+
        examen.getRespuestasCorrectas()+","+
        examen.getCalificacionObtenida()+","+
        examen.getRespuestasAbiertas().stream().collect(Collectors.joining(","))+","+
        examen.getCreador().getNombre());
    } 
    else if (actividad instanceof Encuesta)
    {
        Encuesta encuesta = (Encuesta) actividad;
        writer.write("Encuesta"+ actividad.getDescripcion()+","+
        encuesta.getNivelDificultad()+","+
        encuesta.getObjetivo()+","+
        encuesta.getDuracionEsperada()+","+
        encuesta.getVersion()+","+
        (encuesta.getFechaLimite() != null ? actividad.getFechaLimite().format(formatter):"")+","+
        (encuesta.getFechaInicio() != null ? actividad.getFechaInicio().format(formatter):"")+","+
        encuesta.getStatus()+","+
        encuesta.esObligatoria()+","+
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea = reader.readLine();
            if (linea != null) {
                String[] datos = linea.split(",");
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
                float progreso = Float.parseFloat(datos[10]);

                LearningPath learningPath = new LearningPath(titulo, nivelDificultad, descripcion, objetivos, duracionMinutos, creador, rating, new ArrayList<>());
                learningPath.fechaCreacion = fechaCreacion;
                learningPath.fechaModificacion = fechaModificacion;
                learningPath.version = version;
                learningPath.status = status;
                learningPath.progreso = progreso;

                // Listas para guardar las actividades durante la carga
                List<Actividad> currentList = learningPath.listaActividades;

                while ((linea = reader.readLine()) != null) {
                    if (linea.equals("COMPLETADAS_CON_DUP:")) {
                        currentList = learningPath.listaActividadesCompletadasConDup;
                        continue;
                    } else if (linea.equals("COMPLETADAS_SIN_DUP:")) {
                        currentList = learningPath.listaActividadesCompletadas;
                        continue;
                    }

                    // Cargar la actividad utilizando el método auxiliar
                    Actividad actividad = cargarActividad(linea, creador, formatter);
                    if (actividad != null) {
                        currentList.add(actividad);
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
        Obligatoria obligatoria = Obligatoria.valueOf(datos[9]);
        String submissionMethod = datos[10];
        List<Actividad> actividadesPreviasSugeridas = cargarActividades(datos[11], creador, formatter);
        List<Actividad> actividadesSeguimientoRecomendadas = cargarActividades(datos[12], creador, formatter);

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
        Obligatoria obligatoria = Obligatoria.valueOf(datos[9]);
        double calificacionMinima = Double.parseDouble(datos[10]);
        double calificacionObtenida = Double.parseDouble(datos[11]);
        List<Actividad> actividadesPreviasSugeridas = cargarActividades(datos[12], creador, formatter);
        List<Actividad> actividadesSeguimientoRecomendadas = cargarActividades(datos[13], creador, formatter);

        Quiz quiz= new Quiz(descripcion, nivel, objetivo, duracion, version, fechaLimite, status, obligatoria, new ArrayList<>(), calificacionMinima, creador, actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
        
        // Asignar los valores que no entran al constructor directamente como calificacion obtenida y fehca de inicio
        
        quiz.setFechaInicio(fechaInicio);
        quiz.setCalificacionObtenida(calificacionObtenida);

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
        Obligatoria obligatoria = Obligatoria.valueOf(datos[9]);
        String tipoRecurso = datos[10];
        List<Actividad> actividadesPreviasSugeridas = cargarActividades(datos[11], creador, formatter);
        List<Actividad> actividadesSeguimientoRecomendadas = cargarActividades(datos[12], creador, formatter);

        // Crear el recurso educativo con los datos cargados

        RecursoEducativo recurso = new RecursoEducativo(descripcion, nivel, objetivo, duracion, version, fechaLimite, status, obligatoria, tipoRecurso, creador, actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);

        // Asignar los valores que no entran al constructor directamente como fecha de inicio

        recurso.setFechaInicio(fechaInicio);

        return recurso;

    }

    private static Examen cargarExamen(String[] datos, Profesor creador, DateTimeFormatter formatter) {
        String descripcion = datos[1];
        Nivel nivel = Nivel.valueOf(datos[2]);
        String objetivo = datos[3];
        int duracion = Integer.parseInt(datos[4]);
        double version = Double.parseDouble(datos[5]);
        LocalDateTime fechaLimite = LocalDateTime.parse(datos[6], formatter);
        LocalDateTime fechaInicio = LocalDateTime.parse(datos[7], formatter);
        Status status = Status.valueOf(datos[8]);
        Obligatoria obligatoria = Obligatoria.valueOf(datos[9]);
        double calificacionMinima = Double.parseDouble(datos[10]);
        int respuestasCorrectas = Integer.parseInt(datos[11]);
        double calificacionObtenida = Double.parseDouble(datos[12]);
        List<String> respuestasAbiertas = Arrays.asList(datos[13].split(","));
        List<Actividad> actividadesPreviasSugeridas = cargarActividades(datos[14], creador, formatter);
        List<Actividad> actividadesSeguimientoRecomendadas = cargarActividades(datos[15], creador, formatter);

        Examen examen = new Examen(descripcion, nivel, objetivo, duracion, version, fechaLimite, status, obligatoria, new ArrayList<>(), calificacionMinima, creador, actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
        
        // Asignar los valores que no entran al constructor directamente como calificacion obtenida, respuestas correctas, respuestas abiertas y fecha de inicio

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
        Obligatoria obligatoria = Obligatoria.valueOf(datos[9]);
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


}

