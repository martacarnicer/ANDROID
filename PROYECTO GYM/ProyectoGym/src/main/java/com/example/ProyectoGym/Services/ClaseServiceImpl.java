package com.example.ProyectoGym.Services;

import com.example.ProyectoGym.Entities.Clase;
import com.example.ProyectoGym.Entities.Inscripcion;
import com.example.ProyectoGym.Repositories.ClaseRepository;
import com.example.ProyectoGym.Repositories.InscripcionRepository;
import com.example.ProyectoGym.utils.FechaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.*;

@Service
public class ClaseServiceImpl implements ClaseService {

    @Autowired
    private ClaseRepository claseRepository;
    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Override
    public List<Clase> obtenerTodasLasClases() {
        return claseRepository.findAll();
    }

    @Override
    public int obtenerDisponibilidad(Long idClase) {
        Optional<Clase> claseOpt = claseRepository.findById(idClase);
        if (claseOpt.isPresent()) {
            Clase clase = claseOpt.get();
            int inscripciones = inscripcionRepository.countByClaseId(idClase);
            return clase.getCapacidadMaxima() - inscripciones;
        } else {
            throw new RuntimeException("Clase no encontrada");
        }
    }

    @Override
    public List<Clase> obtenerClasesReservadasPorUsuario(Long idUsuario) {
        return claseRepository.findByUsuarioId(idUsuario);
    }

    @Override
    public List<Clase> obtenerClasesReservadasProximas(Long idUsuario) {
        LocalDate hoy = LocalDate.now();

        List<Clase> clases = claseRepository.findByUsuarioId(idUsuario);
        return clases.stream()
                .filter(clase -> {
                    LocalDate fechaClase = calcularFechaClase(clase);
                    return fechaClase.isAfter(hoy) || fechaClase.isEqual(hoy);
                })
                .peek(clase -> clase.setFechaExactaClase(calcularFechaClase(clase)))  // Aquí se establece la fecha exacta
                .collect(Collectors.toList());
    }

    @Override
    public List<Clase> obtenerClasesReservadasPasadas(Long idUsuario) {
        LocalDate hoy = LocalDate.now();

        List<Clase> clases = claseRepository.findByUsuarioId(idUsuario);
        return clases.stream()
                .filter(clase -> {
                    LocalDate fechaClase = calcularFechaClase(clase); // Calcula la fecha de la clase
                    return fechaClase.isBefore(hoy); // Filtra solo las clases pasadas
                })
                .peek(clase -> clase.setFechaExactaClase(calcularFechaClase(clase))) // Establece la fecha exacta de la clase
                .collect(Collectors.toList());
    }

    // Método para calcular la fecha exacta de la clase
    private LocalDate calcularFechaClase(Clase clase) {
        Set<Inscripcion> inscripciones = clase.getInscripciones();
        if (inscripciones != null && !inscripciones.isEmpty()) {
            Inscripcion primeraInscripcion = inscripciones.iterator().next();
            LocalDate fechaInscripcionParsed = FechaUtils.convertirStringADate(primeraInscripcion.getFechaInscripcion());
            String diaSemana = clase.getDiaSemana();
            return FechaUtils.obtenerProximaFechaClase(fechaInscripcionParsed, diaSemana);
        } else {
            throw new RuntimeException("No se encontraron inscripciones para la clase.");
        }
    }



    @Override
    public List<Clase> filtrarPorHorario(String diaSemana, String horaInicio, String horaFin) {
        return claseRepository.findByDiaSemanaAndHoraBetween(diaSemana, horaInicio, horaFin);
    }

    @Override
    public Optional<Clase> obtenerClasePorId(Long idClase) {
        return claseRepository.findById(idClase);
    }

    @Override
    public Clase guardarClase(Clase clase) {
        return claseRepository.save(clase);
    }

    @Override
    public void eliminarClase(Long idClase) {
        claseRepository.deleteById(idClase);
    }

    @Override
    public List<Clase> filtrarClases(String nombre, String diaSemana, Long idInstructor, Long idCentro) {
        return claseRepository.findByFilters(nombre, diaSemana, idInstructor, idCentro);
    }

    @Override
    public Clase crearClase(Clase clase) {
        return claseRepository.save(clase);
    }

    @Override
    public void cancelarClase(Long idClase) {
        claseRepository.deleteById(idClase);
    }

    @Override
    public List<Clase> obtenerClasesPorInstructor(Long idInstructor) {
        return claseRepository.findByInstructor(idInstructor);
    }

    @Override
    public List<Clase> obtenerClasesPorDia(String dia) {
        return claseRepository.findByDiaSemana(dia);
    }

    @Override
    public List<Clase> obtenerClasesPorDiaYCentro(String dia, Long idCentro) {
        return claseRepository.findClasesPorDiaYCentro(dia, idCentro);
    }

    @Override
    public List<Clase> obtenerClasesPorHoraInicio(LocalDateTime horaInicio) {
        // Convertimos la hora actual y la hora futura a String para realizar la consulta
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String ahoraString = horaInicio.toLocalTime().format(timeFormatter);
        String unaHoraMasString = horaInicio.plusHours(1).toLocalTime().format(timeFormatter);

        // Llamamos al repositorio para obtener las clases que comienzan dentro de 1 hora
        return claseRepository.findByHoraInicioBetween(ahoraString, unaHoraMasString);
    }






}
