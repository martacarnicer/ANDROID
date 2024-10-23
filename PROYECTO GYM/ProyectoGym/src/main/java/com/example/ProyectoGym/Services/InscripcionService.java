package com.example.ProyectoGym.Services;

import com.example.ProyectoGym.Entities.Inscripcion;
import java.util.List;

import com.example.ProyectoGym.Entities.Inscripcion;
import java.util.List;

public interface InscripcionService {

    // Método para inscribir un usuario a una clase (usando IDs)
    Inscripcion inscribirUsuario(Long idUsuario, Long idClase);

    // Método para obtener las inscripciones de un usuario
    List<Inscripcion> obtenerInscripcionesPorUsuario(Long idUsuario);

    // Método para cancelar una inscripción
    void cancelarInscripcion(Long idInscripcion);


    // Método para obtener las inscripciones de una clase
    List<Inscripcion> obtenerInscripcionesPorClase(Long idClase);
}

