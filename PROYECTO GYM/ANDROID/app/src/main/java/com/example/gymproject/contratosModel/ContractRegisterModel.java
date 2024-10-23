package com.example.gymproject.contratosModel;

import com.example.gymproject.entities.Centro;
import com.example.gymproject.entities.Usuario;

import java.util.List;

public interface ContractRegisterModel {

    void registerUser(Usuario usuario, RegisterCallback callback);
    void insertarAcceso(Long idUsuario, Long idCentro, RegisterCallback callback);
    void obtenerCentros(CentrosCallback callback);

    interface RegisterCallback {
        void onSuccess(Object result);
        void onError(String error);
    }

    interface CentrosCallback {
        void onCentrosLoaded(List<Centro> centros);
        void onError(String error);
    }
}

