package actividad;

import pregunta.PreguntaCerrada;
import usuario.Estudiante;
import usuario.Profesor;
import java.time.LocalDateTime;
import java.util.List;

import LPRS.LearningPath;

public class Quiz extends Actividad {

    protected List<PreguntaCerrada> listaPreguntas; // Lista de preguntas cerradas
    protected double calificacionMinima; // Calificación mínima para aprobar
    private double calificacionObtenida; // Nota final obtenida por el estudiante

    public Quiz(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, 
                double version, LocalDateTime fechaLimite, Status status, Obligatoria obligatoria, 
                List<PreguntaCerrada> listaPreguntas, double calificacionMinima, Profesor creador, 
                List<Actividad> actividadesPreviasSugeridas, List<Actividad> actividadesSeguimientoRecomendadas) {
        super(descripcion, nivelDificultad, objetivo, duracionEsperada, version, 
              fechaLimite, status, obligatoria, "quiz", creador, 
              actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
        this.listaPreguntas = listaPreguntas;
        this.calificacionMinima = calificacionMinima;
        this.calificacionObtenida = 0.0;
    }

    // Getters 

    public List<PreguntaCerrada> getListaPreguntas() {
        return listaPreguntas;
    }

    public double getCalificacionMinima() {
        return calificacionMinima;
    }

    public double getCalificacionObtenida() {
        return calificacionObtenida;
    }

    // Setters que solo pueden ser usados por profesores, administradores, o por la persistencia de datos.

    public void setListaPreguntas(List<PreguntaCerrada> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    public void setCalificacionMinima(double calificacionMinima) {
        this.calificacionMinima = calificacionMinima;
    }

    public void setCalificacionObtenida(double calificacionObtenida) {
        this.calificacionObtenida = calificacionObtenida;
    }

    
    

    
    // Método para responder al quiz completo
    @Override
    public void responder(Estudiante estudiante, String respuesta) {
        if (estudiante == null) {
            throw new SecurityException("Se requiere un estudiante para completar el quiz.");
        }

        if (this.status == Status.Exitosa) {
            throw new UnsupportedOperationException("El quiz ya ha sido completado exitosamente y no se puede repetir.");
        }

        int preguntasCorrectas = 0; // Contador de respuestas correctas

        // La respuesta del estudiante debe seguir el formato "1:A,2:B,3:C"
        String[] respuestas = respuesta.split(",");

        for (String respuestaEstudiante : respuestas) {
            String[] partes = respuestaEstudiante.split(":");
            int indicePregunta = Integer.parseInt(partes[0]) - 1; // Convertir índice de pregunta
            String opcionSeleccionada = partes[1]; // Opción seleccionada por el estudiante

            if (indicePregunta >= 0 && indicePregunta < listaPreguntas.size()) {
                PreguntaCerrada pregunta = listaPreguntas.get(indicePregunta);

                // Marcar la opción elegida por el estudiante
                try {
                    pregunta.elegirRespuesta(opcionSeleccionada);
                } catch (IllegalArgumentException e) {
                    System.out.println("Opción no válida para la pregunta " + (indicePregunta + 1) + ". " + e.getMessage());
                    continue; // Continuar con la siguiente pregunta
                }

                // Verificar si la respuesta es correcta
                if (pregunta.esCorrecta()) {
                    preguntasCorrectas++;
                }

                // Mostrar retroalimentación de la pregunta
                System.out.println("Pregunta " + (indicePregunta + 1) + ": " + pregunta.getRetroalimentacion());
            } else {
                System.out.println("Índice de pregunta no válido: " + (indicePregunta + 1));
            }
        }

        // Calcular la nota final obtenida
        calificacionObtenida = ((double) preguntasCorrectas / listaPreguntas.size()) * 100;

        // Verificar si se alcanzó la calificación mínima para aprobar
        if (calificacionObtenida >= calificacionMinima) {
            this.status = Status.Exitosa;
            System.out.println("El quiz fue completado exitosamente con una calificación de " + calificacionObtenida + "%.");
        } else {
            this.status = Status.noExitosa;
            System.out.println("El quiz no fue aprobado. Calificación obtenida: " + calificacionObtenida + "%.");
        }
    }

    // Método para verificar si el quiz es exitoso (cumple la calificación mínima)
    @Override
    public boolean esExitosa(Estudiante estudiante) {
        if (this.status == Status.Exitosa) {
            System.out.println("El quiz fue completado exitosamente por: " + estudiante.getNombre());
            return true;
        } else {
            System.out.println("El quiz no fue aprobado por: " + estudiante.getNombre());
            return false;
        }
    }

    // Método para reintentar el quiz
    @Override
    public void reintentar(Estudiante estudiante) {
        if (this.status == Status.Exitosa) {
            throw new UnsupportedOperationException("El quiz ya fue completado exitosamente y no se puede repetir.");
        } else {
            System.out.println("El estudiante " + estudiante.getNombre() + " puede iniciar o volver a intentar el quiz.");
            // Reiniciar el estado del quiz para reintento
            this.calificacionObtenida = 0.0;
            for (PreguntaCerrada pregunta : listaPreguntas) {
                pregunta.setEscogida(null); // Reiniciar la respuesta elegida
            }
            this.status = Status.Incompleto;
        }
    }

    // Implementación vacía del método evaluar, ya que no se requiere para Quiz
    @Override
        public void evaluar(Profesor profesor, Estudiante estudiante, LearningPath learningPath, double calificacionObtenida, boolean exitosa) {
        // No se necesita implementación para Quiz
        System.out.println("El profesor " + profesor.getNombre() + " no puede evaluar el quiz.");
    }

    // Método para agregar una pregunta al quiz

    public void agregarPregunta(PreguntaCerrada pregunta) {
        if (pregunta == null) {
            throw new IllegalArgumentException("La pregunta no puede ser nula.");
        }

        listaPreguntas.add(pregunta);
    }

    // Método para eliminar una pregunta del quiz

    public void eliminarPregunta(PreguntaCerrada pregunta) {
        if (pregunta == null) {
            throw new IllegalArgumentException("La pregunta no puede ser nula.");
        }

        // Si la cantidad de preguntas es 0 o 1, no se puede eliminar más porque teiene que haber al menos una pregunta
        if (listaPreguntas.size() <= 1) {
            throw new UnsupportedOperationException("El quiz debe tener al menos una pregunta.");
        }

        listaPreguntas.remove(pregunta);
    }
}
