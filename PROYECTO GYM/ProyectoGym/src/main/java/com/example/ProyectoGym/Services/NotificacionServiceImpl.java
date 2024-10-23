package com.example.ProyectoGym.Services;
import com.example.ProyectoGym.Entities.Clase;
import com.example.ProyectoGym.Entities.Inscripcion;
import com.example.ProyectoGym.Entities.Notificacion;
import com.example.ProyectoGym.Entities.Usuario;
import com.example.ProyectoGym.Repositories.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;


    @Autowired
    private ClaseService claseService;

    @Override
    public List<Notificacion> notificacionesPorUsuario(Long idUsuario) {
        return notificacionRepository.findByUsuario_IdUsuario(idUsuario);
    }

    @Override
    public Notificacion crearNotificacion(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    @Override
    public void enviarNotificacion(Long idNotificacion) {
        Notificacion notificacion = notificacionRepository.findById(idNotificacion).orElseThrow();
        System.out.println("Enviando notificación: " + notificacion.getTitulo());
        notificacion.setEnviado(true);
        notificacionRepository.save(notificacion);
    }

    @Scheduled(fixedRate = 60000) // Ejecuta cada minuto
    public void enviarRecordatoriosDeClases() {
        LocalDateTime now = LocalDateTime.now();

        // Obtener las clases que van a empezar en 1 hora
        List<Clase> clasesProximas = claseService.obtenerClasesPorHoraInicio(now);

        for (Clase clase : clasesProximas) {
            for (Inscripcion inscripcion : clase.getInscripciones()) {
                Usuario usuario = inscripcion.getUsuario();

                // Crear notificación de recordatorio
                Notificacion notificacion = new Notificacion();
                notificacion.setUsuario(usuario);
                notificacion.setTitulo("Recordatorio");
                notificacion.setContenido("Queda 1h para tu clase de " + clase.getNombreClase());
                notificacion.setFechaProgramada(now);
                notificacion.setEnviado(false);
                notificacion.setTipoNotificacion("recordatorio_clase");

                // Guardar y enviar notificación
                crearNotificacion(notificacion);
                enviarNotificacion(notificacion.getIdNotificacion());
            }
        }
    }
}