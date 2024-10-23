package com.example.gymproject.models;

import com.example.gymproject.contratosModel.ContractLoginModel;
import com.example.gymproject.entities.Usuario;
import com.example.gymproject.services.AuthService;
import com.example.gymproject.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel implements ContractLoginModel {

    private AuthService authService;

    public LoginModel() {
        this.authService = RetrofitClient.getRetrofitInstance().create(AuthService.class);
    }

    // Método para realizar el login
    @Override
    public void login(String email, String password, ContractLoginModel.LoginCallback callback) {
        Usuario usuario = new Usuario(email, password);

        // Realiza la llamada al servicio de autenticación
        authService.login(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else if (response.code() == 403) {
                    // Usuario inactivo
                    callback.onError("Usuario no activo. Contacta con soporte.");
                } else {
                    callback.onError("Credenciales incorrectas");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });

    }

}
