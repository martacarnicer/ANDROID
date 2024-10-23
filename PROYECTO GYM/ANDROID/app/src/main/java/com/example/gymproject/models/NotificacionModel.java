package com.example.gymproject.models;

import com.example.gymproject.contratosModel.ContractNotificacionModel;
import com.example.gymproject.entities.Notificacion;
import com.example.gymproject.retrofit.RetrofitClient;
import com.example.gymproject.services.NotificacionService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificacionModel implements ContractNotificacionModel {

    private NotificacionService notificacionService;

    public NotificacionModel() {
        notificacionService = RetrofitClient.getRetrofitInstance().create(NotificacionService.class);
    }

    @Override
    public void obtenerNotificaciones(Long idUsuario, OnFinishedListener listener) {
        notificacionService.obtenerNotificacionesPorUsuario(idUsuario).enqueue(new Callback<List<Notificacion>>() {
            @Override
            public void onResponse(Call<List<Notificacion>> call, Response<List<Notificacion>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onFinished(response.body());
                } else {
                    listener.onFailure("Error al obtener las notificaciones");
                }
            }

            @Override
            public void onFailure(Call<List<Notificacion>> call, Throwable t) {
                listener.onFailure("Error de red: " + t.getMessage());
            }
        });
    }
}
