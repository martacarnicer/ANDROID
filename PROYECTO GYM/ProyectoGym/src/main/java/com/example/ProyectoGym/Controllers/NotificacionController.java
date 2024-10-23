package com.example.ProyectoGym.Controllers;

import com.example.ProyectoGym.Entities.Notificacion;
import com.example.ProyectoGym.Services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // Obtener todas las notificaciones de un usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Notificacion>> obtenerNotificacionesPorUsuario(@PathVariable Long idUsuario) {
        List<Notificacion> notificaciones = notificacionService.notificacionesPorUsuario(idUsuario);
        return ResponseEntity.ok(notificaciones);
    }

    // Crear una nueva notificación
    @PostMapping("/crear")
    public ResponseEntity<Notificacion> crearNotificacion(@RequestBody Notificacion notificacion) {
        Notificacion nuevaNotificacion = notificacionService.crearNotificacion(notificacion);
        return ResponseEntity.ok(nuevaNotificacion);
    }

    // Enviar una notificación
    @PostMapping("/enviar/{idNotificacion}")
    public ResponseEntity<Void> enviarNotificacion(@PathVariable Long idNotificacion) {
        notificacionService.enviarNotificacion(idNotificacion);
        return ResponseEntity.noContent().build();
    }
}
