package com.example.gymproject.services;

import com.example.gymproject.entities.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    // Método para obtener los datos del usuario por su ID
    @GET("/usuarios/{id}")
    Call<Usuario> getUsuarioPorId(@Path("id") Long idUsuario);

}
