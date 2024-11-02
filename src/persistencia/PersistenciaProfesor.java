package persistencia;
import java.io.*;
import java.util.*;
import usuario.*;
import LPRS.LearningPath;

public class PersistenciaProfesor {

    // Método para guardar un profesor en el archivo especificado
    public static void guardarProfesor(Profesor profesor, File archivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            // Guardar datos básicos del profesor
            writer.write("PROFESOR," + profesor.getNombre() + "," + profesor.getContrasenia() + "," + profesor.getCorreo());
            writer.newLine();

            // Guardar los títulos de cada LearningPath creado por el profesor
            writer.write("LEARNING_PATHS:");
            writer.newLine();
            for (LearningPath learningPath : profesor.getLearningPathCreado()) {
                writer.write("LEARNING_PATH," + learningPath.getTitulo());  // Solo el título
                writer.newLine();
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
            List<LearningPath> learningPaths = new ArrayList<>();  // Solo almacenaremos títulos
            List<Estudiante> estudiantes = new ArrayList<>();

            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("FIN_PROFESOR")) {
                    if (profesorActual != null) {
                        profesorActual.setLearningPathCreado(learningPaths);  // Asignar los títulos de los LearningPaths
                        profesorActual.setEstudiantes(estudiantes);
                        profesores.add(profesorActual);
                    }
                    profesorActual = null;
                    learningPaths = new ArrayList<>();
                    estudiantes = new ArrayList<>();
                } else if (linea.startsWith("LEARNING_PATH,")) {
                    // Extraer solo el título del LearningPath
                    String titulo = linea.split(",")[1];
                    LearningPath lp = new LearningPath(titulo, null, "", "", 0, profesorActual, 0, new ArrayList<>());  // Crear LearningPath con solo el título
                    learningPaths.add(lp);
                } else if (linea.startsWith("ESTUDIANTE,")) {
                    String[] datosEstudiante = linea.split(",");
                    String nombre = datosEstudiante[1];
                    String correo = datosEstudiante[2];
                    Estudiante estudiante = new Estudiante(nombre, "", correo);  // Constructor de ejemplo
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
