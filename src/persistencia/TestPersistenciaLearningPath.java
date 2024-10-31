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

            learningPathOriginal.agregarActividad(tarea);
            learningPathOriginal.agregarActividad(quiz);

            // Paso 3: Guardar el LearningPath en el archivo
            System.out.println("Guardando el Learning Path en el archivo...");
            PersistenciaLearningPath.guardarLearningPath(learningPathOriginal);

            // Paso 4: Cargar el LearningPath desde el archivo
            System.out.println("Cargando el Learning Path desde el archivo...");
            List<LearningPath> learningPathsCargados = PersistenciaLearningPath.cargarLearningPaths(profesor);

            // Paso 5: Verificar el resultado cargado
            if (!learningPathsCargados.isEmpty()) {
                LearningPath learningPathCargado = learningPathsCargados.get(0);

                // Comparar el título como ejemplo para verificar que los datos coinciden
                System.out.println("Título del Learning Path cargado: " + learningPathCargado.getTitulo());
                System.out.println("Título del Learning Path original: " + learningPathOriginal.getTitulo());
                System.out.println("Comparación de los títulos: " + learningPathCargado.getTitulo().equals(learningPathOriginal.getTitulo()));

                // Comparar la cantidad de actividades
                System.out.println("Número de actividades cargadas: " + learningPathCargado.getListaActividades().size());
                System.out.println("Número de actividades originales: " + learningPathOriginal.getListaActividades().size());
                System.out.println("Comparación de actividades: " + (learningPathCargado.getListaActividades().size() == learningPathOriginal.getListaActividades().size()));

                // Puedes agregar más comparaciones según los atributos que desees verificar
            } else {
                System.out.println("No se cargaron Learning Paths desde el archivo.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
