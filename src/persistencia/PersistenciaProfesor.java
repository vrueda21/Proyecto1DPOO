package persistencia;
import java.io.*;
import java.util.*;
import LPRS.LearningPath;
import usuario.*;

public class PersistenciaProfesor {

    // Método para guardar un profesor en el archivo especificado
    public static void guardarProfesor(Profesor profesor, File archivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            // Guardar datos básicos del profesor
            writer.write("PROFESOR," + profesor.getNombre() + "," + profesor.getContrasenia() + "," + profesor.getCorreo());
            writer.newLine();

            // Guardar cada LearningPath creado por el profesor
            for (LearningPath learningPath : profesor.getLearningPathCreado()) {
                writer.write("LEARNING_PATH:");
                writer.newLine();
                learningPath.guardarEnArchivo(archivo); // Ahora llamamos a guardarEnArchivo en LearningPath pasando el archivo
            }

            // Guardar lista de estudiantes asociados al profesor
            writer.write("ESTUDIANTES:");
            writer.newLine();
            for (Estudiante estudiante : profesor.getEstudiantes()) {
                writer.write("ESTUDIANTE," + estudiante.getNombre() + "," + estudiante.getCorreo());
                writer.newLine();
            }

            writer.write("FIN_PROFESOR");
            writer.newLine();
        }
    }

    // Método para cargar los profesores desde el archivo especificado
    public static List<Profesor> cargarProfesores(File archivo) throws IOException {
        List<Profesor> profesores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            Profesor profesorActual = null;
            List<LearningPath> learningPaths = new ArrayList<>();
            List<Estudiante> estudiantes = new ArrayList<>();

            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("FIN_PROFESOR")) {
                    if (profesorActual != null) {
                        profesorActual.setLearningPathCreado(learningPaths);
                        profesorActual.setEstudiantes(estudiantes);
                        profesores.add(profesorActual);
                    }
                    profesorActual = null;
                    learningPaths = new ArrayList<>();
                    estudiantes = new ArrayList<>();
                } else if (linea.startsWith("LEARNING_PATH:")) {
                    LearningPath lp = LearningPath.cargarDeArchivo(archivo, profesorActual); // Usamos el método de carga de LearningPath
                    if (lp != null) {
                        learningPaths.add(lp);
                    }
                } else if (linea.startsWith("ESTUDIANTE,")) {
                    String[] datosEstudiante = linea.split(",");
                    String nombre = datosEstudiante[1];
                    String correo = datosEstudiante[2];
                    Estudiante estudiante = new Estudiante(nombre, "", correo); // Constructor de ejemplo
                    estudiantes.add(estudiante);
                } else if (linea.startsWith("PROFESOR,")) {
                    String[] datos = linea.split(",");
                    String nombre = datos[1];
                    String contrasena = datos[2];
                    String correo = datos[3];
                    profesorActual = new Profesor(nombre, contrasena, correo, new ArrayList<>(), new ArrayList<>());
                }
            }
        }

        return profesores;
    }
}
