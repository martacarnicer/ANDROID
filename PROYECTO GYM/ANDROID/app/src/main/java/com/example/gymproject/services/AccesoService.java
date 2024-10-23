package com.example.gymproject.services;

import com.example.gymproject.entities.Acceso;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AccesoService {

    // Obtener todos los accesos de un usuario
    @GET("/accesos/usuario/{idUsuario}")
    Call<List<Acceso>> obtenerAccesosPorUsuario(@Path("idUsuario") Long idUsuario);

    @POST("/accesos")
    Call<Acceso> insertarAcceso(@Body Acceso acceso);

    // Actualizar el centro de acceso del usuario
    @PUT("/accesos/usuario/{idUsuario}/centro/{idCentro}")
    Call<Void> actualizarCentroUsuario(@Path("idUsuario") Long idUsuario, @Path("idCentro") Long idCentro);

    // Otorgar acceso Premium
    @PUT("/accesos/usuario/{idUsuario}/premium")
    Call<Void> otorgarAccesoPremium(@Path("idUsuario") Long idUsuario);
}
