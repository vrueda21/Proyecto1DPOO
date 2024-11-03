package persistencia;

import java.io.*;
import java.util.*;
import LPRS.LearningPath;
import usuario.*;

public class PersistenciaEstudiante {

    // Método para guardar un estudiante en el archivo especificado
    public static void guardarEstudiante(Estudiante estudiante, File archivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            // Guardar datos básicos del estudiante
            writer.write("ESTUDIANTE," + estudiante.getNombre() + "," + estudiante.getContrasenia() + "," + estudiante.getCorreo());
            writer.newLine();

            // Guardar el título del LearningPath actual si está siguiendo uno
            if (estudiante.getLearningPathActual() != null) {
                writer.write("LEARNING_PATH_ACTUAL:" + estudiante.getLearningPathActual().getTitulo());
                writer.newLine();
            }

            // Guardar títulos de LearningPaths completados
            writer.write("LEARNING_PATHS_COMPLETADOS:");
            for (LearningPath learningPath : estudiante.listaLearningPathsCompletados) {
                writer.write(learningPath.getTitulo() + ";");
            }
            writer.newLine();

            writer.write("FIN_ESTUDIANTE");
            writer.newLine();
        }
    }

    // Método para cargar los estudiantes desde el archivo especificado
    public static List<Estudiante> cargarEstudiantes(File archivo, Map<String, LearningPath> learningPathsDisponibles) throws IOException {
        List<Estudiante> estudiantes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            Estudiante estudianteActual = null;
            List<LearningPath> learningPathsCompletados = new ArrayList<>();

            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("FIN_ESTUDIANTE")) {
                    if (estudianteActual != null) {
                        estudianteActual.listaLearningPathsCompletados = learningPathsCompletados;
                        estudiantes.add(estudianteActual);
                    }
                    estudianteActual = null;
                    learningPathsCompletados = new ArrayList<>();
                } else if (linea.startsWith("LEARNING_PATH_ACTUAL:")) {
                    if (estudianteActual != null) {
                        String tituloLP = linea.split(":")[1];
                        LearningPath lpActual = learningPathsDisponibles.get(tituloLP);
                        if (lpActual != null) {
                            estudianteActual.setLearningPathActual(lpActual);
                        }
                    }
                } else if (linea.startsWith("LEARNING_PATHS_COMPLETADOS:")) {
                    if (estudianteActual != null) {
                        String[] titulosCompletados = linea.split(":")[1].split(";");
                        for (String titulo : titulosCompletados) {
                            LearningPath lpCompletado = learningPathsDisponibles.get(titulo);
                            if (lpCompletado != null) {
                                learningPathsCompletados.add(lpCompletado);
                            }
                        }
                    }
                } else if (linea.startsWith("ESTUDIANTE,")) {
                    String[] datos = linea.split(",");
                    String nombre = datos[1];
                    String contrasenia = datos[2];
                    String correo = datos[3];
                    estudianteActual = new Estudiante(nombre, contrasenia, correo);
                }
            }
        }

        return estudiantes;
    }
}
