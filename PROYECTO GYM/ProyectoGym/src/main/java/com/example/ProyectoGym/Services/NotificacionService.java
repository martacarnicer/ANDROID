package com.example.ProyectoGym.Services;

import com.example.ProyectoGym.Entities.Notificacion;
import java.util.List;

public interface NotificacionService {

    List<Notificacion> notificacionesPorUsuario(Long idUsuario);

    Notificacion crearNotificacion(Notificacion notificacion);

    void enviarNotificacion(Long idNotificacion);
}
