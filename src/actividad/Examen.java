package actividad;

import pregunta.Pregunta;
import pregunta.PreguntaCerrada;
import pregunta.PreguntaAbierta;
import usuario.Estudiante;
import usuario.Profesor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import LPRS.LearningPath;

public class Examen extends Actividad 

{

    protected List<Pregunta> listaPreguntas;
    protected double calificacionMinima; // Calificación mínima para aprobar
    private int respuestasCorrectas; // Cantidad de respuestas correctas
    private double calificacionObtenida; // Nota final obtenida por el estudiante
    private List<String> respuestasAbiertas; // Lista de respuestas abiertas del estudiante

    public Examen(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, 
                  double version, LocalDateTime fechaLimite, Status status, Obligatoria obligatoria, 
                  List<Pregunta> listaPreguntas, double calificacionMinima, Profesor creador, 
                  List<Actividad> actividadesPreviasSugeridas, List<Actividad> actividadesSeguimientoRecomendadas) {
        super(descripcion, nivelDificultad, objetivo, duracionEsperada, version, 
              fechaLimite, status, obligatoria, "examen", creador, 
              actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
        this.listaPreguntas = listaPreguntas;
        this.calificacionMinima = calificacionMinima;
        this.calificacionObtenida = 0.0;
        this.respuestasCorrectas = 0;
        this.respuestasAbiertas = new ArrayList<>();
    }

    // Getters
    public List<Pregunta> getListaPreguntas() {
        return listaPreguntas;
    }

    public double getCalificacionMinima() {
        return calificacionMinima;
    }

    public double getCalificacionObtenida() {
        return calificacionObtenida;
    }

    public int getRespuestasCorrectas() {
        return respuestasCorrectas;
    }

    public List<String> getRespuestasAbiertas() {
        return respuestasAbiertas;
    }

    // Setters

    public void setListaPreguntas(List<Pregunta> listaPreguntas) {
        this.listaPreguntas = listaPreguntas;
    }

    public void setCalificacionMinima(double calificacionMinima) {
        this.calificacionMinima = calificacionMinima;
    }

    public void setCalificacionObtenida(double calificacionObtenida) {
        this.calificacionObtenida = calificacionObtenida;
    }

    public void setRespuestasCorrectas(int respuestasCorrectas) {
        this.respuestasCorrectas = respuestasCorrectas;
    }

    public void setRespuestasAbiertas(List<String> respuestasAbiertas) {
        this.respuestasAbiertas = respuestasAbiertas;
    }
    // Metodo para que el estudiante responda el examen

    @Override
    public void responder(Estudiante estudiante, String respuestas) {
        if (estudiante == null) {
            throw new SecurityException("Se requiere un estudiante para completar el examen.");
        }
    
        // Dividir las respuestas del estudiante
        String[] respuestasEstudiante = respuestas.split(";");
    
        // Verificar si la cantidad de respuestas coincide con la cantidad de preguntas
        if (respuestasEstudiante.length != listaPreguntas.size()) {
            throw new IllegalArgumentException("La cantidad de respuestas no coincide con la cantidad de preguntas en el examen.");
        }
    
        // Inicializar un contador para respuestas correctas
        int respuestasCorrectas = 0;
    
        // Iterar sobre las preguntas y las respuestas del estudiante
        for (int i = 0; i < respuestasEstudiante.length; i++) {
            Pregunta pregunta = listaPreguntas.get(i);
            String respuestaEstudiante = respuestasEstudiante[i];
    
            // Si es una pregunta cerrada
            if (pregunta instanceof PreguntaCerrada) {
                PreguntaCerrada preguntaCerrada = (PreguntaCerrada) pregunta;
                preguntaCerrada.elegirRespuesta(respuestaEstudiante);
    
                // Si la respuesta es correcta, incrementar el contador de respuestas correctas
                if (preguntaCerrada.esCorrecta()) {
                    respuestasCorrectas++;
                }
    
            // Si es una pregunta abierta
            } else if (pregunta instanceof PreguntaAbierta) {
                PreguntaAbierta preguntaAbierta = (PreguntaAbierta) pregunta;
                // Guardar la respuesta para evaluación manual
                respuestasAbiertas.add(respuestaEstudiante);
                System.out.println("La pregunta abierta debe ser evaluada por un profesor.");
            }
        }
    
        // Guardar la cantidad de respuestas correctas como un atributo para el cálculo final
        this.respuestasCorrectas = respuestasCorrectas;
    
        // Cambiar el estado del examen a enviado
        this.status = Status.Enviada;
        System.out.println("El examen ha sido enviado por: " + estudiante.getNombre());
    }
    
    // Metodo para que el profesor evalúe las preguntas abiertas del examen

        @Override
        public void evaluar(Profesor profesor, Estudiante estudiante, LearningPath learningPath, double calificacionObtenida, boolean exitosa) {
            if (profesor == null || !profesor.equals(creador)) {
                throw new SecurityException("Solo el profesor creador puede evaluar el examen.");
            }

            // Verificación de si el estudiante está inscrito en el Learning Path
            if (!learningPath.verificarSiInscrito(estudiante)) {
                throw new IllegalArgumentException("El estudiante no está inscrito en el Learning Path para este examen.");
            }

            // Advertencia de que la calificación proporcionada será ignorada
            System.out.println("Advertencia: la calificación proporcionada (" + calificacionObtenida + ") se ignorará en el examen, ya que se calcula automáticamente.");

            // Evaluar cada respuesta de pregunta abierta
            for (Pregunta pregunta : listaPreguntas) {
                if (pregunta instanceof PreguntaAbierta) {
                    PreguntaAbierta preguntaAbierta = (PreguntaAbierta) pregunta;
                    boolean respuestaCorrecta = preguntaAbierta.esCorrecta(); // Verificar si la respuesta es correcta
                    if (respuestaCorrecta) {
                        respuestasCorrectas++; // Incrementar el contador si la respuesta es correcta
                    }
                }
            }

            // Calcular la calificación final después de evaluar todas las preguntas abiertas
            calcularCalificacionFinal();

            // Verificar si el examen fue aprobado o no
            if (this.calificacionObtenida >= this.calificacionMinima) {
                this.status = Status.Exitosa;
                System.out.println("El examen ha sido aprobado con una nota de " + calificacionObtenida + "%.");
            } else {
                this.status = Status.noExitosa;
                System.out.println("El examen no ha sido aprobado. Nota obtenida: " + calificacionObtenida + "%.");
            }
        }


    // Método para calcular la calificación final del examen
    private void calcularCalificacionFinal() {
        int totalPreguntas = listaPreguntas.size(); // Total de preguntas en el examen

        // Calcular la nota obtenida como porcentaje de respuestas correctas
        calificacionObtenida = ((double) respuestasCorrectas / totalPreguntas) * 100;
    }

    // Método para verificar si el examen es exitoso
    @Override
    public boolean esExitosa(Estudiante estudiante) {
        if (status == Status.Exitosa) {
            System.out.println("El examen fue completado exitosamente por: " + estudiante.getNombre() + " con una nota de " + calificacionObtenida + "%.");
            return true;
        } else {
            System.out.println("El examen no ha sido completado exitosamente por: " + estudiante.getNombre());
            return false;
        }
    }

    // Método para reintentar el examen
    @Override
    public void reintentar(Estudiante estudiante) {
        if (status == Status.Enviada || status == Status.Exitosa) {
            throw new UnsupportedOperationException("El examen ya ha sido enviado o aprobado y no se puede reintentar.");
        }
        // Reiniciar el contador de respuestas correctas y el estado del examen
        respuestasCorrectas = 0;
        calificacionObtenida = 0.0;
        respuestasAbiertas.clear();
        this.status = Status.Incompleto;
        System.out.println("El examen ha sido reiniciado por: " + estudiante.getNombre());
    }    

}