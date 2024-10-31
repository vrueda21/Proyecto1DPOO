package persistencia;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import usuario.Profesor;
import LPRS.LearningPath;
import actividad.Actividad;

public class PersistenciaLearningPath{
    
    private static final String RUTA_ARCHIVO = "src/persistencia/learningPaths.txt"; // Path del archivo de texto donde se guardan los LearningPaths
    
    // Método para guardar un LearningPath en el archivo
    public static void guardarLearningPath(LearningPath learningPath) throws IOException {
        File archivo = new File(RUTA_ARCHIVO);
        archivo.getParentFile().mkdirs(); // Crear directorio "data" si no existe

        // Llamar directamente a `guardarEnArchivo` en LearningPath, pasándole el archivo
        learningPath.guardarEnArchivo(archivo);
    }

    // Método para cargar todos los LearningPaths del archivo
    public static List<LearningPath> cargarLearningPaths(Profesor creador) throws IOException {
        File archivo = new File(RUTA_ARCHIVO);
        List<LearningPath> learningPaths = new ArrayList<>();

        if (!archivo.exists()) {
            System.out.println("El archivo de learning paths no existe.");
            return learningPaths;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            LearningPath learningPath = LearningPath.cargarDeArchivo(archivo, creador);
            if (learningPath != null) {
                learningPaths.add(learningPath);
            }
        }

        return learningPaths;
    }

    // Método para borrar el archivo (por ejemplo, para reiniciar la persistencia)
    public static void borrarArchivo() throws IOException {
        File archivo = new File(RUTA_ARCHIVO);
        if (archivo.exists()) {
            archivo.delete();
        }
    }

}
