package main;

import actividad.*;
import LPRS.LearningPath;
import usuario.*;
import pregunta.Pregunta;
import pregunta.PreguntaCerrada;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Crear un profesor
        Profesor profesor = new Profesor("Juan", "1234", "juan@example.com");

        // Crear un LearningPath vacío
        LearningPath learningPath = new LearningPath("Learning Path de Ejemplo", Nivel.Intermedio, 
                "Descripción de Ejemplo", "Objetivo de Ejemplo", 60, profesor, 4.5f, new ArrayList<>());

        // Crear una tarea de prueba
        Tarea tarea = new Tarea("Tarea de Ejemplo", Nivel.Intermedio, "Objetivo de Ejemplo",
                60, 1.0, LocalDateTime.now().plusDays(7), Status.Incompleto, 
                Obligatoria.SI, "LMS", profesor, new ArrayList<>(), new ArrayList<>());

        // Agregar la tarea al LearningPath
        learningPath.agregarActividad(tarea);
        System.out.println("Tarea agregada al Learning Path.");

        // Crear un estudiante y registrarlo en el LearningPath
        Estudiante estudiante = new Estudiante("Maria", "1234", "maria@example.com");
        learningPath.inscripcionEstudiante(estudiante);
        System.out.println("Estudiante inscrito en el Learning Path.");

        // Simulación del estudiante enviando la tarea
        try {
            tarea.responder(estudiante, "LMS");
            System.out.println("La tarea ha sido enviada por el estudiante " + estudiante.getNombre());
        } catch (Exception e) {
            System.out.println("Error al enviar la tarea: " + e.getMessage());
        }

        // Simulación del profesor evaluando la tarea
        try {
            tarea.evaluar(profesor, estudiante, learningPath, 100.0, true); // Marcar la tarea como exitosa
            System.out.println("La tarea ha sido evaluada como exitosa.");
        } catch (Exception e) {
            System.out.println("Error al evaluar la tarea: " + e.getMessage());
        }

        // Registrar la actividad completada
        if (tarea.esExitosa(estudiante)) {
            learningPath.actividadObligatoriaCompletada(tarea);
            System.out.println("La actividad obligatoria ha sido completada.");
        }

        // Calcular y mostrar el progreso del Learning Path
        float progreso = learningPath.calcularProgreso(estudiante);
        System.out.println("Progreso actual del Learning Path: " + progreso + "%");
    }
}
