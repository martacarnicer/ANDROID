package com.example.gymproject.views;

import com.example.gymproject.entities.Notificacion;

import java.util.List;

public interface NotificacionView {
    void mostrarNotificaciones(List<Notificacion> notificaciones);
    void mostrarError(String mensaje);
    void mostrarCargando();
    void ocultarCargando();
    void mostrarAvisoNotificaciones(boolean hayNotificaciones);
}
