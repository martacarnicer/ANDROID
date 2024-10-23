package com.example.gymproject.services;

import com.example.gymproject.entities.Notificacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NotificacionService {
    @GET("/notificaciones/usuario/{idUsuario}")
    Call<List<Notificacion>> obtenerNotificacionesPorUsuario(@Path("idUsuario") Long idUsuario);

    @GET("/notificaciones/usuario/{idUsuario}/nuevas")
    Call<Boolean> hayNotificacionesNuevas(@Path("idUsuario") Long idUsuario);

}
