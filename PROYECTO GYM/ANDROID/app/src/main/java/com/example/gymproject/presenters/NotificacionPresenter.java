package com.example.gymproject.presenters;

import com.example.gymproject.contratosModel.ContractNotificacionModel;
import com.example.gymproject.contratosPresenter.ContractNotificacionPresenter;
import com.example.gymproject.entities.Notificacion;
import com.example.gymproject.models.NotificacionModel;
import com.example.gymproject.views.NotificacionView;

import java.util.List;

public class NotificacionPresenter implements ContractNotificacionPresenter, ContractNotificacionModel.OnFinishedListener {

    private NotificacionView notificacionView;
    private ContractNotificacionModel notificacionModel;

    public NotificacionPresenter(NotificacionView view) {
        this.notificacionView = view;
        this.notificacionModel = new NotificacionModel();
    }

    @Override
    public void obtenerNotificaciones(Long idUsuario) {
        if (notificacionView != null) {
            notificacionView.mostrarCargando();
        }
        notificacionModel.obtenerNotificaciones(idUsuario, this);
    }

    @Override
    public void onFinished(List<Notificacion> notificaciones) {
        if (notificacionView != null) {
            notificacionView.ocultarCargando();
            notificacionView.mostrarNotificaciones(notificaciones);
            notificacionView.mostrarAvisoNotificaciones(!notificaciones.isEmpty());
        }
    }

    @Override
    public void onFailure(String errorMessage) {
        if (notificacionView != null) {
            notificacionView.ocultarCargando();
            notificacionView.mostrarError(errorMessage);
        }
    }
}
