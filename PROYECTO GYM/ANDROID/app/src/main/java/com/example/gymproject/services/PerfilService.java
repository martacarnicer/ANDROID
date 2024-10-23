package com.example.gymproject.services;

import com.example.gymproject.entities.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PerfilService {
    @GET("/usuarios/{id}")
    Call<Usuario> obtenerUsuario(@Path("id") Long idUsuario);

    @PUT("/usuarios/actualizar")
    Call<Void> actualizarUsuario(@Body Usuario usuario);

    @DELETE("/usuarios/baja/{idUsuario}")
    Call<Void> desactivarUsuario(@Path("idUsuario") Long idUsuario);
}
