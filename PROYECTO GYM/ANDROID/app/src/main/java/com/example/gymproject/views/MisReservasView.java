package com.example.gymproject.views;

import com.example.gymproject.entities.Reserva;

import java.util.List;

public interface MisReservasView {
    void mostrarCargando();
    void ocultarCargando();
    void mostrarReservasProximas(List<Reserva> reservas);
    void mostrarReservasPasadas(List<Reserva> reservas);
    void mostrarMensajeNoReservasProximas();
    void mostrarMensajeNoReservasPasadas();
    void mostrarError(String mensaje);
}