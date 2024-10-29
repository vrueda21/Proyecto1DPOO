package main;

import actividad.*;
import LPRS.LearningPath;
import usuario.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Crear un profesor
        Profesor profesor = new Profesor("Juan", "1234", "juan@example.com");

        // Crear un LearningPath vacío
        LearningPath learningPath = new LearningPath("Learning Path de Ejemplo");

        // Crear una tarea de prueba
        Tarea tarea = new Tarea("Tarea de Ejemplo", Nivel.Intermedio, "Objetivo de Ejemplo",
                60, 1.0, LocalDateTime.now().plusDays(7), Status.Incompleto, 
                Obligatoria.SI, "LMS", profesor, new ArrayList<>(), new ArrayList<>());

        // Agregar la actividad al LearningPath
        learningPath.agregarActividad(tarea);

        // Crear un estudiante y registrarlo en el LearningPath
        Estudiante estudiante = new Estudiante("Maria", "1234", "maria@example.com");
        learningPath.agregarEstudiante(estudiante);

        // Simulación del estudiante enviando la tarea
        try {
            tarea.marcarEnviada(estudiante, "LMS");
        } catch (Exception e) {
            System.out.println("Error al enviar la tarea: " + e.getMessage());
        }

        // Simulación del profesor evaluando la tarea
        try {
            tarea.evaluarTarea(profesor, true); // Aquí el profesor marca la tarea como exitosa
        } catch (Exception e) {
            System.out.println("Error al evaluar la tarea: " + e.getMessage());
        }

        // Verificar el estado final de la tarea
        System.out.println("Estado final de la tarea: " + tarea.getStatus());
    }
}
