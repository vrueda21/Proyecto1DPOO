package persistencia;

import LPRS.*;
import actividad.*;
import usuario.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

public class TestPersistenciaLearningPath {

    public static void main(String[] args) {
        try {
            // Paso 1: Crear un profesor y un LearningPath de prueba
            Profesor profesor = new Profesor("Juan", "1234", "juan@example.com");
            LearningPath learningPathOriginal = new LearningPath(
                "Learning Path de Prueba", 
                Nivel.Intermedio, 
                "Descripción de prueba", 
                "Objetivos de prueba", 
                120, 
                profesor, 
                4.5f, 
                new ArrayList<>()
            );

            System.out.println("=== Creando actividades de prueba ===");

            // Paso 2: Agregar actividades al LearningPath
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

            Quiz quiz = new Quiz(
                "Quiz de Matemáticas Básicas", 
                Nivel.Principiante, 
                "Verificar habilidades básicas de matemáticas", 
                30, 
                1.0, 
                LocalDateTime.now().plusDays(5), 
                Status.Incompleto, 
                Obligatoria.NO, 
                new ArrayList<>(), 
                60.0, 
                profesor, 
                new ArrayList<>(), 
                new ArrayList<>()
            );

            // Agregar actividades al LearningPath original
            learningPathOriginal.agregarActividad(tarea);
            learningPathOriginal.agregarActividad(quiz);

            System.out.println("=== Guardando el Learning Path en el archivo ===");
            PersistenciaLearningPath.guardarLearningPath(learningPathOriginal);

            // Paso 3: Cargar el LearningPath desde el archivo
            System.out.println("=== Cargando el Learning Path desde el archivo ===");
            List<LearningPath> learningPathsCargados = PersistenciaLearningPath.cargarLearningPaths(profesor);

            // Paso 4: Verificar el resultado cargado
            if (!learningPathsCargados.isEmpty()) {
                LearningPath learningPathCargado = learningPathsCargados.get(0);

                System.out.println("\n=== Verificación del Learning Path cargado ===");
                
                // Comparar el título
                System.out.println("Título cargado: " + learningPathCargado.getTitulo());
                System.out.println("Título original: " + learningPathOriginal.getTitulo());
                System.out.println("¿Los títulos coinciden? " + learningPathCargado.getTitulo().equals(learningPathOriginal.getTitulo()));

                // Comparar el número de actividades
                System.out.println("Número de actividades cargadas: " + learningPathCargado.getListaActividades().size());
                System.out.println("Número de actividades originales: " + learningPathOriginal.getListaActividades().size());
                System.out.println("¿El número de actividades coincide? " + (learningPathCargado.getListaActividades().size() == learningPathOriginal.getListaActividades().size()));

                // Imprimir detalles de cada actividad cargada
                System.out.println("\n=== Detalle de Actividades Cargadas ===");
                for (int i = 0; i < learningPathCargado.getListaActividades().size(); i++) {
                    Actividad actividadCargada = learningPathCargado.getListaActividades().get(i);
                    Actividad actividadOriginal = learningPathOriginal.getListaActividades().get(i);
                    System.out.println("Actividad " + (i + 1) + ":");

                    System.out.println("  Descripción cargada: " + actividadCargada.getDescripcion());
                    System.out.println("  Descripción original: " + actividadOriginal.getDescripcion());
                    System.out.println("  ¿Descripción coincide? " + actividadCargada.getDescripcion().equals(actividadOriginal.getDescripcion()));

                    System.out.println("  Tipo de actividad: " + (actividadCargada instanceof Tarea ? "Tarea" : "Quiz"));

                    if (actividadCargada instanceof Tarea && actividadOriginal instanceof Tarea) {
                        Tarea tareaCargada = (Tarea) actividadCargada;
                        Tarea tareaOriginal = (Tarea) actividadOriginal;
                        System.out.println("  Duración cargada: " + tareaCargada.getDuracionEsperada());
                        System.out.println("  Duración original: " + tareaOriginal.getDuracionEsperada());
                        System.out.println("  ¿Duración coincide? " + (tareaCargada.getDuracionEsperada() == tareaOriginal.getDuracionEsperada()));
                    } else if (actividadCargada instanceof Quiz && actividadOriginal instanceof Quiz) {
                        Quiz quizCargado = (Quiz) actividadCargada;
                        Quiz quizOriginal = (Quiz) actividadOriginal;
                        System.out.println("  Calificación mínima cargada: " + quizCargado.getCalificacionMinima());
                        System.out.println("  Calificación mínima original: " + quizOriginal.getCalificacionMinima());
                        System.out.println("  ¿Calificación mínima coincide? " + (quizCargado.getCalificacionMinima() == quizOriginal.getCalificacionMinima()));
                    }

                    System.out.println(); // Línea en blanco para separar actividades
                }

                // Puedes agregar más comparaciones según los atributos que desees verificar
            } else {
                System.out.println("No se cargaron Learning Paths desde el archivo.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
