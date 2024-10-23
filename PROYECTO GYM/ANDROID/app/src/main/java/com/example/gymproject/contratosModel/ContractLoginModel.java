package com.example.gymproject.contratosModel;

import com.example.gymproject.entities.Usuario;

public interface ContractLoginModel {
    void login(String email, String password, LoginCallback callback);

    interface LoginCallback {
        void onSuccess(Usuario usuario);
        void onError(String error);
    }
}
