package persistencia;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import pregunta.Opcion;
import pregunta.Pregunta;
import pregunta.PreguntaAbierta;
import pregunta.PreguntaCerrada;

public class PersistenciaPregunta {
    // Método auxiliar para cargar las preguntas de un quiz o examen

    public static List<Pregunta> cargarListaPreguntas(String preguntasStr) {
        List<Pregunta> listaPreguntas = new ArrayList<>();
    
        if (preguntasStr == null || preguntasStr.isEmpty()) {
            return listaPreguntas;
        }
    
        String[] preguntasData = preguntasStr.split(";");
        for (String preguntaStr : preguntasData) {
            if (preguntaStr.startsWith("Cerrada:")) {
                String preguntaCerradaStr = preguntaStr.substring("Cerrada:".length());
                listaPreguntas.addAll(cargarPreguntasCerradas(preguntaCerradaStr));
            } else if (preguntaStr.startsWith("Abierta:")) {
                String preguntaAbiertaStr = preguntaStr.substring("Abierta:".length());
                listaPreguntas.addAll(cargarPreguntasAbiertas(preguntaAbiertaStr));
            } else {
                System.out.println("Tipo de pregunta desconocido o formato incorrecto: " + preguntaStr);
            }
        }
    
        return listaPreguntas;
    }
    
    
    // Método para cargar una lista de preguntas cerradas a partir de una cadena de texto
    public static List<PreguntaCerrada> cargarPreguntasCerradas(String preguntasStr) {
        List<PreguntaCerrada> listaPreguntas = new ArrayList<>();
    
        if (preguntasStr == null || preguntasStr.isEmpty()) {
            return listaPreguntas; // Retorna una lista vacía si no hay preguntas
        }
    
        // Dividir cada pregunta usando ';'
        String[] preguntasData = preguntasStr.split(";");
        for (String preguntaStr : preguntasData) {
            // Divide cada componente de la pregunta usando '|'
            String[] partesPregunta = preguntaStr.split("\\|");
    
            // El primer elemento es el enunciado de la pregunta
            String enunciado = partesPregunta[0];
    
            // Crear una nueva pregunta cerrada con el enunciado
            PreguntaCerrada pregunta = new PreguntaCerrada(enunciado);
    
            // Diccionarios para almacenar las opciones
            Dictionary<Opcion, String> opcionA = new Hashtable<>();
            Dictionary<Opcion, String> opcionB = new Hashtable<>();
            Dictionary<Opcion, String> opcionC = new Hashtable<>();
            Dictionary<Opcion, String> opcionD = new Hashtable<>();
            Dictionary<Opcion, String> respuestaCorrecta = new Hashtable<>();
    
            // Iterar sobre las partes de la pregunta para asignar opciones y respuesta
            for (int i = 1; i < partesPregunta.length; i++) {
                String parte = partesPregunta[i];
    
                if (parte.startsWith("A:")) {
                    opcionA.put(Opcion.A, parte.substring(2));
                    pregunta.setOpcionA(opcionA);
                } else if (parte.startsWith("B:")) {
                    opcionB.put(Opcion.B, parte.substring(2));
                    pregunta.setOpcionB(opcionB);
                } else if (parte.startsWith("C:")) {
                    opcionC.put(Opcion.C, parte.substring(2));
                    pregunta.setOpcionC(opcionC);
                } else if (parte.startsWith("D:")) {
                    opcionD.put(Opcion.D, parte.substring(2));
                    pregunta.setOpcionD(opcionD);
                } else if (parte.startsWith("Respuesta:")) {
                    String respuesta = parte.substring(10);
                    if (respuesta.equals("A")) {
                        respuestaCorrecta.put(Opcion.A, opcionA.get(Opcion.A));
                    } else if (respuesta.equals("B")) {
                        respuestaCorrecta.put(Opcion.B, opcionB.get(Opcion.B));
                    } else if (respuesta.equals("C")) {
                        respuestaCorrecta.put(Opcion.C, opcionC.get(Opcion.C));
                    } else if (respuesta.equals("D")) {
                        respuestaCorrecta.put(Opcion.D, opcionD.get(Opcion.D));
                    }
                    pregunta.setRespuesta(respuestaCorrecta);
                }
            }
    
            // Añadir la pregunta configurada a la lista
            listaPreguntas.add(pregunta);
        }
    
        return listaPreguntas; // Retorna la lista de preguntas cerradas
    }
    

    public static List<PreguntaAbierta> cargarPreguntasAbiertas(String preguntasStr) {
        List<PreguntaAbierta> listaPreguntas = new ArrayList<>();
    
        if (preguntasStr == null || preguntasStr.isEmpty()) {
            return listaPreguntas; // Retorna una lista vacía si no hay preguntas
        }
    
        // Dividir cada pregunta usando ';'
        String[] preguntasData = preguntasStr.split(";");
        for (String preguntaStr : preguntasData) {
            // Cada pregunta tiene el formato "Abierta:enunciado"
            String enunciado = preguntaStr.trim();
    
            // Crear una nueva pregunta abierta con el enunciado
            PreguntaAbierta pregunta = new PreguntaAbierta(enunciado);
    
            // Añadir la pregunta configurada a la lista
            listaPreguntas.add(pregunta);
        }
    
        return listaPreguntas; // Retorna la lista de preguntas abiertas
    }
}
