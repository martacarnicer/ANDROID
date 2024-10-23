package com.example.ProyectoGym.Services;

import com.example.ProyectoGym.Entities.Clase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ClaseService {
    List<Clase> filtrarClases(String nombre, String diaSemana, Long idInstructor, Long idCentro);

    Clase crearClase(Clase clase);

    void cancelarClase(Long idClase);
    List<Clase> filtrarPorHorario(String diaSemana, String horaInicio, String horaFin);
    int obtenerDisponibilidad(Long idClase);

    List<Clase> obtenerClasesReservadasPorUsuario(Long idUsuario);
    List<Clase> obtenerClasesReservadasProximas(Long idUsuario);
    List<Clase> obtenerClasesReservadasPasadas(Long idUsuario);
    List<Clase> obtenerClasesPorDia(String dia);
    List<Clase> obtenerClasesPorDiaYCentro(String dia, Long idCentro);

    List<Clase> obtenerClasesPorHoraInicio(LocalDateTime horaInicio);





    List<Clase> obtenerClasesPorInstructor(Long idInstructor);

    Optional<Clase> obtenerClasePorId(Long idClase);

    List<Clase> obtenerTodasLasClases();

    Clase guardarClase(Clase clase);

    void eliminarClase(Long id);
}
