package com.example.ProyectoGym.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FechaUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Método para convertir String a LocalDate
    public static LocalDate convertirStringADate(String fecha) {
        try {
            return LocalDate.parse(fecha, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Error al convertir la fecha: " + fecha);
        }
    }

    // Método para obtener la fecha exacta de la clase en base a la fecha de inscripción y el día de la semana
    public static LocalDate obtenerProximaFechaClase(LocalDate fechaInscripcion, String diaSemana) {
        int diaClase = convertirDiaSemanaAInt(diaSemana);
        int diaInscripcion = fechaInscripcion.getDayOfWeek().getValue(); // Lunes = 1, Domingo = 7
        int diasHastaClase = (diaClase >= diaInscripcion) ? (diaClase - diaInscripcion) : (7 - diaInscripcion) + diaClase;

        return fechaInscripcion.plusDays(diasHastaClase);
    }

    // Conversión de día de la semana a número (1 = Lunes, 7 = Domingo)
    private static int convertirDiaSemanaAInt(String diaSemana) {
        switch (diaSemana.toLowerCase()) {
            case "lunes": return 1;
            case "martes": return 2;
            case "miércoles": return 3;
            case "jueves": return 4;
            case "viernes": return 5;
            case "sábado": return 6;
            case "domingo": return 7;
            default: throw new IllegalArgumentException("Día de la semana inválido: " + diaSemana);
        }
    }
}