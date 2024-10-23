package com.example.gymproject.presenters;

import com.example.gymproject.contratosPresenter.ContractDashboardPresenter;
import com.example.gymproject.entities.Usuario;
import com.example.gymproject.models.DashboardModel;
import com.example.gymproject.views.DashboardView;

public class DashboardPresenter implements ContractDashboardPresenter {
    private DashboardView dashboardView;
    private DashboardModel dashboardModel;

    public DashboardPresenter(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
        this.dashboardModel = new DashboardModel();
    }

    public void obtenerDatosUsuario(Long idUsuario) {
        dashboardView.showLoading();  // Mostrar indicador de carga

        dashboardModel.obtenerDatosUsuario(idUsuario, new DashboardModel.DashboardCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                dashboardView.hideLoading();  // Ocultar el indicador de carga
                dashboardView.showUserDetails(usuario.getNombre());  // Pasar los detalles del usuario a la vista
            }

            @Override
            public void onError(String error) {
                dashboardView.hideLoading();  // Ocultar el indicador de carga
                dashboardView.showError(error);  // Mostrar el error en la vista
            }
        });
    }

    public void verificarNotificacionesNuevas(Long idUsuario) {
        dashboardModel.verificarNotificacionesNuevas(idUsuario, new DashboardModel.NotificacionCallback() {
            @Override
            public void onSuccess(Boolean hayNotificaciones) {
                if (hayNotificaciones) {
                    dashboardView.mostrarAvisoNotificaciones(true);  // Mostrar el aviso de nuevas notificaciones
                }
            }

            @Override
            public void onError(String error) {
                dashboardView.showError("Error al verificar notificaciones nuevas");
            }
        });
    }

}
