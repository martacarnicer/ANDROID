package com.example.gymproject.contratosModel;

import com.example.gymproject.entities.Usuario;

public interface ContractDashboardModel {
    void obtenerDatosUsuario(Long idUsuario, DashboardCallback callback);

    interface DashboardCallback {
        void onSuccess(Usuario usuario);
        void onError(String error);
    }
}
