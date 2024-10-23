package com.example.ProyectoGym.Services;

import com.example.ProyectoGym.Entities.Clase;
import com.example.ProyectoGym.Entities.Inscripcion;
import com.example.ProyectoGym.Entities.Usuario;
import com.example.ProyectoGym.Repositories.ClaseRepository;
import com.example.ProyectoGym.Repositories.InscripcionRepository;
import com.example.ProyectoGym.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import com.example.ProyectoGym.Entities.Clase;
import com.example.ProyectoGym.Entities.Inscripcion;
import com.example.ProyectoGym.Entities.Usuario;
import com.example.ProyectoGym.Repositories.ClaseRepository;
import com.example.ProyectoGym.Repositories.InscripcionRepository;
import com.example.ProyectoGym.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClaseRepository claseRepository;

    // Método para inscribir un usuario a una clase
    @Override
    public Inscripcion inscribirUsuario(Long idUsuario, Long idClase) {
        // Verificar si el usuario existe
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Verificar si la clase existe
        Clase clase = claseRepository.findById(idClase)
                .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada"));

        // Crear una nueva inscripción
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setUsuario(usuario);
        inscripcion.setClase(clase);

        // Establecer la fecha actual como fecha de inscripción
        String fechaInscripcion = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        inscripcion.setFechaInscripcion(fechaInscripcion);
        // Guardar la inscripción en la base de datos
        return inscripcionRepository.save(inscripcion);
    }

    // Obtener el historial de inscripciones de un usuario (clases pasadas y futuras)
    @Override
    public List<Inscripcion> obtenerInscripcionesPorUsuario(Long idUsuario) {
        return inscripcionRepository.findByUsuario_IdUsuario(idUsuario);
    }

    // Método para obtener inscripciones por clase
    @Override
    public List<Inscripcion> obtenerInscripcionesPorClase(Long idClase) {
        return inscripcionRepository.findByClase_IdClase(idClase);
    }

    // Cancelar una inscripción
    @Override
    public void cancelarInscripcion(Long idInscripcion) {
        inscripcionRepository.deleteById(idInscripcion);
    }
}

