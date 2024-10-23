package com.example.gymproject.contratosModel;

import com.example.gymproject.entities.Acceso;
import com.example.gymproject.entities.Centro;
import com.example.gymproject.entities.Usuario;

import java.util.List;

public interface ContractPerfilModel {
    interface PerfilCallback {
        void onSuccess(Usuario usuario, Acceso acceso);  // Ahora acepta ambos objetos
        void onError(String error);
    }

    interface UpdateCallback {
        void onUpdateSuccess();
        void onUpdateError(String error);
    }

    interface CentrosCallback {
        void onCentrosLoaded(List<Centro> centros);
        void onError(String error);
    }

    void obtenerCentros(CentrosCallback callback);
    void obtenerDatosUsuario(Long idUsuario, PerfilCallback callback);
    void actualizarUsuario(Usuario usuario, UpdateCallback callback);
    void desactivarUsuario(Long idUsuario, UpdateCallback callback);

    void actualizarCentroAcceso(Long idUsuario, Long idCentro, UpdateCallback callback);
    void otorgarAccesoPremium(Long idUsuario, UpdateCallback callback);
}
