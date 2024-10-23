package com.example.gymproject.models;

import com.example.gymproject.contratosModel.ContractDashboardModel;
import com.example.gymproject.entities.Usuario;
import com.example.gymproject.services.ApiService;
import com.example.gymproject.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.gymproject.services.NotificacionService;

public class DashboardModel implements ContractDashboardModel {

    private ApiService apiService;
    private NotificacionService notificacionService;

    public DashboardModel() {
        this.apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        this.notificacionService = RetrofitClient.getRetrofitInstance().create(NotificacionService.class);  // Inicializar NotificacionService
    }

    // Método para obtener los datos del usuario
    @Override
    public void obtenerDatosUsuario(Long idUsuario, ContractDashboardModel.DashboardCallback callback) {
        apiService.getUsuarioPorId(idUsuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener los datos del usuario.");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });

    }

    // Método para verificar notificaciones nuevas
    public void verificarNotificacionesNuevas(Long idUsuario, NotificacionCallback callback) {
        notificacionService.hayNotificacionesNuevas(idUsuario).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al verificar notificaciones nuevas");
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }

    public interface NotificacionCallback {
        void onSuccess(Boolean hayNotificaciones);
        void onError(String error);
    }
}

