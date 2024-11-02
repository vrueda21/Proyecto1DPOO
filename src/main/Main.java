package main;

import actividad.*;
import LPRS.LearningPath;
import pregunta.*;
import usuario.*;
import persistencia.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Definir archivo de persistencia para profesores, estudiantes y learning paths
        File archivoProfesores = new File("src/persistencia/profesores.txt");
        File archivoEstudiantes = new File("src/persistencia/estudiantes.txt");
        File archivoLearningPaths = new File("src/persistencia/learningPaths.txt");

        try {
            // === Crear datos iniciales y guardarlos en persistencia ===
            System.out.println("=== Creando y guardando datos iniciales ===");

            // Crear un profesor
            Profesor profesor = new Profesor("Juan", "1234", "juan@example.com", new ArrayList<>(), new ArrayList<>());
            
            // Crear un LearningPath y agregarlo al profesor
            LearningPath learningPath = new LearningPath(
                "Learning Path de Prueba", 
                Nivel.Intermedio, 
                "Un camino de aprendizaje completo", 
                "Mejorar habilidades de programación", 
                120, 
                profesor, 
                4.5f, 
                new ArrayList<>()
            );
            profesor.getLearningPathCreado().add(learningPath);

            // Crear estudiantes y agregarlos al profesor
            Estudiante estudiante1 = new Estudiante("Maria", "1234", "maria@example.com");
            Estudiante estudiante2 = new Estudiante("Carlos", "5678", "carlos@example.com");
            profesor.agregarEstudiante(estudiante1);
            profesor.agregarEstudiante(estudiante2);

            // Crear y agregar una Tarea al LearningPath
            Tarea tarea = new Tarea(
                "Tarea de Ejemplo", 
                Nivel.Intermedio, 
                "Resolver problemas de lógica", 
                60, 
                1.0, 
                LocalDateTime.now().plusDays(7), 
                Status.Incompleto, 
                Obligatoria.SI, 
                "LMS", 
                profesor, 
                new ArrayList<>(), 
                new ArrayList<>()
            );
            learningPath.agregarActividad(tarea);

            // Crear y agregar un Quiz al LearningPath
            List<PreguntaCerrada> preguntasQuiz = new ArrayList<>();
            PreguntaCerrada preguntaQuiz = new PreguntaCerrada("¿Cuál es el resultado de 2+2?");
            
            Dictionary<Opcion, String> opcionesQuiz = new Hashtable<>();
            opcionesQuiz.put(Opcion.A, "3");
            opcionesQuiz.put(Opcion.B, "4"); // Respuesta correcta
            opcionesQuiz.put(Opcion.C, "5");
            opcionesQuiz.put(Opcion.D, "6");

            preguntaQuiz.setOpcionA(opcionesQuiz);
            preguntaQuiz.setRespuesta(opcionesQuiz);  // Respuesta correcta
            preguntasQuiz.add(preguntaQuiz);

            Quiz quiz = new Quiz(
                "Quiz de Matemáticas Básicas", 
                Nivel.Principiante, 
                "Verificar habilidades básicas de matemáticas", 
                30, 
                1.0, 
                LocalDateTime.now().plusDays(5), 
                Status.Incompleto, 
                Obligatoria.NO, 
                preguntasQuiz, 
                60.0, 
                profesor, 
                new ArrayList<>(), 
                new ArrayList<>()
            );
            learningPath.agregarActividad(quiz);

            // Crear y agregar un RecursoEducativo al LearningPath
            RecursoEducativo recurso = new RecursoEducativo(
                "Video de Introducción a Java", 
                Nivel.Intermedio, 
                "Introducción a la programación en Java", 
                20, 
                1.0, 
                LocalDateTime.now().plusDays(10), 
                Status.Incompleto, 
                Obligatoria.NO, 
                "video", 
                profesor, 
                new ArrayList<>(), 
                new ArrayList<>()
            );
            learningPath.agregarActividad(recurso);

            // Guardar el profesor en el archivo de persistencia
            PersistenciaProfesor.guardarProfesor(profesor, archivoProfesores);
            // Guardar los estudiantes en el archivo de persistencia
            PersistenciaEstudiante.guardarEstudiante(estudiante1, archivoEstudiantes);
            PersistenciaEstudiante.guardarEstudiante(estudiante2, archivoEstudiantes);

            // Guardar el LearningPath con todas sus actividades en el archivo de persistencia
            PersistenciaLearningPath.guardarLearningPath(learningPath);

            // === Cargar datos desde la persistencia y mostrar detalles ===
            System.out.println("=== Cargando datos de persistencia ===");

            // Cargar y mostrar profesores
            List<Profesor> profesoresCargados = PersistenciaProfesor.cargarProfesores(archivoProfesores);
            for (Profesor p : profesoresCargados) {
                System.out.println("\nProfesor: " + p.getNombre());
                System.out.println("Correo: " + p.getCorreo());
                
                System.out.println("Learning Paths Creados:");
                for (LearningPath lp : p.getLearningPathCreado()) {
                    System.out.println(" - " + lp.getTitulo());
                }

                System.out.println("Estudiantes:");
                for (Estudiante e : p.getEstudiantes()) {
                    System.out.println(" - " + e.getNombre() + " (" + e.getCorreo() + ")");
                }
            }

            // Cargar y mostrar LearningPaths, incluyendo actividades
            List<LearningPath> learningPathsCargados = PersistenciaLearningPath.cargarLearningPaths(profesor);
            if (!learningPathsCargados.isEmpty()) {
                LearningPath learningPathCargado = learningPathsCargados.get(0);

                System.out.println("\n=== Verificación del Learning Path cargado ===");
                System.out.println("Título cargado: " + learningPathCargado.getTitulo());
                System.out.println("Descripción: " + learningPathCargado.getDescripcion());
                System.out.println("Objetivos: " + learningPathCargado.getObjetivos());
                System.out.println("Duración en minutos: " + learningPathCargado.getDuracionMinutos());

                // Imprimir detalles de cada actividad cargada
                System.out.println("\n=== Detalle de Actividades Cargadas ===");
                for (Actividad actividad : learningPathCargado.getListaActividades()) {
                    System.out.println("Actividad: " + actividad.getDescripcion());
                    System.out.println("  Tipo: " + (actividad instanceof Tarea ? "Tarea" : "Quiz"));
                    System.out.println("  Status: " + actividad.getStatus());
                    System.out.println();
                }
            } else {
                System.out.println("No se cargaron Learning Paths desde el archivo.");
            }

        } catch (Exception e) {
            System.out.println("Error durante la ejecución: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
