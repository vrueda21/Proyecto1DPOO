package main;

import actividad.*;
import LPRS.LearningPath;
import pregunta.*;
import usuario.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Hashtable;
import java.util.Dictionary;

public class Main {

    public static void main(String[] args) {
        
        // Crear un profesor
        Profesor profesor = new Profesor("Juan", "1234", "juan@example.com");
        
        // Crear un LearningPath
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

        // Crear estudiantes
        Estudiante estudiante1 = new Estudiante("Maria", "1234", "maria@example.com");
        Estudiante estudiante2 = new Estudiante("Carlos", "5678", "carlos@example.com");

        // Inscribir estudiantes en el LearningPath
        learningPath.inscripcionEstudiante(estudiante1);
        learningPath.inscripcionEstudiante(estudiante2);
        
        // Crear una Tarea y agregarla al LearningPath
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

        // Crear un Quiz y agregarlo al LearningPath
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

        // Crear un RecursoEducativo y agregarlo al LearningPath
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

        // Responder actividades por parte del estudiante 1
        System.out.println("\n== Simulación de Respuestas de Estudiante 1 ==");

        // Responder Tarea
        tarea.responder(estudiante1, "LMS");
        learningPath.actividadObligatoriaCompletada(tarea);

        // Responder Quiz
        quiz.responder(estudiante1, "1:B"); // Respuesta correcta
        if (quiz.esExitosa(estudiante1)) {
            learningPath.actividadObligatoriaCompletada(quiz);
        }

        // Marcar recurso educativo como revisado
        recurso.responder(estudiante1, "visto");
        if (recurso.esExitosa(estudiante1)) {
            learningPath.actividadObligatoriaCompletada(recurso);
        }

        // Evaluar Tarea por el profesor
        profesor.evaluarTarea(tarea, estudiante1, learningPath, 80.0, true);  // Evaluar como exitosa
        learningPath.actividadObligatoriaCompletada(tarea);

        // Imprimir progreso del LearningPath
        System.out.println("\n== Detalles del Learning Path ==");
        System.out.println("Título: " + learningPath.getTitulo());
        System.out.println("Estudiantes Inscritos: " + learningPath.getEstudiantesInscritos().size());
        System.out.println("Actividades en el Learning Path: " + learningPath.getListaActividades().size());
        System.out.println("Progreso del Estudiante 1: " + learningPath.calcularProgreso(estudiante1) + "%");
        System.out.println("Progreso del Estudiante 2: " + learningPath.calcularProgreso(estudiante2) + "%");

        // Verificar el estado final de la tarea y quiz
        System.out.println("\n== Estado de las Actividades ==");
        System.out.println("Estado de la Tarea: " + tarea.getStatus());
        System.out.println("Estado del Quiz: " + quiz.getStatus());
        System.out.println("Estado del Recurso Educativo: " + recurso.getStatus());

        // Simular el intento de reintentar una tarea
        System.out.println("\n== Reintento de Tarea por Estudiante 1 ==");
        try {
            tarea.reintentar(estudiante1);
        } catch (Exception e) {
            System.out.println("Error al reintentar la tarea: " + e.getMessage());
        }

        // Imprimir resultados finales del LearningPath
        System.out.println("\n== Resultados Finales ==");
        System.out.println("Progreso del Estudiante 1: " + learningPath.calcularProgreso(estudiante1) + "%");
        System.out.println("Progreso del Estudiante 2: " + learningPath.calcularProgreso(estudiante2) + "%");
    }
}
