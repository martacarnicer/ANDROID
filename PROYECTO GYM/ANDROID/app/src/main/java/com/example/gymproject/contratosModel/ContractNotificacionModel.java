package com.example.gymproject.contratosModel;

import com.example.gymproject.entities.Notificacion;

import java.util.List;

public interface ContractNotificacionModel {
    void obtenerNotificaciones(Long idUsuario, OnFinishedListener listener);

    interface OnFinishedListener {
        void onFinished(List<Notificacion> notificaciones);
        void onFailure(String errorMessage);
    }
}
