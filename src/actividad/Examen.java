package actividad;

import pregunta.Pregunta;
import pregunta.PreguntaCerrada;
import pregunta.PreguntaAbierta;
import usuario.Estudiante;
import usuario.Profesor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import LPRS.LearningPath;

public class Examen extends Actividad {

    protected List<Pregunta> listaPreguntas;
    protected double calificacionMinima;
    private List<String> respuestasAbiertas;
    private int respuestasCorrectas;
    private double calificacionObtenida;

    public Examen(String descripcion, Nivel nivelDificultad, String objetivo, int duracionEsperada, 
                  double version, LocalDateTime fechaLimite, Map<Estudiante, Status> estadosPorEstudiante, Obligatoria obligatoria, 
                  List<Pregunta> listaPreguntas, double calificacionMinima, Profesor creador, 
                  List<Actividad> actividadesPreviasSugeridas, List<Actividad> actividadesSeguimientoRecomendadas) {
        super(descripcion, nivelDificultad, objetivo, duracionEsperada, version, 
              fechaLimite, estadosPorEstudiante, obligatoria, "examen", creador, 
              actividadesPreviasSugeridas, actividadesSeguimientoRecomendadas);
        this.listaPreguntas = listaPreguntas;
        this.calificacionMinima = calificacionMinima;
        this.respuestasCorrectas = 0;
        this.calificacionObtenida = 0.0;
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

    // Método para que el estudiante responda el examen
    @Override
    public void responder(Estudiante estudiante, String respuestas) {
        if (estudiante == null) {
            throw new SecurityException("Se requiere un estudiante para completar el examen.");
        }

        Status estadoActual = getStatusParaEstudiante(estudiante);
        if (estadoActual == Status.Completado || estadoActual == Status.Enviada) {
            throw new UnsupportedOperationException("El examen ya ha sido completado o enviado por este estudiante.");
        }

        String[] respuestasEstudiante = respuestas.split(";");
        if (respuestasEstudiante.length != listaPreguntas.size()) {
            throw new IllegalArgumentException("La cantidad de respuestas no coincide con la cantidad de preguntas en el examen.");
        }

        int respuestasCorrectas = 0;
        for (int i = 0; i < respuestasEstudiante.length; i++) {
            Pregunta pregunta = listaPreguntas.get(i);
            String respuestaEstudiante = respuestasEstudiante[i];

            if (pregunta instanceof PreguntaCerrada) {
                PreguntaCerrada preguntaCerrada = (PreguntaCerrada) pregunta;
                preguntaCerrada.elegirRespuesta(respuestaEstudiante);
                if (preguntaCerrada.esCorrecta()) { 
                    respuestasCorrectas++;
                }
            } else if (pregunta instanceof PreguntaAbierta) {
                PreguntaAbierta preguntaAbierta = (PreguntaAbierta) pregunta;
                respuestasAbiertas.add(respuestaEstudiante);
            }
        }

        this.respuestasCorrectas = respuestasCorrectas;
        setStatusParaEstudiante(estudiante, Status.Enviada);
        System.out.println("El examen ha sido enviado por: " + estudiante.getNombre());
    }

    // Método para que el profesor evalúe las preguntas abiertas del examen
    @Override
    public void evaluar(Profesor profesor, Estudiante estudiante, LearningPath learningPath, double calificacionObtenida, boolean exitosa) {
        if (profesor == null || !profesor.equals(creador)) {
            throw new SecurityException("Solo el profesor creador puede evaluar el examen.");
        }

        if (!learningPath.verificarSiInscrito(estudiante)) {
            throw new IllegalArgumentException("El estudiante no está inscrito en el Learning Path para este examen.");
        }

        System.out.println("Advertencia: la calificación proporcionada (" + calificacionObtenida + ") se ignorará en el examen.");

        for (Pregunta pregunta : listaPreguntas) {
            if (pregunta instanceof PreguntaAbierta) {
                PreguntaAbierta preguntaAbierta = (PreguntaAbierta) pregunta;

                // Evaluar la pregunta abierta, esto el profesor lo hace manualmente en la vida real, cuando se haga interfaz gráfica se puede hacer de forma automática con el metodo evaluarPorProfesor en PreguntaAbierta.
                if (preguntaAbierta.esCorrecta()) { 
                    respuestasCorrectas++;

                }
            }
        }

        calcularCalificacionFinal();
        if (this.calificacionObtenida >= this.calificacionMinima) {
            setStatusParaEstudiante(estudiante, Status.Exitosa);
            System.out.println("El examen ha sido aprobado por: " + estudiante.getNombre() + " con una nota de " + calificacionObtenida + "%.");
        } else {
            setStatusParaEstudiante(estudiante, Status.noExitosa);
            System.out.println("El examen no ha sido aprobado por: " + estudiante.getNombre() + ". Nota obtenida: " + calificacionObtenida + "%.");
        }
    }

    // Método para calcular la calificación final del examen
    private void calcularCalificacionFinal() {
        int totalPreguntas = listaPreguntas.size();
        calificacionObtenida = ((double) respuestasCorrectas / totalPreguntas) * 100;
    }

    // Método para verificar si el examen es exitoso para un estudiante específico
    @Override
    public boolean esExitosa(Estudiante estudiante) {
        Status estadoEstudiante = getStatusParaEstudiante(estudiante);
        if (estadoEstudiante == Status.Exitosa || estadoEstudiante == Status.Completado) {
            System.out.println("El examen fue completado exitosamente por: " + estudiante.getNombre() + " con una nota de " + calificacionObtenida + "%.");
            estudiante.agregarActividadCompletada(this);
            return true;
        } else {
            System.out.println("El examen no ha sido completado exitosamente por: " + estudiante.getNombre());
            return false;
        }
    }

    // Método para reintentar el examen por un estudiante específico
    @Override
    public void reintentar(Estudiante estudiante) {
        Status estadoEstudiante = getStatusParaEstudiante(estudiante);
        if (estadoEstudiante == Status.Enviada || estadoEstudiante == Status.Exitosa) {
            throw new UnsupportedOperationException("El examen ya ha sido enviado o aprobado y no se puede reintentar.");
        }

        respuestasCorrectas = 0;
        calificacionObtenida = 0.0;
        respuestasAbiertas.clear();
        setStatusParaEstudiante(estudiante, Status.Incompleto);
        System.out.println("El examen ha sido reiniciado por: " + estudiante.getNombre());
    }

    // Método para agregar una pregunta al examen
    public void agregarPregunta(Pregunta pregunta) {
        if (pregunta == null) {
            throw new IllegalArgumentException("La pregunta no puede ser nula.");
        }

        listaPreguntas.add(pregunta);
    }

    // Método para eliminar una pregunta del examen
    public void eliminarPregunta(Pregunta pregunta) {
        if (pregunta == null) {
            throw new IllegalArgumentException("La pregunta no puede ser nula.");
        }

        if (listaPreguntas.size() <= 1) {
            throw new UnsupportedOperationException("El examen debe tener al menos una pregunta.");
        }

        listaPreguntas.remove(pregunta);
    }
}
