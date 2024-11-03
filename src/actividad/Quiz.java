package actividad;

import pregunta.PreguntaCerrada;
import usuario.Estudiante;
import usuario.Profesor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import LPRS.LearningPath;

public class Quiz extends Actividad {

    protected List<PreguntaCerrada> listaPreguntas; // Lista de preguntas cerradas
    protected double calificacionMinima; // Calificación mínima para aprobar
    private double calificacionObtenida; // Nota final obtenida por el estudiante

    public Quiz(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, 
                double version, LocalDateTime fechaLimite, Map<Estudiante, Status> estadosPorEstudiante, Obligatoria obligatoria, 
                List<PreguntaCerrada> listaPreguntas, double calificacionMinima, Profesor creador, 
                List<Actividad> actividadesPreviasSugeridas, List<Actividad> actividadesSeguimientoRecomendadas) {
        super(descripcion, nivelDificultad, objetivo, duracionEsperada, version, 
              fechaLimite, estadosPorEstudiante, obligatoria, "quiz", creador, 
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
    
        Status estadoEstudiante = estadosPorEstudiante.get(estudiante);
        if (estadoEstudiante == Status.Completado) {
            throw new UnsupportedOperationException("El quiz ya ha sido completado exitosamente y no se puede repetir.");
        }
    
        System.out.println("Respuestas recibidas: " + respuesta);

        int preguntasCorrectas = 0; // Contador de respuestas correctas
    
        // Dividir las respuestas según el formato esperado "1:A,2:B,3:C"
        String[] respuestas = respuesta.split(",");
    
        for (String respuestaEstudiante : respuestas) {
            String[] partes = respuestaEstudiante.split(":");
            int indicePregunta = Integer.parseInt(partes[0]) - 1; // Convertir índice de pregunta (1-based index)
            String opcionSeleccionada = partes[1].trim(); // Opción seleccionada por el estudiante
    
            System.out.println("Opcion desglosada:" + opcionSeleccionada);
            // Asegurarse de que el índice de la pregunta sea válido
            if (indicePregunta >= 0 && indicePregunta < listaPreguntas.size()) {
                PreguntaCerrada pregunta = listaPreguntas.get(indicePregunta);
    
                // Marcar la opción elegida por el estudiante y verificar si es correcta
                pregunta.elegirRespuesta(opcionSeleccionada);
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
            System.out.println("El quiz fue completado exitosamente con una calificación de " + calificacionObtenida + "%.");
            setStatusParaEstudiante(estudiante, Status.Exitosa);
        } else {
            estadosPorEstudiante.put(estudiante, Status.noExitosa);
            System.out.println("El quiz no fue aprobado. Calificación obtenida: " + calificacionObtenida + "%.");
        }
    }
    

    // Método para verificar si el quiz es exitoso (cumple la calificación mínima)
    @Override
    public boolean esExitosa(Estudiante estudiante) {
        Status estadoActual = getStatusParaEstudiante(estudiante);

        if (estadoActual == Status.Exitosa || estadoActual == Status.Completado) {
            System.out.println("El quiz fue completado exitosamente por: " + estudiante.getNombre());
            estudiante.agregarActividadCompletada(this);
            setStatusParaEstudiante(estudiante, Status.Completado);
            return true;
        } else {
            System.out.println("El quiz no fue aprobado por: " + estudiante.getNombre());
            return false;
        }
    }

    // Método para reintentar el quiz
    @Override
    public void reintentar(Estudiante estudiante) {
        Status estadoActual = getStatusParaEstudiante(estudiante);
        if (estadoActual == Status.Exitosa) {
            throw new UnsupportedOperationException("El quiz ya fue completado exitosamente y no se puede repetir.");
        } else {
            System.out.println("El estudiante " + estudiante.getNombre() + " puede iniciar o volver a intentar el quiz.");
            // Reiniciar el estado del quiz para reintento
            this.calificacionObtenida = 0.0;
            for (PreguntaCerrada pregunta : listaPreguntas) {
                pregunta.setEscogida(null); // Reiniciar la respuesta elegida
            }

            setStatusParaEstudiante(estudiante, Status.Incompleto);
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
