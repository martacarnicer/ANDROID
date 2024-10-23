package com.example.ProyectoGym.Controllers;

import com.example.ProyectoGym.Entities.Clase;
import com.example.ProyectoGym.Entities.Inscripcion;
import com.example.ProyectoGym.Entities.Notificacion;
import com.example.ProyectoGym.Entities.Usuario;
import com.example.ProyectoGym.Services.ClaseService;
import com.example.ProyectoGym.Services.InscripcionService;
import com.example.ProyectoGym.Services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private ClaseService claseService;

    // Obtener todas las inscripciones de un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Inscripcion>> obtenerInscripcionesDeUsuario(@PathVariable Long idUsuario) {
        List<Inscripcion> inscripciones = inscripcionService.obtenerInscripcionesPorUsuario(idUsuario);
        // Log para verificar que la fecha de inscripción está presente
        inscripciones.forEach(inscripcion ->
                System.out.println("Fecha de inscripción: " + inscripcion.getFechaInscripcion()));
        return ResponseEntity.ok(inscripciones);
    }


    // Obtener todas las inscripciones de una clase
    @GetMapping("/clase/{idClase}")
    public ResponseEntity<List<Inscripcion>> obtenerInscripcionesDeClase(@PathVariable Long idClase) {
        List<Inscripcion> inscripciones = inscripcionService.obtenerInscripcionesPorClase(idClase);
        return ResponseEntity.ok(inscripciones);
    }

    // Controlador para inscribir a un usuario en una clase
    @PostMapping("/inscribir")
    public ResponseEntity<Inscripcion> inscribirUsuarioEnClase(
            @RequestParam Long idUsuario,
            @RequestParam Long idClase) {
        try {
            // Lógica para inscribir al usuario
            Inscripcion nuevaInscripcion = inscripcionService.inscribirUsuario(idUsuario, idClase);

            // Obtener los detalles de la clase para personalizar el contenido de la notificación
            Clase clase = claseService.obtenerClasePorId(idClase)
                    .orElseThrow(() -> new IllegalArgumentException("Clase no encontrada con ID: " + idClase));

// Crear y guardar la notificación
            Notificacion notificacion = new Notificacion();
            notificacion.setUsuario(new Usuario(idUsuario)); // Asignar el usuario que recibirá la notificación
            notificacion.setTitulo("Reserva confirmada");
            notificacion.setContenido("Tu reserva para la clase de " + clase.getNombreClase() + " ha sido confirmada.");
            notificacion.setFechaProgramada(LocalDateTime.now());
            notificacion.setEnviado(false); // Inicialmente marcamos la notificación como no enviada
            notificacion.setTipoNotificacion("inscripcion_clase");

// Guardar la notificación en la base de datos
            Notificacion notificacionGuardada = notificacionService.crearNotificacion(notificacion);

// Enviar la notificación inmediatamente
            notificacionService.enviarNotificacion(notificacionGuardada.getIdNotificacion());

            // Retornar la respuesta con la inscripción creada
            return ResponseEntity.ok(nuevaInscripcion);
        } catch (IllegalArgumentException e) {
            // Manejo de error en caso de que haya algún problema con la inscripción o la clase
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }




    // Cancelar una inscripción
    @DeleteMapping("/cancelar/{idInscripcion}")
    public ResponseEntity<Void> cancelarInscripcion(@PathVariable Long idInscripcion) {
        inscripcionService.cancelarInscripcion(idInscripcion);
        return ResponseEntity.noContent().build();
    }
}

